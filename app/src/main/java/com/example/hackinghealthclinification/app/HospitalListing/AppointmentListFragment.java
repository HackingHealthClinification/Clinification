package com.example.hackinghealthclinification.app.HospitalListing;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hackinghealthclinification.app.R;
import com.example.hackinghealthclinification.app.Tools.FirebaseListAdapter;
import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by miantorno on 1/24/14.
 */
public class AppointmentListFragment extends android.support.v4.app.ListFragment {

    public final static String TAG = "AppointmentListFragment";

    private String mFirebaseUrl = "https://clinificationandroid.firebaseio.com/appointment";

    public TextView mGreetingMessage;
    public TextView mNextAppointmentMessage;
    private ListView mAppointmentListView;
    private FirebaseAppointmentAdapter mAppointmentListAdapter;

    /**
     * Create the fragment to display the list of medication questions.
     */
    public AppointmentListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Create the adapter.
        mAppointmentListAdapter = createListAdapter();

        View rootView = inflater.inflate(R.layout.appt_list_fragment, container, false);

        mGreetingMessage = (TextView) rootView.findViewById(R.id.user_greeting_field);
        mNextAppointmentMessage = (TextView) rootView.findViewById(R.id.user_greeting_field);

        mAppointmentListView = (ListView) rootView.findViewById(android.R.id.list);

        mAppointmentListView.setAdapter(mAppointmentListAdapter);

        //initializeTitle();

        return rootView;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void initializeTitle() {
        Date lowestDate;
        Date date = null;
        AppointmentDataEntry displayEntry = null;
        try{
        date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(((AppointmentDataEntry) mAppointmentListAdapter.getItem(0)).getDate());
        } catch (Exception e) {
            System.out.println("FAILURE IN PART 1");
        }
        lowestDate = date;
        for (int i = 0 ; i < mAppointmentListAdapter.getCount(); i++) {
            try{
                date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(((AppointmentDataEntry) mAppointmentListAdapter.getItem(i)).getDate());
            } catch (Exception e) {
                System.out.println("FAILURE IN PART 2");
            }
            if (date.compareTo(lowestDate) < 0){
                displayEntry = (AppointmentDataEntry) mAppointmentListAdapter.getItem(i);
            }
        }
        mGreetingMessage.setText(String.format(getActivity().getString(R.string.greeting), displayEntry.getname()));
        mNextAppointmentMessage.setText(String.format(getActivity().getString(R.string.next_appointment), displayEntry.getDate()));
    }

    /**
     * Create a loaded instance of the list adapter.
     *
     * @return
     */
    public FirebaseAppointmentAdapter createListAdapter() {

        Firebase ref = new Firebase(mFirebaseUrl);
        return new FirebaseAppointmentAdapter(getActivity().getApplicationContext(), ref, AppointmentDataEntry.class, R.layout.appt_list_item, getActivity());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    /**
     * User has answered all questions, and the confirm button has been pressed. Store all necessary
     * data.
     */
    private void storeUserResponse() {
        //@TODO Implement this!
    }

}
