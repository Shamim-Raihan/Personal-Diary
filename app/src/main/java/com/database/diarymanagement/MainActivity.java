package com.database.diarymanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {


    private EditText name;
    private EditText password;
    private Button logInButton;
    private Button logInWithFingerPrintButton;
    private TextView forgetPassword;
    private TextView errorAlert;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    DatabaseManagement databaseManagement;

    Drawable themeMain, theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11;
    private LinearLayout mainBackground;
    private ArrayList<QuestionModel> questionList;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        name = findViewById(R.id.LogInUsernameID);
        password = findViewById(R.id.LogInPasswordID);
        logInButton = findViewById(R.id.LogInButtonID);
        forgetPassword = findViewById(R.id.forgetPasswordID);
        logInWithFingerPrintButton = findViewById(R.id.LogInWithFingerPrintButtonID);
        errorAlert = findViewById(R.id.ErrorAlertID);
        mainBackground = findViewById(R.id.main_background_id);
        databaseManagement = new DatabaseManagement(this);
        questionList = new ArrayList<>();

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

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FogetPasswordActivity.class);
                intent.putExtra("forgetKey", 1);
                startActivity(intent);
            }
        });

        logInWithFingerPrintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricManager biometricManager = BiometricManager.from(MainActivity.this);
                switch (biometricManager.canAuthenticate()){
                    case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                        Toast.makeText(MainActivity.this, "Device does not have fingerprint", Toast.LENGTH_SHORT).show();
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                        Toast.makeText(MainActivity.this, "Fingerprint not working", Toast.LENGTH_SHORT).show();
                        break;
                    case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                        Toast.makeText(MainActivity.this, "No fingerprint assigned", Toast.LENGTH_SHORT).show();
                        break;
                }

                Executor executor = ContextCompat.getMainExecutor(MainActivity.this);
                biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(MainActivity.this, "Log in success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, OpenDiary.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });

                promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Personal Diary")
                        .setDescription("Use fingerprint to log in").setDeviceCredentialAllowed(true).build();
                biometricPrompt.authenticate(promptInfo);

            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString();
                String Password = password.getText().toString();

                if (TextUtils.isEmpty(Name))
                {
                    name.setError("Enter username");
                    name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Password))
                {
                    password.setError("Enter username");
                    password.requestFocus();
                    return;
                }


                Cursor cursor = databaseManagement.NumberOfRow();

                while (cursor.moveToNext())
                {
                    String DbName = cursor.getString(1);
                    String DbPassword = cursor.getString(2);
                    if (Name.equals(DbName) && Password.equals(DbPassword))
                    {
                        Intent intent = new Intent(MainActivity.this, OpenDiary.class);
                        startActivity(intent);
                        finish();
                        name.setText("");
                        password.setText("");
                        errorAlert.setVisibility(View.GONE);

                        break;
                    }
                    else
                    {
                        errorAlert.setVisibility(View.VISIBLE);
                    }
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



















