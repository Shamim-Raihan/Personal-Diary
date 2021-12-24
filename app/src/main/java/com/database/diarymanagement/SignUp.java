package com.database.diarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {

    private TextInputEditText name;
    private TextInputEditText password;
    private TextInputEditText secretWord;
    private Button signUp;
    private TextInputEditText ans1Ed;
    private TextInputEditText ans2Ed;
    private TextInputEditText ans3Ed;
    private TextInputEditText ques4Ed;
    private TextInputEditText ans4Ed;
    DatabaseManagement databaseManagement;

    private String ques1 = "Your favourite game ?";
    private String ques2 = "Your favourite place?";
    private String ques3 = "Your favourite color?";
    private String ques4 = "";
    private String ans4 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        name = findViewById(R.id.UserNameID);
        password = findViewById(R.id.PasswordID);
        secretWord = findViewById(R.id.SecretWordID);
        signUp = findViewById(R.id.SignUpButonID);
        ans1Ed = findViewById(R.id.sign_up_ques_1_id);
        ans2Ed = findViewById(R.id.sign_up_ques_2_id);
        ans3Ed = findViewById(R.id.sign_up_ques_3_id);
        ques4Ed = findViewById(R.id.sign_up_ques_4_id);
        ans4Ed = findViewById(R.id.sign_up_ans_4_id);

        databaseManagement = new DatabaseManagement(this);
        SQLiteDatabase sqLiteDatabase = databaseManagement.getWritableDatabase();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Password = password.getText().toString();
                String Secret_word = secretWord.getText().toString().trim();
                String ans1 = ans1Ed.getText().toString();
                String ans2 = ans2Ed.getText().toString();
                String ans3 = ans3Ed.getText().toString();
                ques4 = ques4Ed.getText().toString();
                ans4 = ans4Ed.getText().toString();

                if (TextUtils.isEmpty(Name)) {
                    name.setError("Enter Username");
                    name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    password.setError("Enter Password");
                    password.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Secret_word)) {
                    secretWord.setError("Enter Secret Word");
                    secretWord.requestFocus();
                    return;
                }

                if (Secret_word.length() > 16) {
                    secretWord.setError("Secret Word should be 15 character");
                    secretWord.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(ans1)) {
                    ans1Ed.setError("Enter your ans");
                    ans1Ed.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(ans2)) {
                    ans2Ed.setError("Enter your ans");
                    ans2Ed.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(ans3)) {
                    ans3Ed.setError("Enter your ans");
                    ans3Ed.requestFocus();
                    return;
                }

                if (!ques4.isEmpty()) {
                    if (ans4.isEmpty()){
                        ans4Ed.setError("Enter your ans");
                        ans4Ed.requestFocus();
                        return;
                    }
                }

                QuestionModel questionModel = new QuestionModel(ques1, ans1);
                databaseManagement.insertQuesData(questionModel);
                QuestionModel questionModel1 = new QuestionModel(ques2, ans2);
                databaseManagement.insertQuesData(questionModel1);
                QuestionModel questionModel2 = new QuestionModel(ques3, ans3);
                databaseManagement.insertQuesData(questionModel2);
                QuestionModel questionModel3 = new QuestionModel(ques4, ans4);
                databaseManagement.insertQuesData(questionModel3);
                UserDetail userDetail = new UserDetail(Name, Password, Secret_word);
                long rowId = databaseManagement.InsertUserData(userDetail);
                if (rowId != -1) {
                    Intent intent = new Intent(SignUp.this, OpenDiary.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();




        Cursor cursor = databaseManagement.NumberOfRow();
        if (cursor.getCount() == 1) {

            Intent intent = new Intent(SignUp.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            long result = databaseManagement.insertThemeTableData(0);
        }
    }
}
