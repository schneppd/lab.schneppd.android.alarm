package lab.schneppd.myclockalarm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import lab.schneppd.myclockalarm.Model.AlarmTimePicked

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timePicker.setIs24HourView(true)

        if(wasAlarmCreated(this))
            RestoreAlarm()
    }

    //used to create an alarm
    public fun ExecuteSetupClock(sender:View){
        val hh:Int = timePicker.hour
        val mm:Int = timePicker.minute

        //process input data and create the alarm
        val setup : AlarmTimePicked = AlarmTimePicked(hh, mm)
        CreateAlarm(this, setup)

        //update ui
        btnClockSetup.visibility = View.GONE
        btnClockCancel.visibility = View.VISIBLE

        txtClockActivity.text = "Clock setup to tic in {$hh}h {$mm}m"

    }

    //used to cancel a created alarm
    public fun ExecuteCancelClock(sender:View){
        CancelAlarm(this)

        //update ui
        btnClockSetup.visibility = View.VISIBLE
        btnClockCancel.visibility = View.GONE

        txtClockActivity.text = "No alarm setup"

    }

    //restores created alarm's informations
    private fun RestoreAlarm(){
        RestoreAlarm(this)
        val infos = GetCreatedAlarmDetails(this)
        timePicker.hour = infos.hour
        timePicker.minute = infos.minute
    }

}
