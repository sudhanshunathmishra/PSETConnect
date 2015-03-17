package com.example.sudhanshu.psetconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class UserListAdapter extends BaseAdapter {

    private static final String TAG = UserListAdapter.class.getName();
    private Activity activity;
    private ArrayList<String> items;

    public UserListAdapter(Activity activity,
                           ArrayList<String> items) {
        Log.i(TAG, TAG);
        this.activity = activity;
        this.items = items;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.listrow_user, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.nameTV);
            holder.headingLL = (LinearLayout) convertView.findViewById(R.id.headingLL);
            holder.headingTV = (TextView) convertView.findViewById(R.id.headingTV);
            holder.nameLL = (LinearLayout) convertView.findViewById(R.id.nameLL);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position < items.size()) {

            final String mitClass = items.get(position);
            if (mitClass != null
                    && (!mitClass.contains("."))) {
                holder.nameLL.setVisibility(View.GONE);
                holder.headingLL.setVisibility(View.VISIBLE);
                holder.headingTV.setText(mitClass);
                holder.headingLL.setBackgroundColor(android.R.color.transparent);
            } else {
                holder.nameLL.setVisibility(View.VISIBLE);
                holder.headingLL.setVisibility(View.GONE);
                holder.name.setText(mitClass.toString());

                View ll = (LinearLayout) holder.name.getParent();
                ll.setFocusable(true);
                ll.setSelected(true);
                ll.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CharSequence[] items = {"2", "5"};
                        final int listPosition = position;
                        final ParseObject testObject = new ParseObject("Query");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                activity);

                        // set title
                        alertDialogBuilder.setTitle("Select the Size Of Your Group");

                        // set dialog message
                        alertDialogBuilder
                                //.setMessage("Select the Size Of Your Group")
                                .setCancelable(false)
                                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                    }
                                })
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity


                                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                        final int groupSize = Integer.parseInt(((AlertDialog) dialog).getListView().getItemAtPosition(selectedPosition).toString());
                                        final String emailId = ParseUser.getCurrentUser().getEmail();
                                        final String matches = "Still Searching";
                                        final HashMap<String, Object> params = new HashMap<String, Object>();

                                        params.put("Class", mitClass);
                                        params.put("GroupSize", groupSize);
                                        params.put("UserID", emailId);
                                        params.put("Matches", matches);


                                        ParseCloud.callFunctionInBackground("Delete", params, new FunctionCallback<String>() {
                                            public void done(String result, ParseException e) {
                                                if (e == null) {
                                                    //Deleted
                                                    Log.i("PARSE", "Delted!");

                                                  testObject.put("UserID", emailId);
                                                   testObject.put("Class", mitClass);
                                                   testObject.put("GroupSize", groupSize);
                                                   testObject.put("Matches", matches);
                                                   testObject.saveInBackground();

                                                    Log.i("PARSE ", "Sent to Parse!!");

                                                    ParseCloud.callFunctionInBackground("Match", params, new FunctionCallback<String>() {
                                                        public void done(String result, ParseException e) {
                                                            if (e == null) {
                                                                //Deleted
                                                                Log.i("PARSE ", result);
                                                            }
                                                        }
                                                    });
                                               }
                                            }
                                        });





                                        Toast.makeText(activity, "Requested Group Size " + groupSize + " For Class " + mitClass, Toast.LENGTH_LONG).show();


                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                });
            }
        }


        return convertView;
    }

    private static class ViewHolder {
        TextView name, headingTV;
        LinearLayout nameLL, headingLL;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
