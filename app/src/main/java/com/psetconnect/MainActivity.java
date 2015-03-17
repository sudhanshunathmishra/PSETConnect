package com.psetconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends CustomListIndex {

    private final ArrayList<String> listOfAllClasses = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "14", "15", "16", "17", "18", "20", "21", "21A", "21F", "21H", "21L", "21M", "21W", "22", "AS", "CC", "CDO", "CMS", "CSB", "ES", "EC", "ESD", "HST", "MS", "MAS", "NS", "OR", "RED", "SDM", "SP", "STS", "WGS"));
    private ArrayAdapter<String> arrayAdapterForAllClasses;
    private ArrayAdapter<String> arrayAdapterForSubClasses;
    private ListView listViewForClass;
    private ListView listViewForSubClass;
    private EditText searchText;

    private Button backButton;
    private Button button;
    private Button logout;


    private ListView booksLV;

    private static Button resultButton;

    private UserListAdapter userListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        resultButton = (Button) findViewById(R.id.result_button);
        booksLV = (ListView) findViewById(R.id.booksLV);
        selectedIndex = (TextView) findViewById(R.id.selectedIndex);
        allClasses = TextParser.textParserForSubClasses(getApplicationContext(), "SP15.txt");

        ArrayList<String> subsidiesList = getIndexedBooks(allClasses);
        totalListSize = subsidiesList.size();

        userListAdapter = new UserListAdapter(this, subsidiesList);
        booksLV.setAdapter(userListAdapter);

        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndex.setOnClickListener(onClicked);
        sideIndexHeight = sideIndex.getHeight();
        mGestureDetector = new GestureDetector(this,
                new ListIndexGestureListener());


        // Enable Local Datastore.

        final ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        } else {
            if (!currentUser.getBoolean("emailVerified")) {

                Intent intent = new Intent(this, NotVerified.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("user", ParseUser.getCurrentUser().getEmail());
            installation.saveInBackground();
        }


        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(i);
            }
        });

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        logout = (Button) findViewById(R.id.button2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                loadLoginView();

            }
        });
        /*
        listViewForClass = (ListView) findViewById(R.id.listView);
        listViewForSubClass = (ListView) findViewById(R.id.listView2);
        //button = (Button) findViewById(R.id.button);
        backButton = (Button)findViewById(R.id.button4);
        logout =  (Button) findViewById(R.id.button2);

        arrayAdapterForAllClasses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfAllClasses);

        listViewForClass.setAdapter(arrayAdapterForAllClasses);
        searchText = (EditText) findViewById(R.id.editText);

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                loadLoginView();

            }
        });

        listViewForClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String fileName = listViewForClass.getItemAtPosition(position).toString();
                String textForSearch = fileName.concat(".");


                fileName = fileName.concat(".txt");
                List<String> listOfSubClasses = TextParser.textParserForSubClasses(getApplicationContext(), fileName);

                arrayAdapterForSubClasses = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listOfSubClasses);
                listViewForSubClass.setAdapter(arrayAdapterForSubClasses);
                listViewForClass.setVisibility(View.INVISIBLE);
                listViewForSubClass.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
                searchText.setText(textForSearch);
                searchText.setVisibility(View.VISIBLE);
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewForSubClass.setVisibility(View.INVISIBLE);
                listViewForClass.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.INVISIBLE);

                searchText.setVisibility(View.INVISIBLE);
                searchText.setText("");

                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });


        listViewForSubClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final CharSequence[] items = {"2", "5"};
                final int listPosition = position;
                final ParseObject testObject = new ParseObject("Query");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                       MainActivity.this);

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
                            .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                public void onClick (DialogInterface dialog,int id){
                                    // if this button is clicked, close
                                    // current activity
                                }
                            })
                                    .setPositiveButton("Confirm",new DialogInterface.OnClickListener() {
                                public void onClick (DialogInterface dialog,int id){
                                    // if this button is clicked, close
                                    // current activity
                                    int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                                    int groupSize = Integer.parseInt(((AlertDialog)dialog).getListView().getItemAtPosition(selectedPosition).toString());
                                    testObject.put("UserID", currentUser.getEmail());
                                    testObject.put("Class", listViewForSubClass.getItemAtPosition(listPosition));
                                    testObject.put("GroupSize", groupSize);
                                    testObject.saveInBackground();
                                    Log.i("PARSE ", "Sent to Parse!!");
                                    Toast.makeText(MainActivity.this, "Requested Group Size " + groupSize + " For Class " + listViewForSubClass.getItemAtPosition(listPosition), Toast.LENGTH_LONG).show();
                                }
                            });
                           AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }

            });

        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text


                MainActivity.this.arrayAdapterForSubClasses.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    */

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        getDisplayListOnChange();
    }


    private ArrayList<String> getIndexedBooks(
            List<String> booksVector) {

        //Retrieve it from DB in shorting order

        ArrayList<String> v = new ArrayList<String>();
        //Add default item

        String idx1 = null;
        String idx2 = null;
        for (int i = 0; i < booksVector.size(); i++) {
            String temp = booksVector.get(i);
            //Insert the alphabets
            idx1 = (temp.substring(0, temp.indexOf("."))).toLowerCase();
            if (idx1.startsWith("21")) {
                idx1 = "21";
                if (!idx1.equalsIgnoreCase(idx2)) {
                    v.add(idx1.toUpperCase());
                    idx2 = idx1;
                    dealList.add(i);
                }
            } else if (Character.isLetter(temp.charAt(0))) {
                idx1 = "A-Z";
                if (!idx1.equalsIgnoreCase(idx2)) {
                    v.add(idx1.toUpperCase());
                    idx2 = idx1;
                    dealList.add(i);
                }
            } else {
                if (!idx1.equalsIgnoreCase(idx2)) {
                    v.add(idx1.toUpperCase());
                    idx2 = idx1;
                    dealList.add(i);
                }

            }
            v.add(temp);
        }

        return v;
    }

    /**
     * ListIndexGestureListener method gets the list on scroll.
     */
    private class ListIndexGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {

            /**
             * we know already coordinates of first touch we know as well a
             * scroll distance
             */
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            /**
             * when the user scrolls within our side index, we can show for
             * every position in it a proper item in the list
             */
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}