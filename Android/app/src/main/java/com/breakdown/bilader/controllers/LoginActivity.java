package com.breakdown.bilader.controllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.breakdown.bilader.R;

public class LoginActivity extends Activity
{
/*    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_verification);

//        Button loginButton = findViewById(R.id.buttonLogIn);
//        Button signUpButton = findViewById(R.id.buttonSignUp);
//
//        buttonEffect(loginButton, 0xe01c79e4);
//        buttonEffect(signUpButton, 0xe01c79e4);
    }

    /**
     * adds on press effects for buttons
     * @param button that is going to have the effect
     * @param colorHexCode that is hex value of the press color effect
     *
    public static void buttonEffect(View button, int colorHexCode)
    {
        button.setOnTouchListener(new View.OnTouchListener()
        {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        v.getBackground().setColorFilter(colorHexCode, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }*/
}


