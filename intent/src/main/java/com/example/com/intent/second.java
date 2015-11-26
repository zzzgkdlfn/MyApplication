package com.example.com.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Second 액티비티");

        Intent inIntent = getIntent();
        final String s = inIntent.getStringExtra("Str");
        final String num1 = inIntent.getStringExtra("Num1");
        final String num2 = inIntent.getStringExtra("Num2");

        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            Double res;

            @Override
            public void onClick(View v) {
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                int erck = 0;
                switch(s) {
                    case "더하기":
                        res = Double.parseDouble(num1) + Double.parseDouble(num2);
                        break;
                    case "빼기":
                        res = Double.parseDouble(num1) - Double.parseDouble(num2);
                        break;
                    case "곱하기":
                        res = Double.parseDouble(num1) * Double.parseDouble(num2);
                        break;
                    case "나누기":
                        if(Double.parseDouble(num2)==0) {
                            outIntent.putExtra("error", "에러");
                            erck =1;
                        }
                        res = Double.parseDouble(num1) / Double.parseDouble(num2);
                }
                outIntent.putExtra("Hap", res);
                if(erck==0) outIntent.putExtra("error","ok");
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });
    }

}
