/**

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.

 **/

package com.example.hackinghealthclinification.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hackinghealthclinification.app.CustomViewClasses.CustomButtonView;
import com.example.hackinghealthclinification.app.HospitalListing.AppointmentListFragment;
import com.example.hackinghealthclinification.app.SMSConfirmDialog.SMSConfirmDialog;

public class ClinificationApp extends FragmentActivity {

    CustomButtonView mViewAppointmentButton;
    CustomButtonView mManageAppointment;
    private final String SMS_TRIGGER_KEY = "HHMTL2014";
    private Context mContext;
    private String mPhoneNumber;
    private boolean mIsRegistered;
    BroadcastReceiver mReceiver;
    final String mServerNumber = "5146006096";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinification_app);

        initializeLaunchButtons();
        mIsRegistered = false; // check if BroadcastReceiver registered
        IntentFilter filterSmsReceived = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        final SMSConfirmDialog.ConfirmDialogListener mListener = new SMSConfirmDialog.ConfirmDialogListener() {
            @Override
            public void onDialogPositiveClick(SMSConfirmDialog c) {
//                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("yes"
//                        + mPhoneNumber)));
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mServerNumber, null, "yes", null, null);
            }

            @Override
            public void onDialogNegativeClick(SMSConfirmDialog c) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mServerNumber, null, "no", null, null);
            }
        };

        mReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                mContext = context;
                Bundle pudsBundle = intent.getExtras();
                Object[] pdus = (Object[]) pudsBundle.get("pdus");
                SmsMessage messages =SmsMessage.createFromPdu((byte[]) pdus[0]);
                Log.i("Error with SMS ::: ", messages.getMessageBody());
                if(messages.getMessageBody().contains(SMS_TRIGGER_KEY)) {
                    mPhoneNumber = messages.getOriginatingAddress();
                    SMSConfirmDialog dialog = new SMSConfirmDialog(mListener,
                            "Will you accept the new appointment: " + "\n" + messages.getMessageBody(),
                            "Yes",
                            "No");
                    dialog.show(getSupportFragmentManager(), null);
                }
            }
        };

        this.registerReceiver(mReceiver, filterSmsReceived);
        mIsRegistered = true;

    }

    @Override
    protected void onPause() {
        /*
         * Unregister BroadcastReceiver
         */
        if (mIsRegistered) {
            this.unregisterReceiver(mReceiver);
            mIsRegistered = false;
        }
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        /*
         * Unregister BroadcastReceiver
         */
        if (mIsRegistered) {
            this.unregisterReceiver(mReceiver);
            mIsRegistered = false;
        }
        super.onDestroy();

    }

    private void initializeLaunchButtons() {
        mViewAppointmentButton = (CustomButtonView) findViewById(R.id.launch_button_view_appointments);
        mViewAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointmentListFragment appointmentListFragment = new AppointmentListFragment();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(android.R.id.content, appointmentListFragment, AppointmentListFragment.TAG);
                transaction.addToBackStack(AppointmentListFragment.TAG);
                transaction.commit();
            }
        });

        mViewAppointmentButton = (CustomButtonView) findViewById(R.id.launch_button_test);
        mViewAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clinification_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
