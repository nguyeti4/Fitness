package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

public class MainActivity3 extends AppCompatActivity {

    TextView Date;
    Button Calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Date = (TextView)findViewById(R.id.textView2);
        Intent intent = getIntent();
        String str = intent.getStringExtra("key");
        Date.setText(str);

        Calendar = (Button)findViewById(R.id.button3);
        Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,MainActivity2.class);
                startActivity(intent);
            }
        });


    }
}