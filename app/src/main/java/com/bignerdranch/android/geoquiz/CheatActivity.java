package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.get_answer_true";
    private static final String ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.get_answer_shown";



    public static Intent newIntent(Context packageContext,boolean answerIsTrue){

        Intent intent = new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(ANSWER_SHOWN, false);
    }
    private boolean mAnswerIsTrue;
    private TextView mAnswerText;
    private Button showAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);


        mAnswerText = (TextView) findViewById(R.id.show_answer_text);

        showAnswerButton = (Button) findViewById(R.id.show_answer_button);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerText.setText(R.string.true_button);
                }else{
                    mAnswerText.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });





    }

    public void setAnswerShownResult(boolean isAnswerShown){

        Intent data = new Intent();
        Log.d("Cheaat",Boolean.toString(isAnswerShown));
        data.putExtra(ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);





    }

}
