package com.database.diarymanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodaySNote extends AppCompatActivity {
    private EditText subject, description;
    private TextView date;
    private Button addPageButton;
    private String thisDate;
    DatabaseManagement databaseManagement;
    Drawable themeMain, theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11;
    private LinearLayout mainBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_snote);

        ActionBar ab = getSupportActionBar();
        ab.hide();
//        ab.setTitle("Today's note");



        subject = findViewById(R.id.SubjectID);
        description = findViewById(R.id.DescriptionID);
        date = findViewById(R.id.DateID);
        addPageButton = findViewById(R.id.TodaySavePageButtonID);
        mainBackground = findViewById(R.id.main_background_id);

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
        databaseManagement = new DatabaseManagement(this);
        SQLiteDatabase sqLiteDatabase = databaseManagement.getWritableDatabase();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        thisDate = currentDate.format(todayDate);
        date.setText(thisDate);

        addPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Subject = subject.getText().toString();
                String Description = description.getText().toString();
                if (TextUtils.isEmpty(Subject)) {
                    subject.setError("Enter Username");
                    subject.requestFocus();
                    return;
                }
                SaveDiary saveDiary = new SaveDiary(Subject, thisDate, Description);
                long rowId = databaseManagement.InsertUserDiaryData(saveDiary);
                if (rowId != -1)
                {
                    Intent intent = new Intent(TodaySNote.this, DiaryIndex.class);
                    startActivity(intent);
                }
                finish();
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
