package com.database.diarymanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditingPage extends AppCompatActivity {

    private String subject, title, date, des;

    private EditText Subject, Des;
    private TextView Date;
    private Button saveChangesButton;
    DatabaseManagement databaseManagement;
    Drawable themeMain, theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11;
    private LinearLayout mainBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_page);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        mainBackground = findViewById(R.id.main_background_id);
        Subject = findViewById(R.id.EditPageSubjectID);
        Date = findViewById(R.id.EditPageDateID);
        Des = findViewById(R.id.EditPageDesID);
        saveChangesButton = findViewById(R.id.EditPageSaveChangesID);
        databaseManagement = new DatabaseManagement(this);

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


        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            subject =bundle.getString("subject");
            date =bundle.getString("date");
            des =bundle.getString("description");
        }


        Subject.setText(subject);
        Date.setText(date);
        Des.setText(des);


        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uSubject = Subject.getText().toString();
                String uDate = Date.getText().toString();
                String uDes = Des.getText().toString();

                SQLiteDatabase sqLiteDatabase = databaseManagement.getWritableDatabase();

                long result = databaseManagement.editPageData(uSubject, uDate, uDes, subject);

                if(result != -1)
                {
                    Toast.makeText(EditingPage.this, "Data upadte Successful", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(EditingPage.this, "Data upadte Failed", Toast.LENGTH_SHORT).show();
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
