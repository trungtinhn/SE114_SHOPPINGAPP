package com.example.shoppingapp.customerview.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.color.ColorAdapter;
import com.example.shoppingapp.customerview.color.Colors;
import com.example.shoppingapp.customerview.product.Product;
import com.example.shoppingapp.customerview.size.SizeAdapter;
import com.example.shoppingapp.customerview.viewpagerimage.AutoScrollTask;
import com.example.shoppingapp.customerview.viewpagerimage.ViewPagerImageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView backIcon;
    private Product product;
    private TextView txtProductNameDetail;
    private TextView txtPriceProductDetail;
    private String maSP;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView revColor;
    private ColorAdapter colorAdapter;
    private List<Colors> colors;
    private  RecyclerView rcvSize;
    private SizeAdapter sizeAdapter;
    private List<String> sizes;
    private TextView btnDown, btnUp, txtSoLuong;
    private String dataColor, dataSize;
    private TextView txtMoTaSP;
    private ImageView btnAnHienMoTa;
    private ViewPager2 viewpagerImage;
    private List<String> imageUrls;
    private FirebaseAuth firebaseAuth;
    private TextView btnAddToCard;
    private FirebaseUser firebaseUser;
    private Long giaSP;
    private AlertDialog.Builder builder;
    private TextView txtSeeReview;
    private String idGioHang;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        txtProductNameDetail = findViewById(R.id.txtProductNameDetail);
        txtPriceProductDetail = findViewById(R.id.txtPriceProductDetail);
        revColor = findViewById(R.id.rcvCorlor);
        rcvSize = findViewById(R.id.rcvSize);
        btnDown = findViewById(R.id.btnDown);
        btnUp = findViewById(R.id.btnUp);
        txtSoLuong = findViewById(R.id.txtSoLuong);
        txtMoTaSP = findViewById(R.id.txtMoTaSP);
        btnAnHienMoTa = findViewById(R.id.btnAnHienMoTa);
        viewpagerImage = findViewById(R.id.viewpagerImage);
        btnAddToCard = findViewById(R.id.btnAddToCard);
        txtSeeReview = findViewById(R.id.txtSeeReview);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Log.d("User",firebaseUser.getUid());



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), RecyclerView.HORIZONTAL, false);
        revColor.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManagerSize = new LinearLayoutManager(this.getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcvSize.setLayoutManager(linearLayoutManagerSize);

        firebaseFirestore = FirebaseFirestore.getInstance();

        backIcon = findViewById(R.id.backIcon);

        Intent intent = getIntent();
        maSP = intent.getStringExtra("MaSP");


        setBtnUpDown();
        getProduct(maSP);
        setMoTaSP();
        setAddToCard();
        setDialog();
        setBtnSeeReview();

        //txtProductNameDetail.setText(maSP);



        setOnClickBackICon();
    }

    private void setBtnSeeReview() {
        txtSeeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(DetailProductActivity.this, ReViewer.class );
                intent.putExtra("MaSP", maSP);
                startActivity(intent);
            }
        });
    }

    private void setDialog() {
        builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Tiêu đề")
                .setMessage("Nội dung của dialog")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý sự kiện khi người dùng bấm nút đồng ý
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý sự kiện khi người dùng bấm nút hủy
                    }
                });
    }


    private void setAddToCard() {

        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((dataColor.isEmpty() || dataColor == null) || (dataSize.isEmpty() || dataSize == null)) return;
                else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("MaND", firebaseUser.getUid());
                    data.put("MauSac", dataColor);
                    data.put("Size", dataSize);
                    data.put("HinhAnhSP", imageUrls);
                    data.put("TenSP", txtProductNameDetail.getText());
                    data.put("SoLuong", Integer.valueOf((String) txtSoLuong.getText()));
                    data.put("GiaSP", giaSP);
                    data.put("GiaTien", Integer.valueOf((String) txtPriceProductDetail.getText()));
                    data.put("MaSP", maSP);

                    firebaseFirestore.collection("GIOHANG")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    idGioHang = documentReference.getId();
                                    Toast.makeText(getApplicationContext(), "Add to cart successfully!", Toast.LENGTH_LONG);
                                    Intent intent = new Intent(DetailProductActivity.this, BottomNavigationCustomActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }
            }
        });

        if(idGioHang != null || !idGioHang.isEmpty()){
            DocumentReference washingtonRef = firebaseFirestore.collection("GIOHANG").document(idGioHang);


            washingtonRef
                    .update("MaGH", idGioHang)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                        }
                    });

        }
    }

    private void setMoTaSP() {
        txtMoTaSP.setVisibility(View.GONE);

        btnAnHienMoTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtMoTaSP.getVisibility() == View.GONE){
                    txtMoTaSP.setVisibility(View.VISIBLE);
                }
                else {
                    txtMoTaSP.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onColorClick(String colorName, String colorCode) {
        // Xử lý sự kiện khi màu được chọn
        // Dữ liệu màu tên và mã màu được truyền từ adapter qua activity
        dataColor = colorName;
        Log.d("Color", dataColor);
    }

    public void onSizeClick(String size) {
        // Xử lý sự kiện khi màu được chọn
        // Dữ liệu màu tên và mã màu được truyền từ adapter qua activity
        dataSize = size;
        Log.d("Size", dataSize);
    }

    private void setBtnUpDown() {

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong =  Integer.parseInt((String) txtSoLuong.getText());
                if (soLuong > 1){
                    soLuong--;
                    txtSoLuong.setText(String.valueOf(soLuong));
                    txtPriceProductDetail.setText(String.valueOf(soLuong * giaSP));
                }
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong =  Integer.parseInt((String) txtSoLuong.getText());
                soLuong++;
                txtSoLuong.setText(String.valueOf(soLuong));
                txtPriceProductDetail.setText(String.valueOf(soLuong * giaSP));
            }
        });
    }


    private void getProduct(String maSP){

        colorAdapter = new ColorAdapter();
        colors = new ArrayList<>();

        sizeAdapter =  new SizeAdapter();
        sizes = new ArrayList<>();

        imageUrls = new ArrayList<>();


        final DocumentReference docRef = firebaseFirestore.collection("SANPHAM").document(maSP);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    String tenSP = snapshot.getString("TenSP");
                    giaSP = snapshot.getLong("GiaSP");

                    int soLuong =  Integer.parseInt((String) txtSoLuong.getText());
                    txtPriceProductDetail.setText(String.valueOf(soLuong * giaSP));

                    String moTaSP = snapshot.getString("MoTaSP");
                    txtProductNameDetail.setText(tenSP);
                   // txtPriceProductDetail.setText(String.valueOf(giaSP));

                    txtMoTaSP.setText(moTaSP);

                    imageUrls = (List<String>) snapshot.get("HinhAnhSP");

                    ViewPagerImageAdapter imageAdapter = new ViewPagerImageAdapter(getApplicationContext(), imageUrls);
                    viewpagerImage.setAdapter(imageAdapter);

                    TimerTask autoScrollTask = new AutoScrollTask(viewpagerImage);
                    Timer timer = new Timer();
                    timer.schedule(autoScrollTask, 2000, 2000);

                    List<Colors> mauSacs = new ArrayList<>();
                    List<Map<String, Object>> colorMapList = (List<Map<String, Object>>) snapshot.get("MauSac");
                    for (Map<String, Object> colorMap : colorMapList) {
                        String maMau = (String) colorMap.get("MaMau");
                        String tenMau = (String) colorMap.get("TenMau");
                        String maMauSac = (String) colorMap.get("MaMauSac");
                        Colors mauSac = new Colors(maMau, maMauSac, tenMau);
                        mauSacs.add(mauSac);
                    }

                    sizes = (List<String>) snapshot.get("Size");

                    colorAdapter.setData(mauSacs, DetailProductActivity.this);
                    revColor.setAdapter(colorAdapter);

                    sizeAdapter.setData(sizes, DetailProductActivity.this);
                    rcvSize.setAdapter(sizeAdapter);

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }


    private void setOnClickBackICon() {

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, BottomNavigationCustomActivity.class);
                startActivity(intent);
            }
        });
    }
}