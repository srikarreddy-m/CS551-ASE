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
import android.widget.Toast;

import org.json.JSONObject;

import utility.WebServiceCall;


public class ApplicationActivity extends ActionBarActivity {
    String jobID;
    String email;
    String firstName;
    String lastName;
    String mobileNo;
    String skillSet;
    Button nextApplicationBtn;
    String application_stmt;
    WebServiceCall wcf;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        jobID = getIntent().getStringExtra("Job_Id");
        email = getIntent().getStringExtra("user_email");
        System.out.println("jobID is"+jobID +"User Email is "+email);

        nextApplicationBtn = (Button) findViewById(R.id.application_next_btn);
        nextApplicationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                firstName = ((EditText) findViewById(R.id.application_fname)).getText().toString();
                lastName = ((EditText) findViewById(R.id.application_lname)).getText().toString();
                mobileNo = ((EditText) findViewById(R.id.application_phno)).getText().toString();
                skillSet = ((EditText) findViewById(R.id.application_applicant_skill_ET)).getText().toString();
                application_stmt = "INSERT,"+jobID+",'"+email+"','" + firstName + "','" + lastName + "'," + mobileNo + ",'" + skillSet+"'";

                System.out.print("Insert stmt is:" + application_stmt);

               new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/jobApplication/" + application_stmt);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_application, menu);
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
    //Web service call
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
                if (jsonObject.getString("msg").equalsIgnoreCase("updated data")) {

                    System.out.print("before toast");
                    Toast.makeText(getBaseContext(), "Application Submitted to Next Step",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, ExamActivity.class);
                 //   intent1.putExtra("reg_email", emailET.getText().toString());
                    startActivity(intent);

                }

            } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }
        }
    }
}
