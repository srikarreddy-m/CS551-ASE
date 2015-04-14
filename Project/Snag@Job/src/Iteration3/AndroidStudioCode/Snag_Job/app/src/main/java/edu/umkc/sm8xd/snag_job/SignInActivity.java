package edu.umkc.sm8xd.snag_job;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SignInActivity extends ActionBarActivity {
    final Context context = this;
    public int responseCode = 0;
    public String message;
    public String response;
    String retrieve_stmt;
    String loginEmail;
    Button employer;
    Button applicant;
    Button employer_post;
    Button employer_view;
    Button applicant_search;
    Button applicant_view;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        loginEmail = getIntent().getStringExtra("user_email");
String emailSession = LoginActivity.getDefaults("user_email",context);
        System.out.println("Emaill is:::::from session ::"+emailSession);
        String RegEmail = getIntent().getStringExtra("reg_email");

        EditText email = (EditText) findViewById(R.id.ET_email_signin);


        email.append(loginEmail);

        if (null != loginEmail) {

            retrieve_stmt = loginEmail;
            System.out.println("Login Email is" + loginEmail);
        } else if (null != RegEmail) {
            retrieve_stmt = RegEmail;
            System.out.println("Reg Email is" + RegEmail);
        }


        System.out.println("Inside Button click Statement");
        System.out.println("retrieve_stmt" + retrieve_stmt);

        // new ReadWeatherJSONFeedTask().execute("http://10.0.2.2:60838/Service1.svc/retrieveLogin/"+retrieve_stmt);

        System.out.println("Inside Post Button  Statement");
//
        employer = (Button) findViewById(R.id.sign_in_employer_btn);
        applicant = (Button) findViewById(R.id.sign_in_applicant_btn);
        employer_post = (Button) findViewById(R.id.sign_in_postjob_btn);
        employer_view = (Button) findViewById(R.id.sign_in_view_postedjob_btn);
        applicant_search = (Button) findViewById(R.id.sign_in_searchjob_btn);
        applicant_view = (Button) findViewById(R.id.sign_in_viewappliedjobs_btn);
        employer_post.setVisibility(View.GONE);
        employer_view.setVisibility(View.GONE);
        applicant_search.setVisibility(View.GONE);
        applicant_view.setVisibility(View.GONE);
        System.out.println("Inside Post Button  Statement -After buttons declaration");


        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                employer_post.setVisibility(View.VISIBLE);
                employer_view.setVisibility(View.VISIBLE);
                applicant_search.setVisibility(View.GONE);
                applicant_view.setVisibility(View.GONE);
            }
        });
        applicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                applicant_search.setVisibility(View.VISIBLE);
                applicant_view.setVisibility(View.VISIBLE);
                employer_post.setVisibility(View.GONE);
                employer_view.setVisibility(View.GONE);

            }
        });

        employer_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                System.out.print("Clicked View Post button");
                Intent intent1 = new Intent(context,ViewPostedJobsActivity.class);
                intent1.putExtra("user_email", loginEmail);
                startActivity(intent1);
            }
        });
        employer_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.out.print("Clicked post button");
                Intent intent = new Intent(context, PostJobActivity.class);
                intent.putExtra("user_email", loginEmail);
                startActivity(intent);
            }
        });


        applicant_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, SearchJobActivity.class);
                intent.putExtra("user_email", loginEmail);
                startActivity(intent);
            }
        });

        applicant_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.out.print("Clicked applicant view  button");
//                Intent intent = new Intent(context, PostJobActivity.class);
//                intent.putExtra("user_email",loginEmail);
//                System.out.print("Emain in signin activit is::"+loginEmail);
//
//                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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


}
