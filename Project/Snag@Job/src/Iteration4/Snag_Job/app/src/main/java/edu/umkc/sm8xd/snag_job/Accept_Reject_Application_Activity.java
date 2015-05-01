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
import android.widget.TextView;
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

    TextView emailText;
    TextView fNameText;
    TextView lNameText;
    TextView phNoText;
    TextView skillSetText;
    Boolean isFetch = false;
    Boolean isAccept = false;
    Boolean isReject = false;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept__reject__application_);
        listItem = getIntent().getStringExtra("ListItem");
        email = getIntent().getStringExtra("user_email");
        applicationAcceptBtn = (Button) findViewById(R.id.accept_reject_application_accept_btn);
        applicationRejectBtn = (Button) findViewById(R.id.accept_reject_application_reject_btn);

        emailText = (TextView) findViewById(R.id.accept_reject_application_email);
        fNameText = (TextView) findViewById(R.id.accept_reject_application_fname);
        lNameText = (TextView) findViewById(R.id.accept_reject_application_lname);
        phNoText = (TextView) findViewById(R.id.accept_reject_application_phno);
        skillSetText = (TextView) findViewById(R.id.accept_reject_application_applicant_skill_ET);

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

                application_stmt ="UPDATEJOBSTATUS"+","+listItemsSplit[0]+","+listItemsSplit[1]+",A";

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
                application_stmt ="UPDATEJOBSTATUS"+","+listItemsSplit[0]+","+listItemsSplit[1]+",R";

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
                    emailText.setText(jsonobject_application.getString("Email"));
                    fNameText.setText(jsonobject_application.getString("FirstName"));
                    lNameText.setText(jsonobject_application.getString("LastName"));
                    phNoText.setText(jsonobject_application.getString("MobileNo"));
                    skillSetText.setText(jsonobject_application.getString("SkillSet"));

                }


            }

                if(isAccept){
                    Toast.makeText(getBaseContext(),
                            "Current Application is Accepted and the other Applications are Rejected",
                            Toast.LENGTH_SHORT).show();
/*

WebService Call to update application status
 */

                    Intent intent1 = new Intent(context, SignInActivity.class);
                    intent1.putExtra("reg_email", email);
                    startActivity(intent1);

                }

                if(isReject){
                    Toast.makeText(getBaseContext(),
                            "Current Application Rejected",
                            Toast.LENGTH_SHORT).show();
/*

WebService Call to update application status
 */


                    Intent intent1 = new Intent(context, SignInActivity.class);
                    intent1.putExtra("reg_email", email);
                    startActivity(intent1);

                }
           } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }

        }
    }
}
