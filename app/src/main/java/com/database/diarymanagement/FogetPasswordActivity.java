package com.database.diarymanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FogetPasswordActivity extends AppCompatActivity {

    private TextView qus1Tv, qus2Tv, qus3Tv, qus4Tv;
    private EditText ans1Ed, ans2Ed,ans3Ed, ans4Ed;
    private Button submitButton;
    DatabaseManagement databaseManagement;
    private ArrayList<QuestionModel> questionList;
    private int length;
    private String databaseAns1, databaseAns2, databaseAns3, databaseAns4;
    private int count = 0;
    private String quesNumberChecker;
    Drawable themeMain, theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11;
    private LinearLayout mainBackground;
    private int forgetKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_password);
        getSupportActionBar().hide();
        mainBackground = findViewById(R.id.main_background_id);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            forgetKey = bundle.getInt("forgetKey");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            themeMain = getDrawable(R.drawable.theme_main);
            theme1 = getDrawable(R.drawable.theme_1);
            theme2 = getDrawable(R.drawable.theme_2);
            theme3 = getDrawable(R.drawable.theme_3);
            theme4 = getDrawable(R.drawable.theme_4);
            theme5 = getDrawable(R.drawable.theme_5);
            theme6 = getDrawable(R.drawable.theme_6);
            theme7 = getDrawable(R.drawable.theme_7);
            theme8 = getDrawable(R.drawable.theme_8);
            theme9 = getDrawable(R.drawable.theme_9);
            theme10 = getDrawable(R.drawable.theme_10);
            theme11 = getDrawable(R.drawable.theme_11);
        }

        qus1Tv = findViewById(R.id.qus1_id);
        qus2Tv = findViewById(R.id.qus2_id);
        qus3Tv = findViewById(R.id.qus3_id);
        qus4Tv = findViewById(R.id.qus4_id);

        ans1Ed = findViewById(R.id.ans1_id);
        ans2Ed = findViewById(R.id.ans2_id);
        ans3Ed = findViewById(R.id.ans3_id);
        ans4Ed = findViewById(R.id.ans4_id);

        questionList = new ArrayList<>();
        submitButton = findViewById(R.id.submit_button_id);
        databaseManagement = new DatabaseManagement(this);
        Cursor result = databaseManagement.getAllQuestion();
        if (result != null){
            while (result.moveToNext()){
                QuestionModel questionModel = new QuestionModel(result.getString(1), result.getString(2));
                questionList.add(questionModel);
            }
        }

        length = questionList.size();

        quesNumberChecker = questionList.get(3).getQues();


        if (quesNumberChecker.equals("")){
            qus4Tv.setVisibility(View.GONE);
            ans4Ed.setVisibility(View.GONE);

            qus1Tv.setText(questionList.get(0).getQues());
            qus2Tv.setText(questionList.get(1).getQues());
            qus3Tv.setText(questionList.get(2).getQues());

            databaseAns1 = questionList.get(0).getAns();
            databaseAns2 = questionList.get(1).getAns();
            databaseAns3 = questionList.get(2).getAns();
        }

        if (!quesNumberChecker.equals("")){
            qus1Tv.setText(questionList.get(0).getQues());
            qus2Tv.setText(questionList.get(1).getQues());
            qus3Tv.setText(questionList.get(2).getQues());
            qus4Tv.setText(questionList.get(3).getQues() + "(your own Question)");

            databaseAns1 = questionList.get(0).getAns();
            databaseAns2 = questionList.get(1).getAns();
            databaseAns3 = questionList.get(2).getAns();
            databaseAns4 = questionList.get(3).getAns();
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputAns1 = ans1Ed.getText().toString();
                String inputAns2 = ans2Ed.getText().toString();
                String inputAns3 = ans3Ed.getText().toString();
                String inputAns4;

                if (quesNumberChecker.equals("")){
                    if (databaseAns1.equals(inputAns1)){
                        count++;
                    }
                    if (databaseAns2.equals(inputAns2)){
                        count++;
                    }
                    if (databaseAns3.equals(inputAns3)){
                        count++;
                    }
                }

                else if (!quesNumberChecker.equals("")){
                    inputAns4 = ans4Ed.getText().toString();
                    if (databaseAns1.equals(inputAns1)){
                        count++;
                    }
                    if (databaseAns2.equals(inputAns2)){
                        count++;
                    }
                    if (databaseAns3.equals(inputAns3)){
                        count++;
                    }

                    if (databaseAns4.equals(inputAns4)){
                        count++;
                    }
                }

                if (count >=2){
                    String password = null;
                    if (forgetKey == 1){
                        Cursor result = databaseManagement.getPassword();

                        if (result != null){
                            while (result.moveToNext()){
                                password = result.getString(2);
                            }
                        }
                        final Dialog dialog = new Dialog(FogetPasswordActivity.this);
                        dialog.setContentView(R.layout.show_password);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView passwordTv = dialog.findViewById(R.id.show_password_id);
                        passwordTv.setText("Your password is \n            " + password);
                        Button ok = dialog.findViewById(R.id.ok_button_id);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                    if (forgetKey == 2){
                        Cursor cursor = databaseManagement.getAllSectetWord();
                        if (cursor != null){
                            StringBuilder stringBuilder = new StringBuilder();
                            while (cursor.moveToNext()){
                                stringBuilder.append("Subject : " + cursor.getString(1) + "    ");
                                stringBuilder.append("Key word : " + cursor.getString(4));
                                stringBuilder.append("\n");
                                stringBuilder.append("\n");
                            }
                            final Dialog dialog = new Dialog(FogetPasswordActivity.this);
                            dialog.setContentView(R.layout.show_secret_word_layout);
                            TextView secretWord = dialog.findViewById(R.id.show_secret_word_id);
                            secretWord.setText(stringBuilder);
                            dialog.show();
                        }

                    }

                    if (forgetKey == 3){
                        String getSecretKey = databaseManagement.getSecretKey();
                        final Dialog dialog = new Dialog(FogetPasswordActivity.this);
                        dialog.setContentView(R.layout.show_password);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView passwordTv = dialog.findViewById(R.id.show_password_id);
                        passwordTv.setText("Your secret key is \n            " + getSecretKey);
                        Button ok = dialog.findViewById(R.id.ok_button_id);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }

                    count = 0;


                }
                else if(count < 2){
                    final Dialog dialog = new Dialog(FogetPasswordActivity.this);
                    dialog.setContentView(R.layout.show_password);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView passwordTv = dialog.findViewById(R.id.show_password_id);
                    passwordTv.setText("Please try again");
                    Button ok = dialog.findViewById(R.id.ok_button_id);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        Cursor cursor = databaseManagement.getTheme();
        while (cursor.moveToNext()){
            int theme = cursor.getInt(1);
            SetTheme setTheme = new SetTheme(mainBackground, theme, this, themeMain, theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11);
            setTheme.setThemeAll(getWindow());
        }
    }
}














