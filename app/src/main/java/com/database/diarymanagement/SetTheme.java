package com.database.diarymanagement;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

public class SetTheme {
    private LinearLayout layout;
    private int theme;
    private Context context;
    Drawable themeMain, theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11;


    public SetTheme(LinearLayout layout, int theme, Context context, Drawable themeMain, Drawable theme1, Drawable theme2, Drawable theme3, Drawable theme4, Drawable theme5, Drawable theme6, Drawable theme7, Drawable theme8, Drawable theme9, Drawable theme10, Drawable theme11) {
        this.layout = layout;
        this.theme = theme;
        this.context = context;
        this.themeMain = themeMain;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.theme3 = theme3;
        this.theme4 = theme4;
        this.theme5 = theme5;
        this.theme6 = theme6;
        this.theme7 = theme7;
        this.theme8 = theme8;
        this.theme9 = theme9;
        this.theme10 = theme10;
        this.theme11 = theme11;
    }

    public SetTheme() {
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setThemeAll(Window window){
        if (String.valueOf(theme).equals("0")){
            layout.setBackground(themeMain);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.mainThemeColor));
        }
        else if (String.valueOf(theme).equals("1")){
            layout.setBackground(theme1);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme1Color));
        }
        else if (String.valueOf(theme).equals("2")){
            layout.setBackground(theme2);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme2Color));
        }
        else if (String.valueOf(theme).equals("3")){
            layout.setBackground(theme3);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme3Color));
        }
        else if (String.valueOf(theme).equals("4")){
            layout.setBackground(theme4);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme4Color));
        }
        else if (String.valueOf(theme).equals("5")){
            layout.setBackground(theme5);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme5Color));
        }
        else if (String.valueOf(theme).equals("6")){
            layout.setBackground(theme6);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme6Color));
        }
        else if (String.valueOf(theme).equals("7")){
            layout.setBackground(theme7);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme7Color));
        }
        else if (String.valueOf(theme).equals("8")){
            layout.setBackground(theme8);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme8Color));
        }
        else if (String.valueOf(theme).equals("9")){
            layout.setBackground(theme9);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme9Color));
        }
        else if (String.valueOf(theme).equals("10")){
            layout.setBackground(theme10);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme10Color));
        }
        else if (String.valueOf(theme).equals("11")){
            layout.setBackground(theme11);
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.Theme11Color));
        }
    }
}
