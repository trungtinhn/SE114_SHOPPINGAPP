package com.example.shoppingapp.StaffView.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.shoppingapp.Login.LoginActivity;
import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.Activity.activity_categories;
import com.example.shoppingapp.StaffView.FinancialReport.Activity.activity_financial;
import com.example.shoppingapp.StaffView.MyOrder.Activity.activity_MyOrder;
import com.example.shoppingapp.StaffView.MyProduct.Activity.activity_MyProduct;
import com.example.shoppingapp.StaffView.Promotions.Activity.activity_promotions;
import com.example.shoppingapp.StaffView.ViewShop.Activity.activity_viewshop;
import com.example.shoppingapp.StaffView.activity.activity_admin_control;
import com.example.shoppingapp.StaffView.activity.activity_chat_board;
import com.example.shoppingapp.StaffView.activity.activity_setting;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class home_page extends AppCompatActivity {
    private Button btn_my_product, btn_myOrder, btn_setting,
            btn_chat, btn_financial_report, btn_manage_users, btn_categories, btn_view_shop;
    FirebaseAuth firebaseAuth;

    private Button btn_logout;
    private ImageButton btn_Promotions;
    ProgressDialog progressDialog;
    User user;
    private AppCompatButton btn_confirm, btn_wait, btn_delivering, btn_delivered, btn_cancel;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        setContentView(R.layout.screen_home_page);
        // initialising all views through id defined above
        btn_my_product = findViewById(R.id.btn_my_product);
        btn_categories = findViewById(R.id.btn_categories);
        btn_setting = findViewById(R.id.btn_setting);
        btn_chat = findViewById(R.id.btn_chat);
        btn_myOrder = findViewById(R.id.btn_my_order);
        btn_Promotions = findViewById(R.id.btn_promotions);
        btn_financial_report = findViewById(R.id.btn_financial_report);
        btn_manage_users = findViewById(R.id.btn_manage_user);
        btn_view_shop = findViewById(R.id.btn_view_shop);
        btn_Promotions = findViewById(R.id.btn_promotions);
        btn_logout = findViewById(R.id.btn_logout);
        btn_financial_report = findViewById(R.id.btn_financial_report);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_wait =findViewById(R.id.btn_wait);
        btn_delivering = findViewById(R.id.btn_delivering);
        btn_delivered = findViewById(R.id.btn_delivered);
        //...
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nút "Confirm" được nhấp vào, chuyển đến "activity_MyOrder" và hiển thị fragment Confirm
                Intent intent = new Intent(home_page.this, activity_MyOrder.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected_tab", 0); // 0 là vị trí tab Confirm
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nút "Wait" được nhấp vào, chuyển đến "activity_MyOrder" và hiển thị fragment Wait
                Intent intent = new Intent(home_page.this, activity_MyOrder.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected_tab", 1); // 1 là vị trí tab Wait
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_delivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nút "Delivering" được nhấp vào, chuyển đến "activity_MyOrder" và hiển thị fragment Delivering
                Intent intent = new Intent(home_page.this, activity_MyOrder.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected_tab", 2); // 2 là vị trí tab Delivering
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btn_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi nút "Delivered" được nhấp vào, chuyển đến "activity_MyOrder" và hiển thị fragment Delivered
                Intent intent = new Intent(home_page.this, activity_MyOrder.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected_tab", 3); // 3 là vị trí tab Delivered
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, activity_MyOrder.class);
                Bundle bundle = new Bundle();
                bundle.putInt("selected_tab", 4); // 0 là vị trí tab Confirm
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btn_financial_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, activity_financial.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        // Kiểm tra và xử lý trường hợp khi firebaseAuth.getUid() bị null
        if (firebaseAuth.getCurrentUser() == null) {
            // Xử lý khi firebaseAuth.getUid() bị null, ví dụ chuyển đến màn hình đăng nhập
            Intent intent = new Intent(home_page.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        progressDialog.setTitle("Fetching data...");
        progressDialog.show();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        DocumentReference documentReference=firebaseFirestore.collection("NGUOIDUNG")
                .document(firebaseAuth.getUid());
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
                if(user.getLoaiND().equals("Staff")) {
                    btn_manage_users.setVisibility(View.GONE);
                    findViewById(R.id.txt_manage_user).setVisibility(View.GONE);
                }
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Fetching Failed",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
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
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_page.this, activity_setting.class);
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
        btn_manage_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_page.this, activity_admin_control.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=FirebaseFirestore.getInstance().
                collection("NGUOIDUNG").document(FirebaseAuth.getInstance().getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        });

    }
}
