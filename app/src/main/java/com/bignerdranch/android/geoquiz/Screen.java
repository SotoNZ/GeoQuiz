package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Bundle;

public class Screen extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_activity);



    Intent intent = getIntent();
    //String message = intent.getStringExtra(mystring);
    TextView textViewScreen = findViewById(R.id.screenTextView);


  //  textViewScreen.setText(message);





    }


}
