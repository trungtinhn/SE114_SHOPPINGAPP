package com.example.shoppingapp.StaffView.Promotions.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;

public class activity_add_new_promotions extends AppCompatActivity {
    private ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_promotions);
        btn_back = findViewById(R.id.imgbtn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_add_new_promotions.this, activity_promotions.class);
                startActivity(intent);
            }
        });

    }


}
