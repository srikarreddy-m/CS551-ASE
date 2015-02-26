package edu.umkc.sm8xd.snag_job;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Post_job_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        try {
            addItemsCatagory();

        }
        catch(Exception e){

            System.out.println("Exception is:"+e);
        }
    }
    // add items into spinner Catagory
    public void addItemsCatagory() {
        final Spinner spinner1;
        spinner1 = (Spinner) findViewById(R.id.catagory);
        List<String> list = new ArrayList<String>();
        list.add("<select>");
        list.add("Technical");
        list.add("Non-Technical");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String val = spinner1.getSelectedItem().toString();
                System.out.println("Value is:" + val);
                if (val.equalsIgnoreCase("Technical")) {
                    System.out.println("Intechnical");
                    addItemsTechnicalSubCatagory();
                } else if(val.equalsIgnoreCase("Non-Technical")){
                    System.out.println("Inside nontechnical");
                    addItemsNonTechnicalSubCatagory();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



    }
    // add items into spinner SubCatagory
    public void addItemsTechnicalSubCatagory() {
        Spinner spinner1;
        spinner1 = (Spinner) findViewById(R.id.subcatagory);
        List<String> list = new ArrayList<String>();
        list.add("<select>");
        list.add("Software");
        list.add("Hardware");
        list.add("Tutoring");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }
    // add items into spinner SubCatagory
    public void addItemsNonTechnicalSubCatagory() {
        Spinner spinner1;
        spinner1 = (Spinner) findViewById(R.id.subcatagory);
        List<String> list = new ArrayList<String>();
        list.add("<select>");
        list.add("Laundry");
        list.add("Pickup/Drop");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_job_, menu);
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
