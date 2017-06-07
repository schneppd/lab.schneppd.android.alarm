package lab.schneppd.myclockalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by schneppd on 6/7/17.
 */
class BootBroadcastReceiver : BroadcastReceiver() {
    //used to manage the device reboot case
    override fun onReceive(context: Context, intent: Intent) {
        RestoreAlarmIfNeeded(context)
    }
}