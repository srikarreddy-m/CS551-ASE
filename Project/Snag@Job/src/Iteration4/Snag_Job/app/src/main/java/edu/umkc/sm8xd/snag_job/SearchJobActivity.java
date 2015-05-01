package edu.umkc.sm8xd.snag_job;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utility.WebServiceCall;


public class SearchJobActivity extends ActionBarActivity {
    final Context context = this;
    WebServiceCall wcf;
    Context obj;
    List<String> g_result ;
    String resultSevice="";
    ListView l ;
    String stmt ="";
    String CategoryET;
    String SubCategoryET;
    String JobCityET;
    String JobZipCodeET;
    String JobPayET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);



        try {
            addItemsCatagory();

        } catch (Exception e) {

            System.out.println("Exception is:" + e);
        }
        l = (ListView) findViewById(R.id.search_job_search_listview);

        Button searchJobs = (Button) findViewById(R.id.sign_in_searchjob_btn);
        obj = this;
        searchJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CategoryET = ((Spinner) findViewById(R.id.search_job_category)).getSelectedItem().toString();
                SubCategoryET = ((Spinner) findViewById(R.id.search_job_subcategory)).getSelectedItem().toString();
                JobCityET = ((EditText) findViewById(R.id.search_job_city)).getText().toString();
                JobZipCodeET = ((EditText) findViewById(R.id.search_job_zip)).getText().toString();
                JobPayET = ((EditText) findViewById(R.id.search_job_pay)).getText().toString();

                stmt=CategoryET+","+SubCategoryET+","+JobCityET+","+JobZipCodeET+","+JobPayET;
                System.out.print("Statement is ::" + stmt);

                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/jobSearchDetails/"+stmt);
                List<String> clearlist = new ArrayList<String>();
                final StableArrayAdapter adapter = new StableArrayAdapter(obj,
                        android.R.layout.simple_list_item_1, clearlist);
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_job, menu);
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
        if (id == R.id.action_logout) {
            LoginActivity.clearDefaults(context);
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private class ReadWCFServiceFeed extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            wcf = new WebServiceCall();
            return wcf.readWCFServiceData(urls[0]);
        }

        protected void onPostExecute(String result) {
            System.out.println("Onpost ecec result  is:"+result);

            resultSevice =result;
            System.out.println("Onpost ecec res service is:"+resultSevice);

            try {
                System.out.println("In try block result is: "+ resultSevice);
                JSONArray jsonarray = new JSONArray(resultSevice);
                JSONObject jsonobject = new JSONObject();
                int n = jsonarray.length();
                //  String[] values = new String[n];
                g_result=new ArrayList<>();
                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject = jsonarray.getJSONObject(i);
                    g_result.add(jsonobject.getString("Id") +"\t"+"\t"+"\t"+"\t"+ jsonobject.getString("ShortDescription"));
                    System.out.println("Object :"+i +"  "+ jsonobject);

                }
                System.out.println("Final list"+ g_result);
                System.out.println("Context object is:"+ obj);
                final StableArrayAdapter adapter = new StableArrayAdapter(obj,
                        android.R.layout.simple_list_item_1, g_result);


                l.setAdapter(adapter);
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final String item = (String) parent.getItemAtPosition(position);
                        System.out.println("Item onclick is : " + item);
                        Intent intent = new Intent(context, ViewIndividualSearchJobActivity.class);
                        String email = getIntent().getStringExtra("user_email");
                        System.out.println("Emaaill is: " + email);
                        intent.putExtra("user_email", email);
                        intent.putExtra("ListItem", item);
                        startActivity(intent);

                    }

                });



            } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }



        }
    }
    //for catagory and sub category

    // add items into spinner Catagory
    public void addItemsCatagory() {
        final Spinner spinner1;
        spinner1 = (Spinner) findViewById(R.id.search_job_category);
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
                } else if (val.equalsIgnoreCase("Non-Technical")) {
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
        spinner1 = (Spinner) findViewById(R.id.search_job_subcategory);
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
        spinner1 = (Spinner) findViewById(R.id.search_job_subcategory);
        List<String> list = new ArrayList<String>();
        list.add("<select>");
        list.add("Laundry");
        list.add("Pickup/Drop");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

}
