package com.example.drecks_xcode.ui.main;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.drecks_xcode.Model.Status;
import com.example.drecks_xcode.R;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Status> {

    private int resourceLayout;
    private Context mContext;

    public ListAdapter(Context context, int resource, List<Status> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    // Disable click events on ListView
//    @Override
//    public boolean isEnabled(int position) {
//        return false;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(resourceLayout, null);
        }

        Status status = getItem(position);

        if (status != null) {
            TextView leftTextView = (TextView) view.findViewById(R.id.leftTextView);
            TextView rightTextView = (TextView) view.findViewById(R.id.rightTextView);

            if (leftTextView != null) {
                if (status.name.length() == 0) {
                    leftTextView.setText("Unknown");
                } else {
                    leftTextView.setText(status.name);
                }
            }
            if (rightTextView != null) {
                // TODO: Parse "dateInMS" to correct date format
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(status.date);
                String myTime = formatter.format(calendar.getTime());

                rightTextView.setText(myTime);
            }
        }

        return view;
    }

}
