package com.example.hackinghealthclinification.app.HospitalListing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hackinghealthclinification.app.R;

import java.util.List;

/**
 * Created by miantorno on 1/24/14.
 */
public class AppointmentListAdapter extends ArrayAdapter<AppointmentDataEntry> {

    Context mContext;

    public AppointmentListAdapter(Context context, int resource, List<AppointmentDataEntry> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    /**
     * Set the data for the list.
     *
     * @param data
     */
    public void setData(List<AppointmentDataEntry> data) {
        clear();
        if (data != null) {
            addAll(data);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //If the convertView is null, the view is newly inflated. Otherwise, re-assign new values.
        final ViewHolder holder;

        if ((convertView == null) || (convertView.getTag() == null)) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appt_list_item, null);

            //Set up the viewHolder.
            holder = new ViewHolder();

            holder.appointmentAddress = (TextView) convertView.findViewById(R.id.appointment_location_address);
            holder.appointmentLocation = (TextView) convertView.findViewById(R.id.appointment_location_name);
            holder.doctorName = (TextView) convertView.findViewById(R.id.appointment_doctor);
            holder.mapLink = (ImageButton) convertView.findViewById(R.id.maps_link_button);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Populate view
        AppointmentDataEntry entry = getItem(position);

        holder.appointmentAddress.setText(entry.getAddress());
        holder.appointmentLocation.setText(entry.getHospital());
        holder.doctorName.setText(entry.getDoctor());

        holder.mapLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap(Uri.parse("geo:0,0?q=" + (String) holder.appointmentAddress.getText()));
            }
        });

        return convertView;
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(geoLocation);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }

    private final static class ViewHolder {
        TextView appointmentLocation;
        TextView appointmentAddress;
        TextView doctorName;
        ImageButton mapLink;
    }

}