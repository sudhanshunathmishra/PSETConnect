package com.example.sudhanshu.psetconnect;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {



    private final List<String> listOfAllClasses = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6","7" ,"8","9","10","11", "12", "13", "14", "15", "16", "17","18", "19", "20", "21", "21A", "21F", "21H", "21L", "21M","21W", "22", "AS", "CC", "CDO",  "CMS", "CSB" ,"ES","EC", "ESD","HST", "MS", "MAS", "NS", "OR",  "RED", "SDM", "SP","STS","WGS"));
    private ArrayAdapter<String> arrayAdapterForAllClasses;
    ArrayAdapter<String> arrayAdapterForSubClasses;
    private ListView listViewForClass;
    private ListView listViewForSubClass;
    private EditText searchText;
    private TextView tv;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Local Datastore.


        listViewForClass = (ListView) findViewById(R.id.listView);
        listViewForSubClass = (ListView) findViewById(R.id.listView2);
        button = (Button) findViewById(R.id.button);
        arrayAdapterForAllClasses = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfAllClasses);

        listViewForClass.setAdapter(arrayAdapterForAllClasses);
        searchText = (EditText) findViewById(R.id.editText);


        listViewForClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String fileName = listViewForClass.getItemAtPosition(position).toString();

                fileName = fileName.concat(".txt");
                List <String> listOfSubClasses = TextParser.textParserForSubClasses(getApplicationContext(),fileName);

                arrayAdapterForSubClasses = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listOfSubClasses);
                listViewForSubClass.setAdapter(arrayAdapterForSubClasses);
            }


        });

        listViewForSubClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Parse.enableLocalDatastore(MainActivity.this);
                Parse.initialize(MainActivity.this, "LUlLqUhNCfFo1MdBYJ6qHkIQKUGrsdMarZrqaT8l", "4EBHWdRqwmpelaBHHC053aNlWCPLHq1jHnAsIFN7");

                ParseObject testObject = new ParseObject("NewObject");
                testObject.put("foo", "bar");
                testObject.put("userID", "bar");
                testObject.put("objectId", "snmishra");
                testObject.saveInBackground();

                Toast.makeText(MainActivity.this,"Sent to Cloud",Toast.LENGTH_LONG).show();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
