package com.example.sudhanshu.psetconnect;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private final List<String> listOfAllClasses = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "14", "15", "16", "17", "18", "20", "21", "21A", "21F", "21H", "21L", "21M", "21W", "22", "AS", "CC", "CDO", "CMS", "CSB", "ES", "EC", "ESD", "HST", "MS", "MAS", "NS", "OR", "RED", "SDM", "SP", "STS", "WGS"));
    private ArrayAdapter<String> arrayAdapterForAllClasses;
    private ArrayAdapter<String> arrayAdapterForSubClasses;
    private ListView listViewForClass;
    private ListView listViewForSubClass;
    private EditText searchText;

    private Button backButton;
    private Button button;
    private  Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Local Datastore.

        final ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        else{
           if (!currentUser.getBoolean("emailVerified")){

               Intent intent = new Intent(this, NotVerified.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
           };
        }


        listViewForClass = (ListView) findViewById(R.id.listView);
        listViewForSubClass = (b jghListView) findViewById(R.id.listView2);
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