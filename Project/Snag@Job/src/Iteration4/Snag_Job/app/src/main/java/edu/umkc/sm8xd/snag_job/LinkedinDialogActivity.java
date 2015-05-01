package edu.umkc.sm8xd.snag_job;

/**
 * Created by SrikarReddy on 3/8/2015.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;

import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;

import java.util.ArrayList;
import java.util.List;

public class LinkedinDialogActivity extends Dialog
{
    private ProgressDialog progressDialog = null;

    public static LinkedInApiClientFactory factory;
    public static LinkedInOAuthService oAuthService;
    public static LinkedInRequestToken liToken;

    public static final String LINKEDIN_CONSUMER_KEY = "78b2aphci42021";
    public static final String LINKEDIN_CONSUMER_SECRET = "DzEJj5k2ptSsaEVP";

    public static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
    public static final String OAUTH_CALLBACK_HOST = "callback";
    public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

    /**
     * Construct a new LinkedIn  dialog
     * @param context activity {@link android.content.Context}
     * @param progressDialog {@link android.app.ProgressDialog}
     */
    public LinkedinDialogActivity(Context context, ProgressDialog progressDialog)
    {
        super(context);
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//must call before super.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedin_dialog);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        }

        setWebView();
    }

    /**
     * set webview.
     */
    private void setWebView()
    {
        LinkedinDialogActivity.oAuthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(LINKEDIN_CONSUMER_KEY, LINKEDIN_CONSUMER_SECRET);
        LinkedinDialogActivity.factory = LinkedInApiClientFactory.newInstance(LINKEDIN_CONSUMER_KEY, LINKEDIN_CONSUMER_SECRET);

        LinkedinDialogActivity.liToken = LinkedinDialogActivity.oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);

        WebView mWebView = (WebView) findViewById(R.id.webkitWebView1);
        mWebView.getSettings().setJavaScriptEnabled(true);

        Log.i("LinkedinSample", LinkedinDialogActivity.liToken.getAuthorizationUrl());
        mWebView.loadUrl(LinkedinDialogActivity.liToken.getAuthorizationUrl());
        mWebView.setWebViewClient(new HelloWebViewClient());

        mWebView.setPictureListener(new PictureListener()
        {
            @Override
            public void onNewPicture(WebView view, Picture picture)
            {
                if(progressDialog != null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }

            }
        });

    }

    /**
     * webview client for internal url loading
     */
    class HelloWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if(url.contains(OAUTH_CALLBACK_URL))
            {
                Uri uri = Uri.parse(url);
                String verifier = uri.getQueryParameter("oauth_verifier");

                cancel();

                for(OnVerifyListener d : listeners)
                {
                    //call listener method
                    d.onVerify(verifier);
                }
            }
            else
            {
                Log.i("LinkedinSample", "url: "+url);
                view.loadUrl(url);
            }

            return true;
        }
    }

    /**
     * List of listener.
     */
    private List<OnVerifyListener> listeners = new ArrayList<OnVerifyListener>();

    /**
     * Register a callback to be invoked when authentication  have finished.
     *@param data The callback that will run
     */
    public void setVerifierListener(OnVerifyListener data)
    {
        listeners.add(data);
    }

    /**
     * Listener for oauth_verifier.
     */
    public interface OnVerifyListener
    {
        /**
         * invoked when authentication  have finished.
         * @param verifier oauth_verifier code.
         */
        public void onVerify(String verifier);
    }
}