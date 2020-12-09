package com.example.museumapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton = (Button)findViewById(R.id.button);

        testButton.setOnClickListener(new View.OnClickListener() {
           @Override public void onClick(View v) {
               Toast t = Toast.makeText(getApplicationContext(),
                       "This is a positioned Toast message",
                       Toast.LENGTH_LONG);
               t.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
               t.show();
           }
        });
    }
}