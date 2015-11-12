package com.example.com.mydiary;

import android.content.DialogInterface;
import android.location.GpsStatus;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    TextView  textDate;
    EditText edtDiary;
    Button btnWrite;
    View dialogView;
    String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("일기장");

//        dp =(DatePicker) dialogView.findViewById(R.id.datePicker1);
        textDate = (TextView) findViewById(R.id.textDate);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);


        textDate.setText(cYear + "년 " + cMonth + "월 " + cDay + "일 ");
        fileName = Integer.toString(cYear) + "_" + Integer.toString(cMonth+1) + "_" + Integer.toString(cDay) + ".txt";
        edtDiary.setText(readDiary(fileName));

//        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                fileName = Integer.toString(year) + "_" + Integer.toString(monthOfYear + 1) + "_" + Integer.toString(dayOfMonth) + ".txt";
//                String str = readDiary(fileName);
//                edtDiary.setText(str);
//                btnWrite.setEnabled(true);
//                textDate.setText(Integer.toString(year) + "년 " + Integer.toString(monthOfYear + 1) + "월 " + Integer.toString(dayOfMonth) + "일 ");
//
//            }
//        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //일기 저장 External 로바꿔야함 폴더 mydiary에
                    FileOutputStream outFs = openFileOutput(fileName, 0);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    Toast.makeText(getApplicationContext(), fileName + " 이 저장됨", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {

                }
            }
        });

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog1, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder (MainActivity.this);
                dp =(DatePicker) dialogView.findViewById(R.id.datePicker1);
                dlg.setTitle("날짜 선택");
                dlg.setView(dialogView);

                Calendar cal = Calendar.getInstance();
                int cYear = cal.get(Calendar.YEAR);
                int cMonth = cal.get(Calendar.MONTH);
                int cDay = cal.get(Calendar.DAY_OF_MONTH);

                dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        fileName = Integer.toString(year) + "_" + Integer.toString(monthOfYear + 1) + "_" + Integer.toString(dayOfMonth) + ".txt";
                        String str = readDiary(fileName);
                        edtDiary.setText(str);
                        btnWrite.setEnabled(true);
//                        textDate.setText(Integer.toString(year) + "년 " + Integer.toString(monthOfYear + 1) + "월 " + Integer.toString(dayOfMonth) + "일 ");

                    }
                });
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = Integer.toString(dp.getYear()) + "년 " + Integer.toString(dp.getMonth()+1) + "월 " + Integer.toString(dp.getDayOfMonth()) + "일 ";
                        textDate.setText(str);
                    }
                });

                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlg.show();
            }
        });

    }

    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try{
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnWrite.setText("수정하기");
        }catch(IOException e) {
            edtDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }
        return diaryStr;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
