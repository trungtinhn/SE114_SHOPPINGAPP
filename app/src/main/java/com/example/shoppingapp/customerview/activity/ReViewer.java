package com.example.shoppingapp.customerview.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.UiAutomation;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.review.ReViewData;
import com.example.shoppingapp.customerview.review.ReviewDataAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ReViewer extends AppCompatActivity {
    private TextView TongSoDanhGia, TrungBinh;
    private LinearLayout btn_addcomment;
    private RecyclerView DataComment;
    private ImageView backIcon;
    private RatingBar Rating;
    private List<ReViewData> dataReview;
    private ReviewDataAdapter dataAdapter;
    private  String maSP;

    private Button btn_addreview, btnaddReviewByUser;
    private CardView cardAddReview;
    private TextView txtExit;
    private ImageView imageReview;
    private String imagePath;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseUser firebaseUser;
    private String Uid;
    private ImageView imageAvatar;
    private TextView txtNameUser;

    private String ImageUrl;
    private RatingBar ratingStarUser;

    private EditText editReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_viewer);
        TongSoDanhGia = findViewById(R.id.txt_tongsodanhgia);
        backIcon = findViewById(R.id.backIcon);
        DataComment = findViewById(R.id.data_recyclerview);
       // btn_addcomment = findViewById(R.id.btn_;
        TrungBinh = findViewById(R.id.txt_trungbinh);
        Rating = findViewById(R.id.ratingBar);
        btn_addreview = findViewById(R.id.btn_addreview);
        cardAddReview = findViewById(R.id.cardAddReview);
        txtExit = findViewById(R.id.txtExit);
        imageReview = findViewById(R.id.imageReview);
        imageAvatar = findViewById(R.id.imageAvatar);
        txtNameUser = findViewById(R.id.txtNameUser);
        btnaddReviewByUser = findViewById(R.id.btnaddReviewByUser);
        ratingStarUser = findViewById(R.id.ratingStarUser);
        editReview = findViewById(R.id.editReview);

        Intent intent = getIntent();
        maSP = intent.getStringExtra("MaSP");


        setBtnAddReview();
        setImagePicker();

        setFirebaseUser();



        dataReview = new ArrayList<>();
        //add data
        dataAdapter = new ReviewDataAdapter(this.getApplicationContext());
        dataAdapter.setData(dataReview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        DataComment.setLayoutManager(linearLayoutManager);
        DataComment.setAdapter(dataAdapter);


//        btn_addcomment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent t = new Intent(ReViewer.this, );
//                //startActivity(t);
//            }
//        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent t = new Intent(ReViewer.this, DetailProductActivity.class);
//                startActivity(t);
            }
        });
    }

    private void setFirebaseUser() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Uid = firebaseUser.getUid();

        final DocumentReference docRef = firebaseFirestore.collection("NGUOIDUNG").document(Uid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    String avatar = snapshot.getString("avatar");
                    String fullName = snapshot.getString("fullName");

                    Picasso.get().load(avatar).into(imageAvatar);
                    txtNameUser.setText(fullName);

                } else {
                    Log.d("Current data", "null");
                }
            }
        });

    }

    private void UploadImage(Uri imageUri){
        StorageReference storageRef = firebaseStorage.getReference();

        String imagePath = "imageReview/" + UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child(imagePath);


        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                Log.d("ImageURI: ", downloadUri.toString());
                                ImageUrl = downloadUri.toString();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Tải ảnh thất bại
            }
        });

    }

    private void setImagePicker() {
        imageReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(ReViewer.this)
                        .crop()  // optionally enable image cropping
                        .compress(1024) // image max size in kilobytes
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            // Lấy đường dẫn hình ảnh được chọn
            imagePath = data.getStringExtra(ImagePicker.EXTRA_FILE_PATH);
            Glide.with(this).load(imagePath).into(imageReview);

            UploadImage(data.getData());



            // Sử dụng đường dẫn hình ảnh để hiển thị hoặc xử lý theo nhu cầu của bạn
        }
    }

    private void setBtnAddReview() {
        cardAddReview.setVisibility(View.GONE);
        btn_addreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "1111");
                if (cardAddReview.getVisibility() == View.GONE){
                    cardAddReview.setVisibility(View.VISIBLE);
                }
            }
        });

        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardAddReview.getVisibility() == View.VISIBLE){
                    cardAddReview.setVisibility(View.GONE);
                }
            }
        });

        btnaddReviewByUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReview();

            }


        });
    }

    private synchronized void setReview() {
        float currentRating = ratingStarUser.getRating();
        Timestamp currentTime = Timestamp.now();

        try {


            Map<String, Object> data = new HashMap<>();
            data.put("AnhDG", ImageUrl);
            data.put("MaND", Uid);
            data.put("MaSP", maSP);
            data.put("NDDG", editReview.getText().toString());
            data.put("NgayDG", currentTime);
            data.put("Rating", currentRating);


            firebaseFirestore.collection("DANHGIA")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d( "DocumentSnapshot written with ID: " , documentReference.getId());
                            Toast.makeText(ReViewer.this, "You have successfully reviewed!", Toast.LENGTH_SHORT).show();
                            cardAddReview.setVisibility(View.GONE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Error adding document", e);
                        }
                    });


        }catch (Exception e) {
            // Xử lý lỗi khi không thể lấy được imageURL
            e.printStackTrace();
            Log.d("Error", e.toString());
        }


    }
}