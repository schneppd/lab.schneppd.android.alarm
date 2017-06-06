package lab.schneppd.myclockalarm

import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


/**
 * Created by schneppd on 6/6/17.
 */

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(ct: Context, it: Intent) {
        if (it.action.equals("android.intent.action.BOOT_COMPLETED")){

        } else {
            Toast.makeText(ct, "Alarm received!", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(ct, "Alarm received!", Toast.LENGTH_LONG).show()


    }

}