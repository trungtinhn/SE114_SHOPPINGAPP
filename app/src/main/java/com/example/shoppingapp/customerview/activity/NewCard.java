package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingapp.R;

public class NewCard extends AppCompatActivity {
    ImageView backIcon;
    EditText Name, Number, Date, CVV;
    TextView btn_addcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        backIcon = findViewById(R.id.backIcon);
        Name = findViewById(R.id.text_name);
        Number = findViewById(R.id.text_address);
        Date = findViewById(R.id.txt_ngay);
        CVV = findViewById(R.id.txt_maso);
        btn_addcard = findViewById(R.id.btn_addcard);

        btn_addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(NewCard.this, CheckOut.class);
                startActivity(t);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(NewCard.this, CheckOut.class);
                startActivity(t);
            }
        });
    }
}