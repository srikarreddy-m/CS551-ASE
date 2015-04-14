package edu.umkc.sm8xd.snag_job;

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

import org.json.JSONArray;
import org.json.JSONObject;

import utility.WebServiceCall;


public class Accept_Reject_Application_Activity extends ActionBarActivity {
    String listItem;
    String email;
    Button applicationAcceptBtn;
    Button applicationRejectBtn;
    WebServiceCall wcf;
    String application_stmt;

    EditText emailET;
    EditText fNameET;
    EditText lNameET;
    EditText phNoET;
    EditText skillSetET;
    Boolean isFetch = false;
    Boolean isAccept = false;
    Boolean isReject = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept__reject__application_);
        listItem = getIntent().getStringExtra("ListItem");
        email = getIntent().getStringExtra("user_email");
        applicationAcceptBtn = (Button) findViewById(R.id.accept_reject_application_accept_btn);
        applicationRejectBtn = (Button) findViewById(R.id.accept_reject_application_reject_btn);

        emailET = (EditText) findViewById(R.id.accept_reject_application_email);
        fNameET = (EditText) findViewById(R.id.accept_reject_application_fname);
        lNameET = (EditText) findViewById(R.id.accept_reject_application_lname);
        phNoET = (EditText) findViewById(R.id.accept_reject_application_phno);
        skillSetET = (EditText) findViewById(R.id.accept_reject_application_applicant_skill_ET);

        final String[] listItemsSplit = listItem.split("\t" + "\t" + "\t" + "\t");

        isAccept = false;
        isReject = false;
        isFetch = true;

        new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/individualJobApplications/" + "SINGLE,"+listItemsSplit[0]+","+listItemsSplit[1]);
        System.out.println("After Web Service Call");

        applicationAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                System.out.println("Inside applicationAcceptBtn ");
                isAccept = true;
                isReject = false;
                isFetch = false;

                application_stmt ="UPDATE"+","+listItemsSplit[0]+","+listItemsSplit[1]+",A";

                System.out.println("application_stmt is: "+application_stmt);

                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/jobApplication/" + application_stmt);

            }
        });

        applicationRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                System.out.println("Inside applicationRejectBtn ");
                isAccept = false;
                isReject = true;
                isFetch = false;
                application_stmt ="UPDATE"+","+listItemsSplit[0]+","+listItemsSplit[1]+",R";

                System.out.println("application_stmt is: "+application_stmt);

                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/jobApplication/" + application_stmt);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accept__reject__application_, menu);
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
            if(isFetch){

                JSONArray jsonarray = new JSONArray(result);
                JSONObject jsonobject_application = new JSONObject();
                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject_application = jsonarray.getJSONObject(i);
                    emailET.setText(jsonobject_application.getString("Email"));
                    fNameET.setText(jsonobject_application.getString("FirstName"));
                    lNameET.setText(jsonobject_application.getString("LastName"));
                    phNoET.setText(jsonobject_application.getString("MobileNo"));
                    skillSetET.setText(jsonobject_application.getString("SkillSet"));

                }


            }

                if(isAccept){
                    Toast.makeText(getBaseContext(),
                            "Current Application is Accepted and the other Applications are Rejected",
                            Toast.LENGTH_SHORT).show();

                }

                if(isReject){
                    Toast.makeText(getBaseContext(),
                            "Current Application Rejected",
                            Toast.LENGTH_SHORT).show();

                }
           } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }

        }
    }
}
