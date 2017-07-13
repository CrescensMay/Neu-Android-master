package com.neuandroid.chuck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class HomeActivity extends Activity{

    private Button jButton;
    private View jBackground;
    private colorWheel jColorWheel = new colorWheel();
    private int jColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        jBackground = findViewById(R.id.linearlayout);
        jColor = jColorWheel.getColor();
        jBackground.setBackgroundColor(jColor);
        jButton = (Button) findViewById(R.id.go_to_joke_page);

        jButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Toast.makeText(HomeActivity.this, "App succesfully launched!",Toast.LENGTH_LONG).show();
    }
}
