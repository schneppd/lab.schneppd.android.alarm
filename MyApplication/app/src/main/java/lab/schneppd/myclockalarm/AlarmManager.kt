package lab.schneppd.myclockalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import lab.schneppd.myclockalarm.Model.AlarmTimePicked
import java.util.*

/**
 * Created by schneppd on 6/7/17.
 */
//static const values
private val ALARM_KEY:String = "lab.schneppd.myclockalarm.MY_ALARM"

//static methods, AlarmManager does not need to get instantiated for the features it provides

//used to create an alarm to trigger at given time today or tomorrow if not possible today
public fun CreateAlarm(context:Context, time: AlarmTimePicked){

    val timePicked =  ProcessPickedTime(time)
    RegisterAlarm(context, timePicked)

    SaveAlarm(context, timePicked)
}

//used to cancel a created alarm
public fun CancelAlarm(context:Context){
    val intent = getPendingIntent(context)
    val alarm: AlarmManager = GetAlarmManager(context)
    alarm.cancel(intent)

    DeleteAlarm(context)
}

//used to see if an alarm was created
public fun wasAlarmCreated(context:Context):Boolean{
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    if(sp.contains(ALARM_KEY))
        return true
    return false
}

//used to restore the alarm if defined in SharedPreferences
public fun RestoreAlarm(context:Context){
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val timePicked = sp.getLong(ALARM_KEY, 0)
    RegisterAlarm(context, timePicked)
}

//used to restore the alarm at the device boot time if the alarm was defined in SharedPreferences
public fun RestoreAlarmIfNeeded(context:Context){
    if(wasAlarmCreated(context))
        RestoreAlarm(context)
}

//used to get back when the alarm was restored
public fun GetCreatedAlarmDetails(context:Context) : AlarmTimePicked{
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val timePicked = sp.getLong(ALARM_KEY, 0)

    val timeInfos:Date = Date(timePicked)
    val calendar = Calendar.getInstance()
    calendar.time = timeInfos

    val h:Int = calendar.get(Calendar.HOUR_OF_DAY)
    val m = calendar.get(Calendar.MINUTE)
    val result = AlarmTimePicked(h, m)

    return result
}

private fun RegisterAlarm(context:Context, timePicked: Long){
    val alarm = GetAlarmManager(context)
    val intent = getPendingIntent(context)
    //setup the alarm
    alarm.set(AlarmManager.RTC_WAKEUP, timePicked, intent)
}

private fun SaveAlarm(context:Context, timePicked:Long){
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    sp.edit().putLong(ALARM_KEY, timePicked).apply()
}

private fun DeleteAlarm(context:Context){
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    sp.edit().remove(ALARM_KEY).apply();
}


private fun ProcessPickedTime(atp:AlarmTimePicked) : Long {
    val calendar:Calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, atp.hour)
    calendar.set(Calendar.MINUTE, atp.minute)
    calendar.set(Calendar.SECOND, 0)

    var pickedTime:Long = calendar.time.time
    val isInPast:Boolean = pickedTime <= System.currentTimeMillis()
    if (isInPast)
        pickedTime += 1000 * 60 * 60 * 24 //go next day

    return pickedTime
}

private fun GetAlarmManager(context:Context) : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

private fun getPendingIntent(context:Context) : PendingIntent {
    val intent: Intent = Intent(context, AlarmBroadcastReceiver::class.java)
    return PendingIntent.getBroadcast(context, 0, intent, 0)
}


