package com.example.diary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SigninActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "34.64.44.187";
    private static String TAG = "platon backend";
    private EditText idInsert;
    private EditText pwInsert;
    private EditText pwVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        idInsert = (EditText)findViewById(R.id.idInsert);
        pwInsert = (EditText)findViewById(R.id.pwInsert);
        pwVerify = (EditText)findViewById(R.id.pwVerify);


        Button buttonRealSignup = (Button)findViewById(R.id.realSignupBtn);

        buttonRealSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String idInsert_s = idInsert.getText().toString();
                String pwInsert_s = pwInsert.getText().toString();
                String pwVerify_s = pwVerify.getText().toString();

                if (idInsert_s.equals("") ) {
                    String msg = "아이디를 입력하세요";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                } else if (pwInsert_s.equals("")){
                    String msg = "비밀번호를 입력하세요";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } else {

                    if (pwInsert_s.equals(pwVerify_s)){
                        InsertData task = new InsertData();
                        task.execute("http://" + IP_ADDRESS + "/signup.php", idInsert_s, pwInsert_s, pwVerify_s);

                        idInsert.setText("");
                        pwInsert.setText("");
                        pwVerify.setText("");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else {
                        String msg = "비밀번호가 일치하지 않습니다!";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }


                }




            }

        });

    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SigninActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String idInsert_s = (String)params[1];
            String pwInsert_s = (String)params[2];
            String pwVerify_s = (String)params[3];



            String serverURL = (String)params[0];
            String postParameters = "&idInsert_s=" + idInsert_s + "&pwInsert_s=" + pwInsert_s + "&pwVerify_s=" + pwVerify_s ;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}