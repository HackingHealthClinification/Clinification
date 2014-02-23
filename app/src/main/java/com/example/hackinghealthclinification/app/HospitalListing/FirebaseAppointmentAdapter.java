package com.example.hackinghealthclinification.app.HospitalListing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hackinghealthclinification.app.R;
import com.example.hackinghealthclinification.app.Tools.FirebaseListAdapter;
import com.firebase.client.Query;

/**
 * Created by miantorno on 2/22/14.
 */
public class FirebaseAppointmentAdapter extends FirebaseListAdapter<AppointmentDataEntry> {

    Context mContext;

    public FirebaseAppointmentAdapter(Context c, Query ref, Class modelClass, int layout, Activity activity) {
        super(ref, AppointmentDataEntry.class, layout, activity);
        mContext = c;
    }

    @Override
    protected void populateView(View v, AppointmentDataEntry model) {

        final TextView loc = (TextView) v.findViewById(R.id.appointment_location_address);
        TextView name = (TextView) v.findViewById(R.id.appointment_location_name);
        TextView doctor = (TextView) v.findViewById(R.id.appointment_doctor);
        TextView phone = (TextView) v.findViewById(R.id.appointment_phone_number);
        TextView date = (TextView) v.findViewById(R.id.appointment_date_time);

        loc.setText(model.getAddress());
        name.setText(model.getHospital());
        doctor.setText(model.getDoctor());
        phone.setText(model.getPhone());
        date.setText(model.getDate());

        ImageButton mapButton = (ImageButton) v.findViewById(R.id.maps_link_button);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap(Uri.parse("geo:0,0?q=" + (String) loc.getText()));
            }
        });

    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(geoLocation);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }
}
