package com.database.diarymanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class ReadPage extends AppCompatActivity {

    private TextView subject, date, des;
    private String Sub, Date, Title, Des, id, Dsub, Key;
    private int check = 0;

    DatabaseManagement databaseManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_page);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        subject = findViewById(R.id.ReadSubjectID);
        date = findViewById(R.id.ReadDateID);
        des = findViewById(R.id.ReadDesID);

        databaseManagement = new DatabaseManagement(this);
        SQLiteDatabase sqLiteDatabase = databaseManagement.getWritableDatabase();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            Sub = bundle.getString("sub");
            Date = bundle.getString("date");
            Des = bundle.getString("des");
            id = bundle.getString("id");
        }

        if (id.equals("adapter"))
        {
            subject.setText(Sub);
            date.setText(Date);
            des.setText(Des);
        }

        if (id.equals("searchItem")) {
            Cursor cursor = databaseManagement.showdata();
            while (cursor.moveToNext()) {
                Dsub = cursor.getString(1);
                Date = cursor.getString(2);
                Des = cursor.getString(3);
                if (Dsub.equals(Sub)) {
                    check = 1;
                    break;
                }
            }

            if (check == 1)
            {
                subject.setText(Sub);
                date.setText(Date);
                des.setText(Des);
            }

            else
            {
                Cursor cursor1 = databaseManagement.showdata1();

                while (cursor1.moveToNext()) {
                    Dsub = cursor1.getString(1);
                    Date = cursor1.getString(2);
                    Des = cursor1.getString(3);
                    Key = cursor1.getString(4);

                    if (Key.equals(Sub)) {
                        subject.setText(Dsub);
                        date.setText(Date);
                        des.setText(Des);
                        break;
                    }
                }
            }
        }
    }
}
