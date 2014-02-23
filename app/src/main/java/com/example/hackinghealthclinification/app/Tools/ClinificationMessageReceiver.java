package com.example.hackinghealthclinification.app.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.hackinghealthclinification.app.R;
import com.example.hackinghealthclinification.app.SMSConfirmDialog.SMSConfirmDialog;

/**
 * Created by miantorno on 2/22/14.
 */
public class ClinificationMessageReceiver extends BroadcastReceiver implements SMSConfirmDialog.ConfirmDialogListener {

    private final String SMS_TRIGGER_KEY = "HHMTL2014";
    private Context mContext;
    private String mPhoneNumber;

    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages =SmsMessage.createFromPdu((byte[]) pdus[0]);
        Log.i("Error with SMS ::: ", messages.getMessageBody());
        if(messages.getMessageBody().contains(SMS_TRIGGER_KEY)) {
            mPhoneNumber = messages.getOriginatingAddress();
            SMSConfirmDialog dialog = new SMSConfirmDialog(this,
                    "Will you accept the new appointment: " + "\n" + messages.getMessageBody(),
                    "Yes",
                    "No");
            dialog.show(((FragmentActivity)mContext.getApplicationContext()).getSupportFragmentManager(), null);
        }
    }

    @Override
    public void onDialogPositiveClick(SMSConfirmDialog c) {
       mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Resources.getSystem().getString(R.string.string_yes)
               + mPhoneNumber)));
    }

    @Override
    public void onDialogNegativeClick(SMSConfirmDialog c) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Resources.getSystem().getString(R.string.string_no)
                + mPhoneNumber)));
    }

}