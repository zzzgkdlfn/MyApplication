package com.example.com.mydiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    //외부저장소 Path 가져옴
    final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();

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

        //앱 실행시 오늘날짜를 TextView에 보여줌.
        textDate.setText(cYear + "년 " + (cMonth+1) + "월 " + cDay + "일 ");
        //오늘 날짜를 파일이름으로 줌.
        fileName = Integer.toString(cYear) + "년_" + Integer.toString(cMonth+1) + "월_" + Integer.toString(cDay) + "일.txt";
        //처음에 해당날짜 일기 있으면 불러와서 EditText에 보여준다.
        edtDiary.setText(readDiary(fileName));

        final File myDir = new File(strSDpath + "/mydiary");
        //저장버튼 누르면 TextView에 보여지는 날짜에 해당하는 파일에 EditText내용 저장
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //myDir디렉터리 없으면 생성.
                    if(myDir.exists()==false)
                        myDir.mkdir();

                    //mydiary 밑에 해당날짜 txt파일 저장
                    File file = new File(strSDpath + "/mydiary/" + fileName);
                    //파일 생성후 씀
                    file.createNewFile();
                    FileOutputStream outFs = openFileOutput(strSDpath + "/mydiary/" + fileName, 0);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    Toast.makeText(getApplicationContext(), fileName+" 이 저장됨", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), fileName+" 저장 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //TextView터치하면 DatePicker위젯을 가지고있는 다이얼로그 나타남. 이 다이얼로그에서 날짜를 선택하고
        //확인버튼 누르면 해당날짜로 변경되고 일기가 존재하면 읽어옴.
       textDate.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog1, null);

               AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
               dp = (DatePicker) dialogView.findViewById(R.id.datePicker1);
               dlg.setTitle("날짜 선택");
               dlg.setView(dialogView);

               Calendar cal = Calendar.getInstance();
               int cYear = cal.get(Calendar.YEAR);
               int cMonth = cal.get(Calendar.MONTH);
               int cDay = cal.get(Calendar.DAY_OF_MONTH);
               dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
                   @Override
                   public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        //파일이름을 바뀐 날짜로 저장
                       fileName = Integer.toString(year) + "년_" + Integer.toString(monthOfYear + 1) + "월_" + Integer.toString(dayOfMonth) + "일.txt";
                   }
               });
               //확인 누르면
               dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //확인 누르면 testView내용 해당날짜로 변경하고 일기 읽어옴.
                       String str = Integer.toString(dp.getYear()) + "년 " + Integer.toString(dp.getMonth() + 1) + "월 " + Integer.toString(dp.getDayOfMonth()) + "일 ";
                       String str1 = readDiary(fileName);
                       edtDiary.setText(str1);
                       textDate.setText(str);
                   }
               });
               //취소는 아무것도 안함.
               dlg.setNegativeButton("취소",null);
               dlg.show();
               return false;
           }
       });
    }

    String readDiary(String fName) {


        //외부저장소
        String diaryStr = null;
        FileInputStream inFs;
        //Path가져옴.

        try{
            // mydiary밑에 파일이름 불러옴
//            inFs = new FileInputStream(strSDpath + "/mydiary/" + fName);
            inFs = new FileInputStream(strSDpath + "/diary/" +fName);
//            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inFs));
            byte[] txt = new byte[inFs.available()];
            inFs.read(txt);
            diaryStr = (new String(txt)).trim();
            inFs.close();
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

            //다시 읽으면 해당날짜 다시 읽어옴.
            case R.id.itemread:
                readDiary(fileName);
                Toast.makeText(getApplicationContext(), fileName + " 파일 다시 읽어옴.", Toast.LENGTH_SHORT).show();
                break;

            //삭제 클릭시
            case R.id.itemdelete:
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(MainActivity.this);
                dlg2.setTitle("삭제");
                dlg2.setMessage(fileName + "일기를 삭제하시겠습니까?");
                //확인클릭시 editText내용 삭제, 파일 삭제
                dlg2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edtDiary.setText("");
                        edtDiary.setHint("일기 삭제");
                        File file = new File(strSDpath + "/mydiary/" + fileName);
                        Toast.makeText(getApplicationContext(), fileName + " 파일 삭제하였습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
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
