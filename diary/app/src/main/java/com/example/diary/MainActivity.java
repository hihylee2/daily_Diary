package com.example.diary;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "34.64.44.187";
    private static String TAG = "platon backend";

    private EditText id;
    private EditText pw;

    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID = "id";
    private static final String TAG_pw = "pw";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText)findViewById(R.id.idInput);
        pw = (EditText)findViewById(R.id.passwordInput);



        Button buttonInsert = (Button)findViewById(R.id.SignupBtn);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id_s = id.getText().toString();
                String pw_s = pw.getText().toString();

                if (id_s.equals("") ) {
                    String msg = "아이디를 입력하세요";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                } else if (pw_s.equals("")){
                    String msg = "비밀번호를 입력하세요";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
                else {
                    InsertData task = new InsertData();
                    task.execute("http://" + IP_ADDRESS + "/insert.php", id_s, pw_s);

                    id.setText("");
                    pw.setText("");

                    Intent intent = new Intent(getApplicationContext(), MonthlyDiaryActivity.class);
                    startActivity(intent);

                    //----------------------------------

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                Toast.makeText(getApplicationContext(), "1단계 성공", Toast.LENGTH_LONG).show();

                                if (success) {

                                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext(), MonthlyDiaryActivity.class);

                                    intent.putExtra("id_s", id_s);
                                    intent.putExtra("pw_s", pw_s);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }};
                            //---------------------------------
//                    Intent intent = new Intent(getApplicationContext(), MonthlyDiaryActivity.class);
//                    startActivity(intent);

                }


            }
        });

        Button buttonSign = (Button)findViewById(R.id.signinBtn);
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
            }
        });

    }


    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
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

            String id_s = (String)params[1];
            String pw_s = (String)params[2];



            String serverURL = (String)params[0];
            String postParameters = "&id_s=" + id_s + "&pw_s=" + pw_s ;


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