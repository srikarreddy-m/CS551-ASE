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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utility.WebServiceCall;


public class ViewPostedJobsActivity extends ActionBarActivity {
    final Context context = this;
    String loginEmail = "";
    List<String> g_result ;
    String resultSevice="";
    String[] job_result = new String[10] ;
    WebServiceCall wcf;
    CheckBox Iscompleted ;
    CheckBox Ispending ;
    TextView id;
    TextView desc;
    Context obj;
    String stmt ="";

    ListView l ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posted_jobs);
        loginEmail = getIntent().getStringExtra("user_email");
        //stmt = loginEmail+"P,C";
        stmt =loginEmail+",P,C";
        System.out.println(stmt);
        Iscompleted = (CheckBox) findViewById(R.id.view_post_completed_chkbx);
        Ispending = (CheckBox) findViewById(R.id.view_post_pending_chkbx);
        l = (ListView) findViewById(R.id.view_post_listview);
        id = (TextView)findViewById(R.id.view_post_job_id_text);
        desc = (TextView)findViewById(R.id.view_post_job_desc_text);
        id.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);
//
//
        Button viewposts = (Button) findViewById(R.id.view_post_search_btn);
        obj = this;
        viewposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                id.setVisibility(View.VISIBLE);
                desc.setVisibility(View.VISIBLE);
          if (Iscompleted.isChecked() && Ispending.isChecked()) {
            stmt = loginEmail+",P,C";
              new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/retrieveJobDetails/"+stmt);
//
        }
        else if(Iscompleted.isChecked() && !Ispending.isChecked()) {
            stmt = loginEmail+",P,";
              new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/retrieveJobDetails/"+stmt);
        }
        else if(!Iscompleted.isChecked() && Ispending.isChecked()) {
            stmt = loginEmail+",,C";
              new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/retrieveJobDetails/"+stmt);
        }
        else if (!Iscompleted.isChecked() && !Ispending.isChecked()){
            //Clearing the before set view joblist
              List<String> clearlist = new ArrayList<String>() ;
              final StableArrayAdapter adapter = new StableArrayAdapter(obj,
                      android.R.layout.simple_list_item_1, clearlist);


              l.setAdapter(adapter);
            Toast.makeText(getApplicationContext(), "Please Click Atleast one CheckBox", Toast.LENGTH_LONG).show();


          }

        }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_posted_jobs, menu);
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

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private class ReadWCFServiceFeed extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            wcf = new WebServiceCall();
            return wcf.readWCFServiceData(urls[0]);

        }

        protected void onPostExecute(String result) {
            System.out.println("Onpost ecec result  is:" + result);

            resultSevice = result;
            System.out.println("Onpost ecec res service is:" + resultSevice);

            try {
                System.out.println("In try block result is: " + resultSevice);
                JSONArray jsonarray = new JSONArray(resultSevice);
                JSONObject jsonobject = new JSONObject();
                int n = jsonarray.length();
                //  String[] values = new String[n];
                g_result = new ArrayList<>();
                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject = jsonarray.getJSONObject(i);
                    g_result.add(jsonobject.getString("Id") +"\t"+"\t"+"\t"+"\t"+ jsonobject.getString("ShortDescription"));
                    System.out.println("Job id is::" + jsonobject.getString("Id"));
                    System.out.println("Object :" + i + "\t" + jsonobject);

                }
                System.out.println("Final list" + g_result);
                System.out.println("Context object is:" + obj);
                final StableArrayAdapter adapter = new StableArrayAdapter(obj,
                        android.R.layout.simple_list_item_1, g_result);


                l.setAdapter(adapter);

// not working list view listener
                l.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final String item = (String) parent.getItemAtPosition(position);
                        System.out.println("Item onclick is : " + item);
                        Intent intent = new Intent(context, ViewIndividualPostJobActivity.class);
                        String email = getIntent().getStringExtra("user_email");
                        System.out.println("Emaaill is: " + email);
                        intent.putExtra("user_email", email);
                        intent.putExtra("ListItem", item);
                        startActivity(intent);

                    }

                });
// End of list view listener

            } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }


        }
    }
}
