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
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utility.WebServiceCall;


public class PostJobActivity extends ActionBarActivity {
    final Context context = this;
    String insert_stmt = "";
    String ShortDescriptionET;
    String LongDescriptionET;
    String SkillSetET;
    String JobAddressET;
    String JobCityET;
    String JobZipCodeET;
    String JobPayET;
    String JobDateET;
    String CategoryET;
    String SubCategoryET;
    WebServiceCall wcf;
    String loginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginEmail = getIntent().getStringExtra("user_email");
        setContentView(R.layout.activity_post_job);
        try {
            addItemsCatagory();

        } catch (Exception e) {

            System.out.println("Exception is:" + e);
        }


        Button postJobBtn = (Button) findViewById(R.id.postJob_button);
        postJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ShortDescriptionET = ((EditText) findViewById(R.id.post_job_desc)).getText().toString();
                System.out.print("ShortDescriptionET in post activity is:" + ShortDescriptionET);
                LongDescriptionET = ((EditText) findViewById(R.id.post_longjob_desc)).getText().toString();
                SkillSetET = ((EditText) findViewById(R.id.post_job_skill)).getText().toString();
                JobAddressET = ((EditText) findViewById(R.id.post_address)).getText().toString();
                System.out.print("JobAddressET in post activity is:" + JobAddressET);
                JobCityET = ((EditText) findViewById(R.id.post_city)).getText().toString();
                JobZipCodeET = ((EditText) findViewById(R.id.post_zip)).getText().toString();
                JobPayET = ((EditText) findViewById(R.id.post_job_pay)).getText().toString();
                JobDateET = ((EditText) findViewById(R.id.post_jobDate)).getText().toString();
                CategoryET = ((Spinner) findViewById(R.id.catagory)).getSelectedItem().toString();
                SubCategoryET = ((Spinner) findViewById(R.id.subcatagory)).getSelectedItem().toString();
                System.out.print("SubCategoryET  is:" + SubCategoryET);

                String loginEmail = getIntent().getStringExtra("user_email");
                System.out.print("Email in post activity is:" + loginEmail);
                insert_stmt = "'" + ShortDescriptionET + "','" + LongDescriptionET + "','" + SkillSetET + "','" + JobAddressET + "','" + JobCityET + "'," + JobZipCodeET + "," + JobPayET + ",'" + JobDateET + "','" + CategoryET + "','" + SubCategoryET + "','" + loginEmail + "'";

                System.out.print("Insert stmt is:" + insert_stmt);

                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/insertJobDetails/" + insert_stmt);


            }
        });
    }
/*
    public String readJSONFeed(String URL) {
        System.out.println("Inside readJSON 1");
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            System.out.println("Inside TRY readJSON 2");
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Inside TRY WHILE readJSON 3");
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                System.out.println("status code not 200" + statusCode);
                Log.d("JSON", "status code not 200");
            }
        } catch (Exception e) {
            System.out.println("Failed to download file" + e);
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }
*/
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

    private class ReadWCFServiceFeed extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            wcf = new WebServiceCall();
            return wcf.readWCFServiceData(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {

                System.out.println("Input to onPost" + result);
                JSONObject jsonObject = new JSONObject(result);
               /* JSONObject weatherObservationItems =
                        new JSONObject(jsonObject.getString("weatherObservation"));*/

                /*Toast.makeText(getBaseContext(),
                        jsonObject.getString("email") +
                                " - " + jsonObject.getString("password"),
                        Toast.LENGTH_SHORT).show();*/
                System.out.println("JSON FEED FROM WS" + jsonObject.getString("msg")
                );
                if (jsonObject.getString("msg").equalsIgnoreCase("Inserted data")) {
                    System.out.print("before toast");

                    Toast.makeText(getBaseContext(), "User Created",
                            Toast.LENGTH_SHORT).show();

                   // Intent intent1 = new Intent(context, SignInActivity.class);
                     // intent1.putExtra("reg_email",loginEmail);
                    //startActivity(intent1);

                }

            } catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
            }
        }
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
}
