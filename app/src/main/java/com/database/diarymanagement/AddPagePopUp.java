package com.database.diarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddPagePopUp extends AppCompatActivity {

    private TextView dateView;
    private Button selectDate;
    private Button submit;
    private String date;

    DatabaseManagement databaseManagement;


    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page_pop_up);


        selectDate = findViewById(R.id.SelectDateID);
        dateView = findViewById(R.id.SelectDateID);
        submit = findViewById(R.id.SubmitButtonID);

        databaseManagement = new DatabaseManagement(this);



        getSupportActionBar().hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.7), (int)(height*.27));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddPagePopUp.this, android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                String day = String.valueOf(dayOfMonth);

                if (day.length() == 1) {
                    day = "0" + day;
                }


                String monthString = String.valueOf(month+1);

                if (monthString.length() == 1) {
                    monthString = "0" + monthString;
                }

                date = day + "/" + (monthString) + "/" + year;
                dateView.setText(date);

            }
        };


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check = 0;
                Cursor cursor = databaseManagement.showdata();

                while (cursor.moveToNext())
                {
                    if (cursor.getString(3).equals(date))
                    {
                        check = 1;
                        break;
                    }
                }

                if (check == 0)
                {
                    Intent intent = new Intent(AddPagePopUp.this, AddingPage.class);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(AddPagePopUp.this, "You can not select this date", Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });

    }
}
