package lab.schneppd.myclockalarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Vibrator
import android.support.v4.app.NotificationCompat


/**
 * Created by schneppd on 6/6/17.
 */

class AlarmBroadcastReceiver : BroadcastReceiver() {

    //used as callback when the alarm is triggered
    override fun onReceive(context: Context, intent: Intent) {
        //we show a notification to the user
        val ncb:NotificationCompat.Builder = NotificationCompat.Builder(context)
        ncb.setSmallIcon(R.mipmap.ic_alarm)
        ncb.setContentTitle("Epic alarm trigger")
        ncb.setContentText("Hey wake up, your alarm triggered!")

        val result:Intent = Intent(context, MainActivity::class.java)
        val resultP:PendingIntent = PendingIntent.getActivity(context, 0, result, PendingIntent.FLAG_UPDATE_CURRENT)
        ncb.setContentIntent(resultP)

        val nm:NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id_notification:Int = 1
        nm.notify(id_notification, ncb.build())

        val v:Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(500)

        CancelAlarm(context)
    }

}