package com.example.com.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인 액티비티");

        Button btnNewActivity = (Button) findViewById(R.id.btnNewActivity);
        final RadioGroup rGroup1 = (RadioGroup) findViewById(R.id.rGroup1);

        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNum1 = (EditText) findViewById(R.id.edtNum1);
                EditText edtNum2 = (EditText) findViewById(R.id.edtNum2);

                Intent intent = new Intent(getApplicationContext(), second.class);
                switch(rGroup1.getCheckedRadioButtonId()) {
                    case R.id.RdoAdd:
                        intent.putExtra("Str","더하기");
                        break;
                    case R.id.RdoSub:
                        intent.putExtra("Str","빼기");
                        break;
                    case R.id.RdoMul:
                        intent.putExtra("Str","곱하기");
                        break;
                    case R.id.RdoDiv:
                        intent.putExtra("Str","나누기");
                        break;
                    default:
                        intent.putExtra("Str","");
                        break;
                }
                intent.putExtra("Num1", edtNum1.getText().toString());
                intent.putExtra("Num2", edtNum2.getText().toString());
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Double hap = data.getDoubleExtra("Hap", 0);
            String error = data.getStringExtra("error");
            if(error.equals("에러")) {
                Toast.makeText(getApplicationContext(), "0으로 나눌수 없습니다.", Toast.LENGTH_SHORT).show();
            }
             else
                Toast.makeText(getApplicationContext(), "결과 :" + hap, Toast.LENGTH_SHORT).show();

        }
    }
}
