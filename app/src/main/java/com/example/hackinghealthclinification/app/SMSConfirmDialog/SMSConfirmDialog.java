package com.example.hackinghealthclinification.app.SMSConfirmDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hackinghealthclinification.app.R;


/**
 * @author Mark Iantorno
 */
public class SMSConfirmDialog extends DialogFragment {


    // Use this instance of the interface to deliver action events
    ConfirmDialogListener mListener;

    private String mContentMessage;
    private String mPositiveButtonLabel;
    private String mNegativeButtonLabel;

    /**
     * The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     **/
    public interface ConfirmDialogListener {
        public void onDialogPositiveClick(SMSConfirmDialog c);
        public void onDialogNegativeClick(SMSConfirmDialog c);
    }

    /**
     * Constructor.
     * @param contentMessage
     * @param leftButton
     * @param rightButton
     */
    public SMSConfirmDialog(ConfirmDialogListener listener, String contentMessage,
                            String leftButton, String rightButton) {
        super();
        mContentMessage = contentMessage;
        mPositiveButtonLabel = leftButton;
        mNegativeButtonLabel = rightButton;
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.activity_default_dialog, null);
        TextView textView = (TextView) layout.findViewById(R.id.dialog_text_field);
        textView.setText(mContentMessage);
        Button leftButton = (Button) layout.findViewById(R.id.dialog_left_button);
        leftButton.setText(mPositiveButtonLabel);
        Button rightButton = (Button) layout.findViewById(R.id.dialog_right_button);
        rightButton.setText(mNegativeButtonLabel);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogPositiveClick(SMSConfirmDialog.this);
                SMSConfirmDialog.this.dismiss();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDialogNegativeClick(SMSConfirmDialog.this);
                SMSConfirmDialog.this.dismiss();
            }
        });

        builder.setView(layout);

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }
}
