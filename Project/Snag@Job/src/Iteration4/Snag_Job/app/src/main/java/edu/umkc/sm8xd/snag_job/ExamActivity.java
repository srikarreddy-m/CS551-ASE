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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import utility.WebServiceCall;


public class ExamActivity extends ActionBarActivity {

    WebServiceCall wcf;
    List<JSONObject> jsonQuestionsList;
    Button NextBtn;
    Button StartBtn;
    Button FinishBtn;
    RelativeLayout exam_subLayout;

    int n = 0;
    RadioGroup radioGroup;
    TextView question;
    TextView questionNumber;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    int examScore=0;
    String jobId;
    String application_stmt;
    String email;
    final Context context = this;
    Boolean isUpdate = false;
    Boolean isFetch = true;
    Integer answerIndex;
    int finalPercentage =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        jobId = getIntent().getStringExtra("job_Id");
        email = getIntent().getStringExtra("reg_email");
        new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/retrieveQuestions/Technical,Software");
//FOR UPDATE SCORE

       // final String[] listItemsSplit = listItem.split("\t" + "\t" + "\t" + "\t");
//
// System.out.println("Before BTN logic JSON Quesion List 0 is : " + jsonQuestionsList.get(0));
//        System.out.println("Before BTN logic JSON Quesion List 1 is : " + jsonQuestionsList.get(1));


        radioGroup =(RadioGroup) findViewById(R.id.exam_options);
        question = (TextView) findViewById(R.id.exam_question);
        questionNumber = (TextView) findViewById(R.id.exam_question_no);
        option1 = (RadioButton) findViewById(R.id.exam_question_option1);
        option1 = (RadioButton) findViewById(R.id.exam_question_option1);
        option2 = (RadioButton) findViewById(R.id.exam_question_option2);
        option3 = (RadioButton) findViewById(R.id.exam_question_option3);
        option4 = (RadioButton) findViewById(R.id.exam_question_option4);
        NextBtn = (Button) findViewById(R.id.exam_question_next_btn);
        FinishBtn = (Button) findViewById(R.id.exam_question_finish_btn);
        StartBtn = (Button) findViewById(R.id.exam_start_btn);
        exam_subLayout = (RelativeLayout)findViewById(R.id.exam_question_sub_layout);

        exam_subLayout.setVisibility(View.GONE);

        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                exam_subLayout.setVisibility(View.VISIBLE);
                n = 1;

