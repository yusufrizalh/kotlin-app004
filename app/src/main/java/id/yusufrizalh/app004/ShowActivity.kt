package id.yusufrizalh.app004

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        txtShowName.text = intent.getStringExtra("name")
        txtShowEmail.text = intent.getStringExtra("email")
        txtShowAddress.text = intent.getStringExtra("address")

        // backing bean: kemampuan utk mengenali identifier secara otomatis
        // dengan syarat: nama identifier yg dideklarasi dan dipanggil harus sama
    }

    fun openAlertDiaog(view: View){
        val openAlert = AlertDialog.Builder(this)
        openAlert.setTitle("Alert Dialog")
        openAlert.setMessage("Creating alert dialog")
        openAlert.setPositiveButton("Positive"){
            dialogInterface: DialogInterface, _: Int -> Toast.makeText(this, "Positive is clicked", Toast.LENGTH_LONG)
        }
        openAlert.setNegativeButton("Negative"){
                dialogInterface: DialogInterface, _: Int -> Toast.makeText(this, "Negative is clicked", Toast.LENGTH_LONG)
        }
        openAlert.setNeutralButton("Neutral"){
                dialogInterface: DialogInterface, _: Int -> Toast.makeText(this, "Neutral is clicked", Toast.LENGTH_LONG)
        }
        openAlert.setCancelable(false)
        openAlert.show()
    }

    fun openNotification(view: View){
        // cek versi dari android SDK
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // membuat notification
            val channel_id = "channel_01"
            val channel_name = "notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val myChannel = NotificationChannel(channel_id, channel_name, importance)
            myChannel.description = "notification testing"
            myChannel.enableLights(true)
            myChannel.enableVibration(true)
            myChannel.lightColor = Color.GREEN

            val intent = Intent(this, NotificationActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val notify: Notification = Notification.Builder(this, channel_id)
                .setSmallIcon(R.drawable.bell)
                .setContentTitle("Test Notification")
                .setContentText("Learn Android ATC is Simple")
                .setContentIntent(pendingIntent)
                .build()

            val myNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            if (myNotifyManager != null) {
                myNotifyManager.createNotificationChannel(myChannel)
                myNotifyManager.notify(1, notify)
            }
        }
    }
}