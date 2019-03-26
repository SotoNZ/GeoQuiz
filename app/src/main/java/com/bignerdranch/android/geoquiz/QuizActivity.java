package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    public static final String TAG = "QuizActivity"; //The TAG is used to determine the source of the Log files croming
    //Fromt the onCreate Metjhod.
    public static final String KEY_INDEX = "index";
    public static final String TEXT_VIEW_KEY="index";
    public static final String SCORE_KEY = "index";
    public static final int CHEAT_KEY = 0;


    private Button mCheatButton;
    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mNextButton;
    private Button mBackButton;
    private TextView questionTextView;
    private TextView score_display;
    private boolean misCheater;
    private Questions[] mQuestionBank = new Questions[]{
            new Questions(R.string.africa_quest, true),
            new Questions(R.string.colo_quest, true),
            new Questions(R.string.lakes_quest, true),
            new Questions(R.string.ocean_quest, true),
    };



    public int currentQuestion;

    public int score;
    private double total = mQuestionBank.length;

    public int trueQuestion = 0;






    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        Log.d(TAG,"OnCreate Method Called");  //This will notify in the log files when the onCreate method is called.




        //Checks is the savedInstanceState exists and if it does gives it the question

        if(savedInstanceState != null){
            currentQuestion=savedInstanceState.getInt("currentQuestion");
            score = savedInstanceState.getInt("score");
            String boy = "You're taking the quiz again!";
            misCheater=savedInstanceState.getBoolean("cheater");
            trueQuestion=savedInstanceState.getInt("trueQuestion");
            if(boy.equals(savedInstanceState.getString(TEXT_VIEW_KEY))){
                questionTextView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
            }


        }


        setContentView(R.layout.activity_quiz);


        questionTextView = (TextView) findViewById(R.id.question_text);
        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int question = updateQuestion();
                questionTextView.setText(question);


            }

        });


        mNextButton = (Button) findViewById(R.id.next_button);



        questionTextView.setText(mQuestionBank[currentQuestion].getTextResId());


        //Sets up a variable for the true button declared at the top of the class.
        //findViewById: gets a referance to a widget by id.  R.id is used to get the id of the button.
        // Listner is needed to see when the button is clicked.  uses anamous class.




        mButtonTrue = (Button) findViewById(R.id.true_button);

        mButtonTrue.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {  //When the button is clicked evecute the method


                if(currentQuestion<=mQuestionBank.length-1) {
                    checkQuestion(true, currentQuestion);

                }
                disableButton();


            }
            });


        mButtonFalse = (Button) findViewById(R.id.false_button);
        mButtonFalse.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                //Toasts are used
                if(currentQuestion<=mQuestionBank.length-1) {
                    checkQuestion(false, currentQuestion);

                }
                disableButton();

            }


        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){



            public void onClick(View v){
                //Toasts are used
                trueQuestion+=1;
                if(trueQuestion<=mQuestionBank.length-1) {
                    misCheater = false;
                    int question = updateQuestion();
                    if (currentQuestion <= mQuestionBank.length - 1) {
                        enableButton();
                    }
                    questionTextView.setText(question);

                }else{
                    disableButton();
                }
            }


        });
        mBackButton = (Button) findViewById(R.id.back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int question = previousQuestion();

              if(currentQuestion <= mQuestionBank.length -1 ) {
                    enableButton();
                  questionTextView.setText(question);
                }else{
                  questionTextView.setText(question);
                    disableButton();
                }




            }
        });


        score_display = (TextView) findViewById(R.id.score_display);

        //score_display.setText(Double.toString(score));

        mCheatButton = (Button) findViewById(R.id.cheat_button);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAnswerTrue =mQuestionBank[currentQuestion].getAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,isAnswerTrue);
                startActivityForResult(intent,CHEAT_KEY);


            }
        });


    }



    public void disableButton(){
        mButtonTrue.setClickable(false);
        mButtonFalse.setClickable(false);

    }

    public void enableButton(){
        if(score<=100.0) {
            mButtonTrue.setClickable(true);
            mButtonFalse.setClickable(true);
        }
    }






    public int updateQuestion(){


        currentQuestion = (currentQuestion+1) % mQuestionBank.length; //Position in the array increases then is divided by modulus array length so that when it reaches the end of the array
        //FOr example 3, it will return to zero as 3%3==0;

        int question =  mQuestionBank[currentQuestion].getTextResId(); //Ueses the getter fron Questions to get the textId of the string at position currentQuestion.

        enableButton();

        return question; // returns a text id for the textview to reference;


    }
    public void checkQuestion(boolean userPressedTrue, int question){



             //Toasts

         Toast butter = Toast.makeText(QuizActivity.this ,R.string.correct_toast,Toast.LENGTH_SHORT);
        butter.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,460);


        Toast jam = Toast.makeText(QuizActivity.this ,R.string.false_toast,Toast.LENGTH_SHORT);
        jam.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,460);



        Toast ice = Toast.makeText(QuizActivity.this ,R.string.judjment_toast,Toast.LENGTH_SHORT);
        ice.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,460);

       // questionTextView.setText(Boolean.toString(misCheater));


        boolean correctAnswer = mQuestionBank[question].getAnswerTrue();

            if(misCheater){
                ice.show();
            }else {
                if (userPressedTrue == correctAnswer) {

                    butter.show(); // correct

                    score = checkScore(true);


                } else {

                    jam.show(); // wrong
                    score = checkScore(false);


                }

            }
            disableButton();


        }
        public int previousQuestion() {

            if (currentQuestion == 0) {
                currentQuestion = mQuestionBank.length - 1;

            } else {
                currentQuestion = currentQuestion - 1;
            }

            enableButton();
           // enableButton();
            return mQuestionBank[currentQuestion].getTextResId();
        }



        public int checkScore(boolean answer){

        double percentage = (score/total * 100);

        score_display.setText(Double.toString(percentage) + " %");
        Toast yingo = Toast.makeText(QuizActivity.this,Double.toString(score/total * 100) + "% on this Quiz",Toast.LENGTH_SHORT);
        boolean tester = false;
        //double finalP;
        if(answer) {
            score += 1;
            percentage = (score/total * 100);
            score_display.setText(Double.toString(percentage) + " %");
        }else{
            percentage = (score/total * 100);
            score_display.setText(Double.toString(percentage) + " %");
        }
        if (currentQuestion == mQuestionBank.length-1){
            tester=true;
            yingo.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,300);
            yingo.show();

            score_display.setText("You've completed thie quiz, congrats on your " + percentage + "%");
          //  finalP = percentage;

        }


            return score;
        }





        //        @Override makes sure the super class has the method you want to overriding from the superclass.
        @Override
        public void onStart(){
            super.onStart();
            Log.d(TAG, "onStart() called");

        }

        @Override

        public  void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");

        }


            @Override

            public void onPause(){
                super.onPause();
                 Log.d(TAG, "onPause() called");




                }

                @Override
                public void onRestoreInstanceState(Bundle savedInstanceState){

                score_display.setText(savedInstanceState.getString(TEXT_VIEW_KEY));



                }


            @Override
            public void onSaveInstanceState(Bundle savedInstanceState){
                Log.i(TAG,"onSaveInstanceState");
                Log.i(TAG,Integer.toString(currentQuestion));
                Log.i(TAG,Integer.toString(score));
                Integer score2=score;


                savedInstanceState.putInt("score",score2);
                savedInstanceState.putInt("currentQuestion",currentQuestion);
                savedInstanceState.putString(TEXT_VIEW_KEY, (String) score_display.getText()); // Test the text_view_key equal to whats isnide of score display
                savedInstanceState.putBoolean("cheater",misCheater);
                savedInstanceState.putInt("truQuestion",trueQuestion);


                Log.i(TAG,Integer.toString(currentQuestion));
                Log.i(TAG,Integer.toString(score));

                super.onSaveInstanceState(savedInstanceState);

                //putInt puts an integer in the bunble which can be used to store strings and integers.  Now
                //The current question is inside of the savedInstanceState Bundle when ever it's called
            }
             @Override

            public void onStop(){
                super.onStop();
                Log.d(TAG, "onStop() called");


            }
            @Override

            public void onDestroy(){
                super.onDestroy();
                Log.d(TAG, "onDestroy() called");
                }



                @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data){
            Log.d("quiz activity","test");
        /*
        if(requestCode != Activity.RESULT_OK){

            Log.d("quiz activity","test1");

            return;
                    }
                    if(resultCode == CHEAT_KEY){
                        if(data==null){
                            Log.d("quiz activity","test3");

                            return;
                        }

         */
                        Log.d("quiz activity","test4");

                        misCheater = CheatActivity.wasAnswerShown(data);
                        Log.d("quiz activity","test58");

                        Log.d("QuizAcitiy",Boolean.toString(misCheater));

                    }
                }





