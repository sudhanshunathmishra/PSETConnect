package com.psetconnect;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ResultActivity extends ActionBarActivity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        final String emailId = ParseUser.getCurrentUser().getEmail();

        final Map<String, Object> params = new HashMap<String, Object>();
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        params.put("UserID", emailId);

        ParseCloud.callFunctionInBackground("RetreiveMatch", params, new FunctionCallback<ArrayList<ParseObject>>() {
            public void done(ArrayList<ParseObject> pList, ParseException e) {
                if (e == null) {
                    Log.i("PARSE", pList.get(0).getUpdatedAt().getClass().getName());
                    Log.i("PARSE", ""+pList.size());


                    // preparing list data

                    Map<Date, ParseObject> map = new TreeMap<Date, ParseObject>();

                    for (int i=0; i<pList.size();i++){
                        Date dateUpdated = pList.get(i).getUpdatedAt();
                        map.put(dateUpdated, pList.get(i));
                        Log.i("Map", map.toString());
                    }

                    ArrayList <ParseObject> sortedPlist = new ArrayList<ParseObject>();
                    for (Date date : map.keySet()){
                        sortedPlist.add(map.get(date));
                    }
                    Collections.reverse(sortedPlist);
                    prepareListData(sortedPlist, emailId);

                    listAdapter = new ExpandableListAdapter(ResultActivity.this, listDataHeader, listDataChild);

                    // setting list adapter
                    expListView.setAdapter(listAdapter);
                }

           }

      });
  }

    /*
     * Preparing the list data
     */
    private void prepareListData(ArrayList<ParseObject>pList, String emailID) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        for (int i =0; i<pList.size();i++){
            ParseObject everyObject = pList.get(i);
            String text = everyObject.get("Class").toString();
            text = ((text.concat("      ")).concat("Group Size- ")).concat(everyObject.get("GroupSize").toString());
            listDataHeader.add(text);

            List<String> childList = new ArrayList<>();
            try{
                String childText = everyObject.get("Matches").toString();
                List <String> matches = Arrays.asList(childText.split(","));
                for (String eachMatch: matches){
                    if (!eachMatch.equals(emailID)){
                        childList.add(eachMatch);
                    }
                }

            }
            catch(NullPointerException np){
                String childText = "Still Searching";
                childList.add(childText);
            }


                listDataChild.put(listDataHeader.get(i), childList);


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
