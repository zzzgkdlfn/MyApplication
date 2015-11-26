package com.example.com.mydiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends Activity {

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
        int cMonth = cal.get(Calendar.MONTH) ;
        int cDay = cal.get(Calendar.DAY_OF_MONTH);


        textDate.setText(cYear + "년 " + (cMonth+1) + "월 " + cDay + "일 ");
        fileName = Integer.toString(cYear) + "년_" + Integer.toString(cMonth+1) + "월_" + Integer.toString(cDay) + "일.txt";

        //처음에 해당날짜 일기 있으면 불러옴.
        edtDiary.setText(readDiary(fileName));


        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

//                    FileOutputStream outFs = openFileOutput(fileName, 0);
                    //외부저장소 저장 (미완성)
                    String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File myDir = new File(strSDpath + "/mydiary");
                    //다이어리 폴더가 없으면 생성
                    myDir.mkdir();
                    //mydiary 밑에 해당날짜 txt파일 저장
                    FileOutputStream outFs = new FileOutputStream(strSDpath + "/mydiary/" + fileName);
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

                        fileName = Integer.toString(year) + "년_" + Integer.toString(monthOfYear + 1) + "월_" + Integer.toString(dayOfMonth) + "일.txt";
//                        String str = readDiary(fileName);
//                        edtDiary.setText(str);
//                        btnWrite.setEnabled(true);
//                        textDate.setText(Integer.toString(year) + "년 " + Integer.toString(monthOfYear + 1) + "월 " + Integer.toString(dayOfMonth) + "일 ");

                    }
                });
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = Integer.toString(dp.getYear()) + "년 " + Integer.toString(dp.getMonth()+1) + "월 " + Integer.toString(dp.getDayOfMonth()) + "일 ";
                        String str1 = readDiary(fileName);
                        edtDiary.setText(str1);
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


        //외부저장소
        String diaryStr = null;
        FileInputStream inFs;
        String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();

        try{
            // mydiary밑에 파일이름 불러옴
            inFs = new FileInputStream(strSDpath + "/mydiary/" + fName);
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
        super.onCreateOptionsMenu(menu);
        MenuInflater  mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.itemread:
                readDiary(fileName);
//                Toast.makeText(getApplicationContext(), fileName + " 파일 다시 읽어옴.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itemdelete:
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(MainActivity.this);
                dlg2.setTitle("삭제");
                dlg2.setMessage(fileName + "일기를 삭제하시겠습니까?");
                //삭제했을때 리스너 작성
                dlg2.setPositiveButton("확인", null);
                dlg2.setNegativeButton("취소", null);
                dlg2.show();

                break;

            //다이어리 내용을 버튼클릭에 따라 지정
            case R.id.itemBig:
                edtDiary.setTextSize(30);
                break;
            case R.id.itemNormal:
                edtDiary.setTextSize(20);
                break;
            case R.id.itemSmall:
                edtDiary.setTextSize(10);
                break;
        }
       return false;
    }
}
