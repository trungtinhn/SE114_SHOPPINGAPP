package com.example.shoppingapp.customerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.review.ReViewData;
import com.example.shoppingapp.customerview.review.ReviewDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReViewer extends AppCompatActivity {
    TextView TongSoDanhGia, TrungBinh;
    LinearLayout btn_addcomment;
    RecyclerView DataComment;
    ImageView backIcon;
    RatingBar Rating;
    List<ReViewData> dataReview;
    ReviewDataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_viewer);
        TongSoDanhGia = findViewById(R.id.txt_tongsodanhgia);
        backIcon = findViewById(R.id.backIcon);
        DataComment = findViewById(R.id.data_recyclerview);
        btn_addcomment = findViewById(R.id.btn_addreview);
        TrungBinh = findViewById(R.id.txt_trungbinh);
        Rating = findViewById(R.id.ratingBar);

        dataReview = new ArrayList<>();

        dataAdapter = new ReviewDataAdapter(this.getApplicationContext());
        dataAdapter.setData(dataReview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        DataComment.setLayoutManager(linearLayoutManager);
        DataComment.setAdapter(dataAdapter);


        btn_addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent t = new Intent(ReViewer.this, );
                //startActivity(t);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent t = new Intent(ReViewer.this, DetailProductActivity.class);
//                startActivity(t);
            }
        });
    }
}