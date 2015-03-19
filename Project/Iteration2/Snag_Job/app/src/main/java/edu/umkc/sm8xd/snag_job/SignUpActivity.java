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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import utility.FieldValidation;
import utility.WebServiceCall;


public class SignUpActivity extends ActionBarActivity {

    View focusView = null;
    EditText emailET;
    EditText passwordET;
    EditText firstNameET;
    EditText lastNameET;
    EditText phnoET;
    EditText addressET;
    EditText stateET;
    EditText cityET;
    EditText zipCodeET;
    Context context;
    String insert_stmt = "";
    WebServiceCall wcf;
    // UI references.
    private AutoCompleteTextView mEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        emailET = (EditText) findViewById(R.id.email_signup);
        passwordET = (EditText) findViewById(R.id.pwd);
        firstNameET = (EditText) findViewById(R.id.fname);
        lastNameET = (EditText) findViewById(R.id.lname);
        phnoET = (EditText) findViewById(R.id.phno);
        addressET = (EditText) findViewById(R.id.usr_addr);
        stateET = (EditText) findViewById(R.id.usr_state);
        cityET = (EditText) findViewById(R.id.usr_city);
        zipCodeET = (EditText) findViewById(R.id.usr_zip);

        System.out.println("Before Insert Statement");

        Button mEmailSignUpButton = (Button) findViewById(R.id.usr_reg_btn);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                String email = emailET.getText().toString();
                // Get Password Edit View Value
                System.out.print("After email null check");
                String password = passwordET.getText().toString();
                // Get Password Edit View Value
                String firstName = firstNameET.getText().toString();
                // Get Password Edit View Value
                String lastName = lastNameET.getText().toString();
                // Get Password Edit View Value
                String phno = phnoET.getText().toString();
                // Get Password Edit View Value
                String address = addressET.getText().toString();
                // Get Password Edit View Value
                String city = cityET.getText().toString();
                // Get Password Edit View Value
                String state = stateET.getText().toString();

                // Get Password Edit View Value
                String zipCode = zipCodeET.getText().toString();
                System.out.print("outside textUtils if");
                System.out.print("R.string.error_field_required");

// Validation for email address
                if (!FieldValidation.isNotNull(email)) {
                    emailET.setError("This field is required");

                } else if (!FieldValidation.validate(email)) {

                    emailET.setError("Invalid email address :(");
                }

// Validation for password address
                if (!FieldValidation.isNotNull(password)) {


                    passwordET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(firstName)) {


                    firstNameET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(lastName)) {


                    lastNameET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(phno)) {


                    phnoET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(address)) {


                    addressET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(password)) {


                    cityET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(state)) {


                    stateET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(state)) {


                    stateET.setError("This field is required");

                }
                // Validation for password address
                if (!FieldValidation.isNotNull(zipCode)) {


                    zipCodeET.setError("This field is required");

                }


                insert_stmt = "'" + email + "','" + password + "','" + firstName + "','" + lastName + "'," + phno + ",'" + address + "','" + state + "','" + city + "'," + zipCode;

                System.out.println("Inside Button click Statement");
                System.out.println(insert_stmt);

                new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/insertUserDetails/" + insert_stmt);

                System.out.println("Inside Post Button  Statement");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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

                    Intent intent1 = new Intent(context, SignInActivity.class);
                    intent1.putExtra("reg_email", emailET.getText().toString());
                    startActivity(intent1);

                }

            } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }
        }
    }
    //Our code starts from here

    /**
     * Method gets triggered when Login button is clicked
     */


}