                    try {

                        questionNumber.setText("" + n + ")");
                        question.setText(jsonQuestionsList.get(n-1).getString("Question"));
                        option1.setText(jsonQuestionsList.get(n-1).getString("Option1"));
                        option2.setText(jsonQuestionsList.get(n-1).getString("Option2"));
                        option3.setText(jsonQuestionsList.get(n-1).getString("Option3"));
                        option4.setText(jsonQuestionsList.get(n-1).getString("Option4"));
                        System.out.println("Inside START BTN logic JSON Quesion List 0 is : " + jsonQuestionsList.get(n-1));
                        // System.out.println("Inside BTN logic JSON Quesion List 1 is : " + jsonQuestionsList.get(1));
                        n++;
                        radioGroup.clearCheck();
                        StartBtn.setVisibility(View.GONE);
                    } catch (Exception e) {
                        System.out.print("Exception in next btn click is:" + e);
                    }
                }

        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.exam_question_option1:

                        answerIndex= new Integer(1);
                        break;

                    case R.id.exam_question_option2:
                     //   answerIndex =2;
                        answerIndex= new Integer(2);
                        break;

                    case R.id.exam_question_option3:
                      //  answerIndex = 3;
                        answerIndex= new Integer(3);
                        break;
                    case R.id.exam_question_option4:
                      //  answerIndex = 4;
                        answerIndex= new Integer(4);
                        break;
                }


            }
        });


        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    System.out.println("Anser index is" + answerIndex);
                    System.out.println("Correct answer is :" +Integer.valueOf(jsonQuestionsList.get(n - 2).getString("CorrectChoice")) );
                    if (answerIndex.equals(Integer.valueOf(jsonQuestionsList.get(n-2).getString("CorrectChoice")))){
                        System.out.println("Insdie if of anserindex" + examScore);
                        examScore++;

                    }
                    System.out.println("Current exm score is" + examScore);

                    StartBtn.setVisibility(View.GONE);
                    exam_subLayout.setVisibility(View.VISIBLE);
                    radioGroup.clearCheck();
                    if ((n - 1) < jsonQuestionsList.size()) {

                        questionNumber.setText("" + n + ")");
                        question.setText(jsonQuestionsList.get(n - 1).getString("Question"));
                        option1.setText(jsonQuestionsList.get(n - 1).getString("Option1"));
                        option2.setText(jsonQuestionsList.get(n - 1).getString("Option2"));
                        option3.setText(jsonQuestionsList.get(n - 1).getString("Option3"));
                        option4.setText(jsonQuestionsList.get(n - 1).getString("Option4"));
                        System.out.println("Inside  BTN logic JSON Quesion List 0 is : " + jsonQuestionsList.get(n - 1));
                        // System.out.println("Inside BTN logic JSON Quesion List 1 is : " + jsonQuestionsList.get(1));
                        n++;
                    }
                    else {
                        int listLength = jsonQuestionsList.size();

                            finalPercentage = Math.round((examScore*100)/listLength);

                            Toast.makeText(getBaseContext(),
                                    "Exam Successfully Completed"+finalPercentage,
                                    Toast.LENGTH_SHORT).show();

                        application_stmt ="UPDATESCORE"+","+jobId+","+email+","+finalPercentage;
                        Toast.makeText(getBaseContext(),
                                "application_stmt   "+application_stmt,
                                Toast.LENGTH_SHORT).show();

                        System.out.println("before update score ws call" + application_stmt);

                        isUpdate =true;
                        isFetch =false;

                        new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/jobApplication/" + application_stmt);
                        Intent intent1 = new Intent(context, SignInActivity.class);
                        intent1.putExtra("reg_email", email);
                        startActivity(intent1);

                        }
                }catch (Exception e) {
                        System.out.print("Exception in next btn click is:" + e);
                    }
                }

        });

        FinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                try {
                System.out.println("Anser index in finish button is" + answerIndex);
                System.out.println("Correct answer is :" +Integer.valueOf(jsonQuestionsList.get(n - 2).getString("CorrectChoice")) );
                if (answerIndex.equals(Integer.valueOf(jsonQuestionsList.get(n-2).getString("CorrectChoice")))){
                    System.out.println("Insdie if of anserindex" + examScore);
                    examScore++;

                }
                StartBtn.setVisibility(View.GONE);
                exam_subLayout.setVisibility(View.VISIBLE);
                int listLength = jsonQuestionsList.size();

                finalPercentage = Math.round(((examScore * 100) / listLength));

                    Toast.makeText(getBaseContext(),
                            "Exam Successfully Completed"+finalPercentage,
                            Toast.LENGTH_SHORT).show();

                    application_stmt ="UPDATESCORE"+","+jobId+","+email+","+finalPercentage;
                    Toast.makeText(getBaseContext(),
                            "application_stmt   "+application_stmt,
                            Toast.LENGTH_SHORT).show();
                    System.out.println("before update score ws call" + application_stmt);
                    isUpdate =true;
                    isFetch =false;

                    new ReadWCFServiceFeed().execute("http://10.0.2.2:60838/Service1.svc/jobApplication/" + application_stmt);
/*
                    Intent intent1 = new Intent(context, SignInActivity.class);
                    intent1.putExtra("reg_email", email);
                    startActivity(intent1);
*/
                }catch (Exception e) {
                    System.out.print("Exception in FinishBtn click is:" + e);
                }
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questions, menu);
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

        if(isFetch) {

            try {
                System.out.println("In try block result is: " + result);
                jsonQuestionsList = new ArrayList<JSONObject>();
                JSONArray jsonarray = new JSONArray(result);
                JSONObject jsonobject = new JSONObject();
                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject = jsonarray.getJSONObject(i);
                    System.out.println("JSON Quesion object is : " + jsonobject);
                    jsonQuestionsList.add(jsonobject);
                }
                System.out.println("JSON Quesion List 0 is : " + jsonQuestionsList.get(0));
                System.out.println("JSON Quesion List 1 is : " + jsonQuestionsList.get(1));

            } catch (Exception e) {
                Log.d("ReadWCFServiceFeed", e.getLocalizedMessage());
            }
        }if(isUpdate){
                Toast.makeText(getBaseContext(),
                        "Score Updated Successfully",
                        Toast.LENGTH_SHORT).show();
                    }


         }
    }
}
