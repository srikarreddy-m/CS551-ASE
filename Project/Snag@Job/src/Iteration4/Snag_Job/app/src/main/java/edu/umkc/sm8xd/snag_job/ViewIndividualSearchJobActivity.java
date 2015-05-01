package edu.umkc.sm8xd.snag_job;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import utility.WebServiceCall;


public class ViewIndividualSearchJobActivity extends ActionBarActivity {
    final Context context = this;
    String listItem;
    String email;
    WebServiceCall wcf;
    RelativeLayout editPostLayout;
    RelativeLayout viewPostLayout;
    Button editPost;
    Button viewApplications;
    Button saveButton;
    TextView category;
    TextView subCategory;
    TextView shortDesc;
    TextView skills;
    TextView postalAddress;
    TextView cityName;
    TextView zip;
    TextView rnr;
    TextView jobPay;
    TextView longDesc;
    String update_stmt="";
    Boolean isUpdate = false;
    Boolean isFetch = true;
    Button searchApplyJob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_search_job);
        listItem = getIntent().getStringExtra("ListItem");
        email = getIntent().getStringExtra("user_email");
        final String[] listItemsSplit = listItem.split("\t" + "\t" + "\t" + "\t");
        System.out.println("1st item : :" + listItemsSplit[0]);
        System.out.println("2nd item : :" + listItemsSplit[1]);
        System.out.println("email : :" + email);

        final String stmt1 = listItemsSplit[0] + "," + email;
        searchApplyJob = (Button) findViewById(R.id.view_individual_search_job_save_btn);

        new ReadWCFServiceFeed().execute("http://kc-sce-cs551.kc.umkc.edu/aspnet_client/Group5/project/Snag_Job/Service1.svc/retrieveIndividualJobDetail/" + stmt1);

        searchApplyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent1 = new Intent(context, ApplicationActivity.class);
                intent1.putExtra("user_email", email);
                intent1.putExtra("Job_Id",listItemsSplit[0]);
                startActivity(intent1);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_individual_search_job, menu);
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
                    System.out.print("Json object is::"+jsonobject);
                    category = (TextView) findViewById(R.id.view_individual_search_job_search_job_category);
                    subCategory = (TextView) findViewById(R.id.view_individual_search_job_search_job_subcategory);
                    shortDesc = (TextView) findViewById(R.id.view_individual_search_job_post_job_desc);
                    skills = (TextView) findViewById(R.id.view_individual_search_job_post_job_skill);
                    postalAddress = (TextView) findViewById(R.id.view_individual_search_job_post_address);
                    cityName = (TextView) findViewById(R.id.view_individual_search_job_search_job_city);
                    zip = (TextView) findViewById(R.id.view_individual_search_job_search_job_zip);
                    rnr = (TextView) findViewById(R.id.view_individual_search_job_post_job_rnr);
                    jobPay = (TextView) findViewById(R.id.view_individual_search_job_job_pay);
                    longDesc = (TextView) findViewById(R.id.view_individual_search_job_longjob_desc);
                    category.setText(jsonobject.getString("Category"));
                    subCategory.setText(jsonobject.getString("SubCategory"));
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
            } else if (isUpdate) {
                Toast.makeText(getBaseContext(),
                        "Job Post Successfully Updated",
                        Toast.LENGTH_SHORT).show();

            }

        }
    }
}
