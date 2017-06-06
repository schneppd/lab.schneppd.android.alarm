package lab.schneppd.myclockalarm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.app.AlarmManager
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.os.SystemClock
import android.app.PendingIntent
import android.content.Intent
import android.opengl.Visibility
import java.util.*
import android.content.SharedPreferences
import android.text.format.Time


class MainActivity : AppCompatActivity() {
    private var alarmMgr:AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    public fun ExecuteSetupClock(sender:View){
        val hh:Int = timePicker.hour
        val mm:Int = timePicker.minute

        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val alarmIntent:PendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)

        var alarm:Calendar = Calendar.getInstance()
        alarm.set(Calendar.HOUR, 4)
        alarm.set(Calendar.MINUTE, 55)
        alarm.set(Calendar.SECOND, 0)
        alarm.set(Calendar.MILLISECOND, 0)

        //set time for next tic



        //save alarm for reboot
        val sp:SharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        var spe:SharedPreferences.Editor = sp.edit()
        spe.putLong("Alarm", alarm.timeInMillis);
        spe.commit();


        //setup alarm
        alarmMgr!!.set(AlarmManager.RTC_WAKEUP, alarm.timeInMillis, alarmIntent)

        //update ui
        btnClockSetup.visibility = View.GONE
        btnClockCancel.visibility = View.VISIBLE

        txtClockActivity.text = "Clock setup to tic in {$hh}h {$mm}m"

    }

    public fun ExecuteCancelClock(sender:View){
        val intent = Intent(this, AlarmReceiver::class.java)
        val alarmIntent:PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        //cancel alarm
        alarmMgr!!.cancel(alarmIntent)

        //delete from memory
        val sp:SharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        var spe:SharedPreferences.Editor = sp.edit()
        spe.remove("Alarm");
        spe.commit();

        //update ui
        btnClockSetup.visibility = View.VISIBLE
        btnClockCancel.visibility = View.GONE

        txtClockActivity.text = "No alarm setup"

    }

}
