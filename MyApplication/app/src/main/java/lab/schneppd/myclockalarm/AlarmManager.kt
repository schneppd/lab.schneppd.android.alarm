package lab.schneppd.myclockalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import lab.schneppd.myclockalarm.Model.AlarmTimePicked
import java.util.*

/**
 * Created by schneppd on 6/7/17.
 */
//static values
private val ALARM_KEY:String = "lab.schneppd.myclockalarm.MY_ALARM"

//static methodes
//used to create an alarm to trigger at given time today or tomorrow if not possible today
fun CreateAlarm(context:Context, time: AlarmTimePicked){

    val timePicked =  ProcessPickedTime(time)
    val alarm = GetAlarmManager(context)
    val intent = getPendingIntent(context)
    //setup the alarm
    alarm.set(AlarmManager.RTC_WAKEUP, timePicked, intent)

}

//used to cancel created alarm
fun CancelAlarm(context:Context){
    val intent = getPendingIntent(context)
    val alarm: AlarmManager = GetAlarmManager(context)
    alarm.cancel(intent)
}

private fun ProcessPickedTime(atp:AlarmTimePicked) : Long {
    val calendar:Calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, atp.hour)
    calendar.set(Calendar.MINUTE, atp.minute)
    calendar.set(Calendar.SECOND, 0)

    var pickedTime:Long = calendar.time.time
    val isInPast:Boolean = pickedTime <= System.currentTimeMillis()
    if (isInPast)
        pickedTime += 1000 * 60 * 60 * 24 //add one day

    return pickedTime
}

private fun GetAlarmManager(context:Context) : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

private fun getPendingIntent(context:Context) : PendingIntent {
    val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
    return PendingIntent.getBroadcast(context, 0, intent, 0)
}


