package edu.umkc.sm8xd.snag_job;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.schema.Person;

import java.util.EnumSet;

import edu.umkc.sm8xd.snag_job.LinkedinDialogActivity;
import edu.umkc.sm8xd.snag_job.LinkedinDialogActivity.OnVerifyListener;



/**
 * @author Vivek Kumar Srivastava
 */
public class LinkedInSampleActivity extends Activity
{
    private Button linkedInButton;
    private LinkedInApiClient client;
    private LinkedInApiClientFactory factory;
    final Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in_sample);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        }
        factory = LinkedInApiClientFactory.newInstance("78b2aphci42021","DzEJj5k2ptSsaEVP");
//        linkedInButton  = (Button) findViewById(R.id.linkedInbtn);
//
//        linkedInButton.setOnClickListener(new OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
                linkedInLogin();
//            }
//        });

    }

    /**
     * Connect IceBreaker with  linkedIn.
     * <br>
     * i.e. send  linkedIn access token to IceBreaker server.
     */
    private void linkedInLogin()
    {
        ProgressDialog progressDialog = new ProgressDialog(LinkedInSampleActivity.this);//.show(LinkedInSampleActivity.this, null, "Loadong...");

        LinkedinDialogActivity d = new LinkedinDialogActivity(LinkedInSampleActivity.this, progressDialog);
        d.show();

        //set call back listener to get oauth_verifier value
        d.setVerifierListener(new OnVerifyListener()
        {
            @Override
            public void onVerify(String verifier)
            {
                try
                {
                    Log.i("Enterd try block", "hiii");
                    Log.i("LinkedinSample", "verifier: " + verifier);

                    LinkedInAccessToken accessToken = LinkedinDialogActivity.oAuthService.getOAuthAccessToken(LinkedinDialogActivity.liToken, verifier);
                    System.out.print("After accesstoken");
                    LinkedinDialogActivity.factory.createLinkedInApiClient(accessToken);
                    System.out.print("After factory");


                    client = factory.createLinkedInApiClient(accessToken);
                    Person profile = client.getProfileForCurrentUser(EnumSet.of(ProfileField.ID, ProfileField.FIRST_NAME, ProfileField.LAST_NAME, ProfileField.HEADLINE,ProfileField.EMAIL_ADDRESS));
                    Log.i("First Name :: ",profile.getFirstName());
                    Log.i("Last Name :: ", profile.getLastName());
                    Log.i("Emailll addresss is :: ", profile.getEmailAddress());
                    Log.i("Head Line :: ", profile.getHeadline());
                    Log.i("Head Line :: ", profile.getHeadline());

                    Intent signinIntent = new Intent(context, SignInActivity.class);
                    signinIntent.putExtra("user_email",profile.getEmailAddress());
                    startActivity(signinIntent);



                    Log.i("LinkedinSample", "ln_access_token: " + accessToken.getToken());
                    Log.i("LinkedinSample", "ln_access_token: " + accessToken.getTokenSecret());
                }
                catch (Exception e)
                {
                    System.out.print("Got Exception : "+e);
                    Log.i("LinkedinSample", "error to get verifier");
                    e.printStackTrace();
                }
            }
        });

        //set progress dialog
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}