package com.database.diarymanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.DataInput;
import java.util.ArrayList;

public class DiaryIndex extends AppCompatActivity {

    private Button addPageButton;
    private Button todaySNote;
    private Button secretPage;
    private SearchView searchView;
    private String name;
    RecyclerView recyclerView;
    ArrayList<SaveDiary> dataholder;
    DatabaseManagement databaseManagement;
    SwipeRefreshLayout swipeRefreshLayout;
    private TextView nameTv;
    MyAdapter myAdapter;
    private ImageView themeImageView;
    Drawable themeMain, theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11;
    String subject, date, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_index);
        getSupportActionBar().hide();

        addPageButton = findViewById(R.id.addPageButtonID);
        todaySNote = findViewById(R.id.TodaySNoteID);
        secretPage = findViewById(R.id.SecretPageID);
        recyclerView = findViewById(R.id.RecyclerViewID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataholder = new ArrayList<>();
        databaseManagement = new DatabaseManagement(this);
        swipeRefreshLayout = findViewById(R.id.RefreshLayoutID);
        searchView = findViewById(R.id.SearchViewID);
        nameTv = findViewById(R.id.nameId);
        themeImageView = findViewById(R.id.theme_id);

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

        themeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

        final Cursor cursor = databaseManagement.showdata();
        while (cursor.moveToNext()) {
            SaveDiary saveDiary = new SaveDiary(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder.add(saveDiary);
        }

        myAdapter = new MyAdapter(DiaryIndex.this, dataholder);
        recyclerView.setAdapter(myAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                finish();
                Intent intent = new Intent(DiaryIndex.this, DiaryIndex.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        Cursor cursor1 = databaseManagement.NumberOfRow();

        while (cursor1.moveToNext()) {
            name = cursor1.getString(1);
            nameTv.setText(name + "'s " + "Personal Diary");
        }

        todaySNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DiaryIndex.this, TodaySNote.class);
                startActivity(intent);
            }
        });

        addPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DiaryIndex.this, AddPagePopUp.class);
                startActivity(intent);
            }
        });

        secretPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DiaryIndex.this);
                dialog.setContentView(R.layout.navigate_from_secret_page_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final EditText readEd, writeEd;
                final Button readButton, writeButton;
                final TextView forgetSecretWord, forgetSecretKey;

                readEd = dialog.findViewById(R.id.readSecretPage_id);
                readButton = dialog.findViewById(R.id.readSecretPage_button_id);
                writeEd = dialog.findViewById(R.id.writeSecretPage_id);
                writeButton = dialog.findViewById(R.id.writeSecretPage_button_id);
                forgetSecretWord = dialog.findViewById(R.id.forget_secret_word);
                forgetSecretKey = dialog.findViewById(R.id.forget_secret_key);

                forgetSecretWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DiaryIndex.this, FogetPasswordActivity.class);
                        intent.putExtra("forgetKey", 2);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                forgetSecretKey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DiaryIndex.this, FogetPasswordActivity.class);
                        intent.putExtra("forgetKey", 3);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                readButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String secretWord = null;
                        String readSecretWord = readEd.getText().toString();

                        if (readSecretWord.isEmpty()){
                            readEd.setError("Secret word required !");
                            readEd.setFocusable(true);
                            return;
                        }

                        Cursor result = databaseManagement.getKeyWord();

                        if (result != null) {
                            while (result.moveToNext()) {
                                secretWord = result.getString(4);
                                subject = result.getString(1);
                                date = result.getString(2);
                                description = result.getString(3);

                                if (secretWord.equals(readSecretWord)) {
                                    Intent intent = new Intent(DiaryIndex.this, ReadPage.class);
                                    intent.putExtra("sub", subject);
                                    intent.putExtra("date", date);
                                    intent.putExtra("des", description);
                                    intent.putExtra("id", "adapter");
                                    startActivity(intent);
                                    dialog.dismiss();
                                    break;
                                }
                                else {
                                    dialog.dismiss();
                                    final Dialog dialog1 = new Dialog(DiaryIndex.this);
                                    dialog1.setContentView(R.layout.show_password);
                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    TextView passwordTv = dialog1.findViewById(R.id.show_password_id);
                                    passwordTv.setText("Wrong Secret Word");
                                    Button ok = dialog1.findViewById(R.id.ok_button_id);
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog1.dismiss();
                                        }
                                    });
                                    dialog1.show();
                                }

                            }
                        }
                    }
                });

                writeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String writeSecretKey = writeEd.getText().toString();
                        if (writeSecretKey.isEmpty()){
                            writeEd.setError("Secret key required !");
                            writeEd.setFocusable(true);
                            return;
                        }
                        String dataSecretKey = databaseManagement.getSecretKey();

                        if (writeSecretKey.equals(dataSecretKey)) {
                            Intent intent = new Intent(DiaryIndex.this, SecretPage.class);
                            startActivity(intent);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            final Dialog dialog1 = new Dialog(DiaryIndex.this);
                            dialog1.setContentView(R.layout.show_password);
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            TextView passwordTv = dialog1.findViewById(R.id.show_password_id);
                            passwordTv.setText("Wrong Secret Key");
                            Button ok = dialog1.findViewById(R.id.ok_button_id);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog1.dismiss();
                                }
                            });
                            dialog1.show();
                        }
                    }

                });
                dialog.show();
            }
        });
    }

    private void setTheme() {
        final Dialog dialog = new Dialog(DiaryIndex.this);
        dialog.setContentView(R.layout.theme_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView themeMainDialog = dialog.findViewById(R.id.themeMain);
        ImageView theme1Dialog = dialog.findViewById(R.id.theme1);
        ImageView theme2Dialog = dialog.findViewById(R.id.theme2);
        ImageView theme3Dialog = dialog.findViewById(R.id.theme3);
        ImageView theme4Dialog = dialog.findViewById(R.id.theme4);
        ImageView theme5Dialog = dialog.findViewById(R.id.theme5);
        ImageView theme6Dialog = dialog.findViewById(R.id.theme6);
        ImageView theme7Dialog = dialog.findViewById(R.id.theme7);
        ImageView theme8Dialog = dialog.findViewById(R.id.theme8);
        ImageView theme9Dialog = dialog.findViewById(R.id.theme9);
        ImageView theme10Dialog = dialog.findViewById(R.id.theme10);
        ImageView theme11Dialog = dialog.findViewById(R.id.theme11);

        themeMainDialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(0);
                swipeRefreshLayout.setBackground(themeMain);
                setStatusBarColor(R.color.mainThemeColor, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme1Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(1);
                swipeRefreshLayout.setBackground(theme1);
                setStatusBarColor(R.color.Theme1Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme2Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(2);
                swipeRefreshLayout.setBackground(theme2);
                setStatusBarColor(R.color.Theme2Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme3Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(3);
                swipeRefreshLayout.setBackground(theme3);
                setStatusBarColor(R.color.Theme3Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme4Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(4);
                swipeRefreshLayout.setBackground(theme4);
                setStatusBarColor(R.color.Theme4Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme5Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(5);
                swipeRefreshLayout.setBackground(theme5);
                setStatusBarColor(R.color.Theme5Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme6Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(6);
                swipeRefreshLayout.setBackground(theme6);
                setStatusBarColor(R.color.Theme6Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme7Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(7);
                swipeRefreshLayout.setBackground(theme7);
                setStatusBarColor(R.color.Theme7Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme8Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(8);
                swipeRefreshLayout.setBackground(theme8);
                setStatusBarColor(R.color.Theme8Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme9Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(9);
                swipeRefreshLayout.setBackground(theme9);
                setStatusBarColor(R.color.Theme9Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme10Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(10);
                swipeRefreshLayout.setBackground(theme10);
                setStatusBarColor(R.color.Theme10Color, getApplicationContext());
                dialog.dismiss();
            }
        });

        theme11Dialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                long result = databaseManagement.updateTheme(11);
                swipeRefreshLayout.setBackground(theme11);
                setStatusBarColor(R.color.Theme11Color, getApplicationContext());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(DiaryIndex.this);
        dialog.setContentView(R.layout.select_dialog_box);
        TextView no = dialog.findViewById(R.id.NoID);
        TextView yes = dialog.findViewById(R.id.YesID);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        Cursor cursor = databaseManagement.getTheme();
        while (cursor.moveToNext()) {
            int theme = cursor.getInt(1);
            if (String.valueOf(theme).equals("0")) {
                swipeRefreshLayout.setBackground(themeMain);
                setStatusBarColor(R.color.mainThemeColor, this);
            } else if (String.valueOf(theme).equals("1")) {
                swipeRefreshLayout.setBackground(theme1);
                setStatusBarColor(R.color.Theme1Color, this);
            } else if (String.valueOf(theme).equals("2")) {
                swipeRefreshLayout.setBackground(theme2);
                setStatusBarColor(R.color.Theme2Color, this);
            } else if (String.valueOf(theme).equals("3")) {
                swipeRefreshLayout.setBackground(theme3);
                setStatusBarColor(R.color.Theme3Color, this);
            } else if (String.valueOf(theme).equals("4")) {
                swipeRefreshLayout.setBackground(theme4);
                setStatusBarColor(R.color.Theme4Color, this);
            } else if (String.valueOf(theme).equals("5")) {
                swipeRefreshLayout.setBackground(theme5);
                setStatusBarColor(R.color.Theme5Color, this);
            } else if (String.valueOf(theme).equals("6")) {
                swipeRefreshLayout.setBackground(theme6);
                setStatusBarColor(R.color.Theme6Color, this);
            } else if (String.valueOf(theme).equals("7")) {
                swipeRefreshLayout.setBackground(theme7);
                setStatusBarColor(R.color.Theme7Color, this);
            } else if (String.valueOf(theme).equals("8")) {
                swipeRefreshLayout.setBackground(theme8);
                setStatusBarColor(R.color.Theme8Color, this);
            } else if (String.valueOf(theme).equals("9")) {
                swipeRefreshLayout.setBackground(theme9);
                setStatusBarColor(R.color.Theme9Color, this);
            } else if (String.valueOf(theme).equals("10")) {
                swipeRefreshLayout.setBackground(theme10);
                setStatusBarColor(R.color.Theme10Color, this);
            } else if (String.valueOf(theme).equals("11")) {
                swipeRefreshLayout.setBackground(theme11);
                setStatusBarColor(R.color.Theme11Color, this);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setStatusBarColor(int color, Context context) {
        getWindow().setStatusBarColor(ContextCompat.getColor(context, color));
    }
}
