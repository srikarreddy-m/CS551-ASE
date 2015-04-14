package edu.umkc.sm8xd.snag_job;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utility.WebServiceCall;


public class ViewIndividualPostJobActivity extends ActionBarActivity {
    final Context context = this;
    String listItem;
    String email;
    WebServiceCall wcf;
    RelativeLayout editPostLayout;
    RelativeLayout viewPostLayout;
    Button editPost;
    Button viewApplications;
    Button saveButton;
    Spinner category;
    Spinner subCategory;
    EditText shortDesc;
    EditText skills;
    EditText postalAddress;
    EditText cityName;
    EditText zip;
    EditText rnr;
    EditText jobPay;
    EditText longDesc;
    String update_stmt = "";
    String jobId;

    String resultSevice = "";
    List<String> g_result;
    Context obj;
    ListView l;

    Boolean isUpdate = false;
    Boolean isFetch = false;
    Boolean isViewApplication = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_post_job);
        listItem = getIntent().getStringExtra("ListItem");
        email = getIntent().getStringExtra("user_email");
        editPostLayout = (RelativeLayout) findViewById(R.id.edit_post_layout);
        viewPostLayout = (RelativeLayout) findViewById(R.id.view_application_layout);
        editPostLayout.setVisibility(View.GONE);
        viewPostLayout.setVisibility(View.GONE);
        editPost = (Button) findViewById(R.id.view_individual_post_edit_post_btn);
        viewApplications = (Button) findViewById(R.id.view_individual_post_applications_btn);
        saveButton = (Button) findViewById(R.id.view_individual_post_edit_post_save_btn);

        System.out.println("Short description is :" + listItem);
        System.out.println("Email : :" + email);

        final String[] listItemsSplit = listItem.split("\t" + "\t" + "\t" + "\t");
        System.out.println("1st item : :" + listItemsSplit[0]);
        System.out.println("1st item : :" + listItemsSplit[1]);
        jobId = listItemsSplit[0];
        final String stmt1 = listItemsSplit[0] + "," + email;
        l = (ListView) findViewById(R.id.view_applications_listview);
        obj = this;


        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                editPostLayout.setVisibility(View.VISIBLE);
                viewPostLayout.setVisibility(View.GONE);
                // saveButton.setVisibility(View.VISIBLE);
                isFetch = true;
                isUpdate = false;
                isViewApplication = false;
                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/retrieveIndividualJobDetail/" + stmt1);

            }
        });
        viewApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                System.out.println("View Applications OnClick");
                editPostLayout.setVisibility(View.GONE);
                viewPostLayout.setVisibility(View.VISIBLE);
                //saveButton.setVisibility(View.GONE);
                isFetch = false;
                isUpdate = false;
                isViewApplication = true;

                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/individualJobApplications/" + "LIST,"+jobId);

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                System.out.println("HIIIIIIIIIIIIIIIIII");
                //category  = (Spinner) findViewById(R.id.view_individual_post_edit_post_search_job_category);
                //subCategory  = (Spinner) findViewById(R.id.view_individual_post_edit_post_search_job_subcategory);
                shortDesc = (EditText) findViewById(R.id.view_individual_post_edit_post_post_job_desc);
                skills = (EditText) findViewById(R.id.view_individual_post_edit_post_post_job_skill);
                postalAddress = (EditText) findViewById(R.id.view_individual_post_edit_post_post_address);
                cityName = (EditText) findViewById(R.id.view_individual_post_edit_post_search_job_city);
                zip = (EditText) findViewById(R.id.view_individual_post_edit_post_search_job_zip);
                //rnr= (EditText)findViewById(R.id.view_individual_post_edit_post_post_job_rnr);
                jobPay = (EditText) findViewById(R.id.view_individual_post_edit_post_job_pay);
                longDesc = (EditText) findViewById(R.id.view_individual_post_edit_post_longjob_desc);


                String shortDescValue = shortDesc.getText().toString();
                String longDescValue = longDesc.getText().toString();
                String skillsValue = skills.getText().toString();
                String postalAddressValue = postalAddress.getText().toString();
                String cityNameValue = cityName.getText().toString();
                String zipValue = zip.getText().toString();
                //String rnrValue = rnr.getText().toString();
                String jobPayValue = jobPay.getText().toString();

                update_stmt = listItemsSplit[0] + "," + shortDescValue + "," + longDescValue + "," + skillsValue + "," + postalAddressValue + "," + cityNameValue + "," + zipValue + "," + jobPayValue + "," + "P";

                isUpdate = true;
                isFetch = false;
                isViewApplication = false;

                System.out.println("Update Statement is" + update_stmt);

                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/updatePostJobDetails/" + update_stmt);


            }
        });

        // new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/retrieveJobDetails/" + stmt);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //     getMenuInflater().inflate(R.menu.menu_view_individual_post_job_description, menu);individualJobDetail
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

    private class ReadWCFServiceFeed extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            wcf = new WebServiceCall();
            return wcf.readWCFServiceData(urls[0]);

        }

        protected void onPostExecute(String result) {
            System.out.println("Onpost ecec result  is:" + result);

            if (isFetch) {

                try {

                    System.out.println("In try block result is: " + result);

                    JSONObject jsonobject = new JSONObject(result);
                    category = (Spinner) findViewById(R.id.view_individual_post_edit_post_search_job_category);
                    subCategory = (Spinner) findViewById(R.id.view_individual_post_edit_post_search_job_subcategory);
                    shortDesc = (EditText) findViewById(R.id.view_individual_post_edit_post_post_job_desc);
                    skills = (EditText) findViewById(R.id.view_individual_post_edit_post_post_job_skill);
                    postalAddress = (EditText) findViewById(R.id.view_individual_post_edit_post_post_address);
                    cityName = (EditText) findViewById(R.id.view_individual_post_edit_post_search_job_city);
                    zip = (EditText) findViewById(R.id.view_individual_post_edit_post_search_job_zip);
                    rnr = (EditText) findViewById(R.id.view_individual_post_edit_post_post_job_rnr);
                    jobPay = (EditText) findViewById(R.id.view_individual_post_edit_post_job_pay);
                    longDesc = (EditText) findViewById(R.id.view_individual_post_edit_post_longjob_desc);
                    shortDesc.setText(jsonobject.getString("ShortDescription"));
                    skills.setText(jsonobject.getString("SkillSet"));
                    postalAddress.setText(jsonobject.getString("JobAddress"));
                    cityName.setText(jsonobject.getString("JobCity"));
                    zip.setText(jsonobject.getString("JobZipCode"));
                    //   rnr.append(jsonobject.getString("ShortDescription"));
                    jobPay.setText(jsonobject.getString("JobPay"));
                    longDesc.setText(jsonobject.getString("LongDescription"));


                } catch (Exception e) {
                    Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
                }
            }
            if (isUpdate) {
                Toast.makeText(getBaseContext(),
                        "Job Post Successfully Updated",
                        Toast.LENGTH_SHORT).show();

            }
            if (isViewApplication) {


                System.out.println("Onpost ecec result  is:" + result);

                resultSevice = result;
                System.out.println("Onpost ecec res service is:" + resultSevice);

                try {
                    System.out.println("In try block result is: " + resultSevice);
                    JSONArray jsonarray = new JSONArray(resultSevice);
                    JSONObject jsonobject_application = new JSONObject();
                    int n = jsonarray.length();
                    //  String[] values = new String[n];
                    g_result = new ArrayList<>();
                    for (int i = 0; i < jsonarray.length(); i++) {

                        jsonobject_application = jsonarray.getJSONObject(i);
                        g_result.add(jsonobject_application.getString("JobID")+ "\t" + "\t" + "\t" + "\t" +jsonobject_application.getString("Email") + "\t" + "\t" + "\t" + "\t" + jsonobject_application.getString("MobileNo"));
                        System.out.println("Email is::" + jsonobject_application.getString("Email"));
                        System.out.println("MobileNo is::" + jsonobject_application.getString("MobileNo"));
                        System.out.println("Object :" + i + "\t" + jsonobject_application);

                    }
                    System.out.println("Final list" + g_result);
                    System.out.println("Context object is:" + obj);
                    final StableArrayAdapter adapter = new StableArrayAdapter(obj,
                            android.R.layout.simple_list_item_1, g_result);
                    l.setAdapter(adapter);

                    l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            final String item = (String) parent.getItemAtPosition(position);
//
//                            Toast.makeText(getBaseContext(),
//                                    "Selected item"+item,
//                                    Toast.LENGTH_SHORT).show();
                            System.out.println("Item onclick is : " + item);
                          Intent intent = new Intent(context, Accept_Reject_Application_Activity.class);
                          //  String email = getIntent().getStringExtra("user_email");
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
}