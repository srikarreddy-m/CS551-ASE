package edu.umkc.sm8xd.snag_job;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import utility.WebServiceCall;


public class ViewIndividualAppliedJobActivity extends ActionBarActivity {

    String item;

    String loginEmail;
    String stmt = "";
    WebServiceCall wcf;

    TextView shortDesc;
    TextView skillSet;


    TextView email;

    TextView fname;
    TextView lname;
    TextView mobile;
    TextView postalAddress;
    TextView cityName;
    TextView zip;
    TextView jobPay;
    TextView jobStatus;
    TextView applicantStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_applied_job);

        item = getIntent().getStringExtra("ListItem");
        loginEmail = getIntent().getStringExtra("user_email");

        String[] words = item.split("\t" + "\t" + "\t" + "\t");

        stmt = words[0] + "," + words[1] + "," + words[2];

        System.out.println("Item in individual applied jobs: "+item);
        System.out.println("Item 1: "+words[0]);
        System.out.println("Item 2: "+words[1]);
        System.out.println("Item 3: "+words[2]);

        new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/retrieveApplicationJobDetails/" + stmt);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_individual_applied_job, menu);
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
            System.out.println("Onpost ecec result  is:" + result);


            try {

                System.out.println("In try block result is: " + result);

                JSONObject jsonobject = new JSONObject(result);

                email = (TextView) findViewById(R.id.view_individual_applied_job_email);
                fname = (TextView) findViewById(R.id.view_individual_applied_job_fname);
                lname = (TextView) findViewById(R.id.view_individual_applied_job_lname);
                mobile = (TextView) findViewById(R.id.view_individual_applied_job_phno);
                skillSet = (TextView) findViewById(R.id.view_individual_applied_job_applicant_skill_ET);
                applicantStatus = (TextView) findViewById(R.id.view_individual_applied_job_status_text_ET);
                shortDesc = (TextView) findViewById(R.id.view_individual_applied_job_post_job_desc);
                //email = (TextView) findViewById(R.id.view_individual_applied_job_post_job_skill);
                postalAddress = (TextView) findViewById(R.id.view_individual_applied_job_post_address);
                cityName = (TextView) findViewById(R.id.view_individual_applied_job_city);
                zip = (TextView) findViewById(R.id.view_individual_applied_job_zip);
                jobStatus = (TextView) findViewById(R.id.view_individual_applied_job_post_job_rnr);
                jobPay = (TextView) findViewById(R.id.view_individual_applied_job_job_pay);

                email.setText(jsonobject.getString("Email"));
                fname.setText(jsonobject.getString("FirstName"));
                lname.setText(jsonobject.getString("LastName"));
                mobile.setText(jsonobject.getString("MobileNo"));
                skillSet.setText(jsonobject.getString("SkillSet"));
                applicantStatus.setText(jsonobject.getString("Status"));
                shortDesc.setText(jsonobject.getString("ShortDescription"));
                jobStatus.setText(jsonobject.getString("JobStatus"));

                postalAddress.setText(jsonobject.getString("JobAddress"));
                cityName.setText(jsonobject.getString("JobCity"));
                zip.setText(jsonobject.getString("JobZipCode"));

                jobPay.setText(jsonobject.getString("JobPay"));


            } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }
        }
    }
}
