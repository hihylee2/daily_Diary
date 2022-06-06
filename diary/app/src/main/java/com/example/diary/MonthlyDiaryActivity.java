package com.example.diary;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MonthlyDiaryActivity extends AppCompatActivity {

    DatePicker dp;
    EditText et;
    Button btn_write;
    String fname;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_diary);
        dp = (DatePicker)findViewById(R.id.date_picker);
        et = (EditText)findViewById(R.id.et);
        btn_write = (Button)findViewById(R.id.btn_write);
        setDatePicker();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int ny = calendar.get(Calendar.YEAR);
        int nm = calendar.get(Calendar.MONTH);
        int nd = calendar.get(Calendar.DAY_OF_MONTH);
        DateChange(ny,nm,nd);

        dp.init(ny,nm,nd, new DatePicker.OnDateChangedListener(){
            public void onDateChanged(DatePicker view, int year, int month,int day){
                DateChange(year, month,day);
            }
        });
    }

    public void DateChange(int year, int month,int day){
        fname = Integer.toString(year)+"_"
                +Integer.toString(month+1)+"_"
                +Integer.toString(day)+".txt";
        readDiary(fname);
    }

    private RadioGroup rg;
    private ImageView img;



    void readDiary(String rfname){
        String str;
        FileInputStream fs_in;

        try{
            fs_in = openFileInput(rfname);
            StringBuffer sbuf = new StringBuffer();
            BufferedReader buf = new BufferedReader(new InputStreamReader(fs_in));
            str = buf.readLine();
            if(str == null){
                et.setText("");
                et.setHint("일기 없음");
                btn_write.setText("새로 저장");
                buf.close();
                return;
            }
            while(str != null){
                sbuf.append(str +"\n");
                str = buf.readLine();
            }
            et.setText(sbuf);
            buf.close();

            btn_write.setText("수정하기");
        }
        catch (IOException e){
            et.setText("");
            et.setHint("일기 없음");
            btn_write.setText("새로 저장");
        }

    }
    public void writeBtnOnClick(View view){
        try{
            FileOutputStream fs_out;
            fs_out = openFileOutput(fname, Context.MODE_APPEND);
            String str = et.getText().toString();
            PrintWriter  pw = new PrintWriter(fs_out);
            pw.println(str);
            pw.close();
        }
        catch (IOException e){

        }
    }
}