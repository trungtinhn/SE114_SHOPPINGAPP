package com.example.shoppingapp.StaffView.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.Activity.activity_categories;
import com.example.shoppingapp.StaffView.MyOrder.Activity.activity_MyOrder;
import com.example.shoppingapp.StaffView.MyProduct.Activity.activity_MyProduct;
import com.example.shoppingapp.StaffView.Home.Promotions.Activity.activity_promotions;
import com.example.shoppingapp.StaffView.ViewShop.activity_viewshop;
import com.example.shoppingapp.StaffView.activity.activity_chat_board;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class home_page extends AppCompatActivity {
    private Button btn_my_product, btn_myOrder,
            btn_chat, btn_financial_report, btn_manage_users, btn_categories, btn_view_shop;
    FirebaseAuth firebaseAuth;

    private ImageButton btn_Promotions;
    User user;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home_page);
        // initialising all views through id defined above
        btn_my_product = findViewById(R.id.btn_my_product);
        btn_categories = findViewById(R.id.btn_categories);
        btn_chat = findViewById(R.id.btn_chat);
        btn_myOrder = findViewById(R.id.btn_my_order);
        btn_Promotions = findViewById(R.id.btn_promotions);
        btn_financial_report = findViewById(R.id.btn_financial_report);
        btn_manage_users = findViewById(R.id.btn_manage_user);
        btn_view_shop = findViewById(R.id.btn_view_shop);
        btn_Promotions = findViewById(R.id.btn_promotions);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        DocumentReference documentReference=firebaseFirestore.collection("NGUOIDUNG").document(firebaseAuth.getUid());
        firebaseFirestore.collection("NGUOIDUNG").document(firebaseAuth.getUid()).
                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                ((TextView) findViewById(R.id.userName)).setText(user.getFullName());
                ((TextView) findViewById(R.id.userID)).setText(user.getMaND());
                String uri=user.getAvatar();
                try{
                    if(uri.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"null is recieved",Toast.LENGTH_SHORT).show();
                    }
                    else Picasso.get().load(uri).into((ImageView) findViewById(R.id.img_avt));
                }
                catch (Exception e)
                {

                }
                return;
            }
        });
        firebaseFirestore.collection("NGUOIDUNG").document(firebaseAuth.getUid()).update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Now User is Online",Toast.LENGTH_SHORT).show();
            }
        });
        btn_my_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình "MyProductActivity"
                Intent intent = new Intent(home_page.this, activity_MyProduct.class);
                startActivity(intent);
            }
        });
        btn_myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình "MyOrderActivity"
                Intent intent = new Intent(home_page.this, activity_MyOrder.class);
                startActivity(intent);
            }
        });
        btn_view_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, activity_viewshop.class);
                startActivity(intent);
            }
        });
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_page.this, activity_chat_board.class);
                startActivity(intent);
            }
        });
        btn_categories.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent (home_page.this, activity_categories.class);
                startActivity(intent);
            }
        });
        btn_Promotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, activity_promotions.class);
                startActivity(intent);
            }
        });
    }
}
