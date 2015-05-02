package edu.umkc.sm8xd.snag_job;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SendEmailActivity extends ActionBarActivity {

    private EditText recipient;

    private EditText subject;

    private EditText body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        recipient = (EditText) findViewById(R.id.recipient);

        subject = (EditText) findViewById(R.id.subject);

        body = (EditText) findViewById(R.id.body);


        Button sendBtn = (Button) findViewById(R.id.sendEmail);

        sendBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                sendEmail();

                // after sending the email, clear the fields

                recipient.setText("");

                subject.setText("");

                body.setText("");

            }


        });

    }


    protected void sendEmail() {

        String[] recipients = {recipient.getText().toString()};
//
        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
//
//        // prompts email clients only
//
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL, recipients);

        email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());

        email.putExtra(Intent.EXTRA_TEXT, body.getText().toString());



        try {

            // the user can choose the email client

            startActivity(Intent.createChooser(email, "Choose an email client from..."));
//            Intent intent1 = new Intent(getApplicationContext(), SignInActivity.class);
//            intent1.putExtra("reg_email", email);
//            startActivity(intent1);


        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(SendEmailActivity.this, "No email client installed.",

                    Toast.LENGTH_LONG).show();

        }
        finally{


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_email, menu);
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
}
