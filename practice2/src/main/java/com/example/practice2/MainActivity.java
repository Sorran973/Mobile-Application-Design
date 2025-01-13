package com.example.practice2;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    int color;
    int textColor;
    String locale = "en";
    TextView textView;
    Button buttonBlue, buttonRed, buttonBlack, buttonWhite, buttonLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.image_view);
        buttonBlue = findViewById(R.id.buttonBlue);
        buttonRed = findViewById(R.id.buttonRed);
        buttonBlack = findViewById(R.id.buttonBlack);
        buttonWhite = findViewById(R.id.buttonWhite);
        buttonLocale = findViewById(R.id.buttonLocale);

        color = R.color.blue;
        textColor = R.string.blue;
        setVisual();
        setButtons();

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = R.color.blue;
                textColor = R.string.blue;
                setVisual();
            }
        });

        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = R.color.red;
                textColor = R.string.red;
                setVisual();
            }
        });

        buttonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = R.color.black;
                textColor = R.string.black;
                setVisual();
            }
        });

        buttonWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = R.color.white;
                textColor = R.string.white;
                setVisual();
            }
        });

        buttonLocale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locale.equals("en")) {
                    locale = "ru";
                } else {
                    locale = "en";
                }
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.setLocale(new Locale(locale));
                resources.updateConfiguration(configuration, null);

                setButtons();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("color", color);
        outState.putInt("textColor", textColor);
        outState.putString("locale", locale);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        color = savedInstanceState.getInt("color");
        textColor = savedInstanceState.getInt("textColor");
        locale = savedInstanceState.getString("locale");

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(new Locale(locale));
        resources.updateConfiguration(configuration, null);

        setVisual();
        setButtons();
    }

    private void setVisual() {
        imageView.setColorFilter(ContextCompat.getColor(MainActivity.this, color));
        textView.setText(textColor);
    }

    private void setButtons() {
        textView.setText(textColor);
        buttonBlue.setText(R.string.blue);
        buttonRed.setText(R.string.red);
        buttonBlack.setText(R.string.black);
        buttonWhite.setText(R.string.white);
        buttonLocale.setText(R.string.locale);
    }
}