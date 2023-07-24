package com.example.shoppingapp.customerview.activity;

import static android.content.ContentValues.TAG;

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

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;



import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ReViewer extends AppCompatActivity {
    private TextView TongSoDanhGia, TrungBinh;
    private LinearLayout btn_addcomment;


    private ImageView backIcon;
    private RatingBar Rating;



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

    private ReviewDataAdapter reviewDataAdapter;
    private RecyclerView data_recyclerview;
    private String avatarUri;
    private String nameUser;
    private Boolean allowReview = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_viewer);
        TongSoDanhGia = findViewById(R.id.txt_tongsodanhgia);
        backIcon = findViewById(R.id.backIcon);

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

        data_recyclerview = findViewById(R.id.data_recyclerview);



        Intent intent = getIntent();
        maSP = intent.getStringExtra("MaSP");



        setBtnAddReview();
        setImagePicker();


        setFirebaseUser();

        fetchDataReviewSanPham();
        checkAllowReview();




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        data_recyclerview.setLayoutManager(linearLayoutManager);



        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent t = new Intent(ReViewer.this, DetailProductActivity.class);
//                startActivity(t);
            }
        });
    }

    private void checkAllowReview() {
        firebaseFirestore.collection("DONHANG")
                .whereEqualTo("maND", firebaseUser.getUid())

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<String> dataMaDH = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Log.d("Test.", "111" );
                            dataMaDH.add(doc.getString("MaDH"));
                        }

                        for (String maDH : dataMaDH){



                            firebaseFirestore.collection("DATHANG")
                                    .whereEqualTo("MaDH", maDH)
                                    .whereEqualTo("MaSP", maSP)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value,
                                                            @Nullable FirebaseFirestoreException e) {

                                            if (value.size() > 0){
                                                btn_addreview.setVisibility(View.VISIBLE);
                                                return;
                                            }
                                        }
                                    });
                        }

                    }
                });



    }


    private void fetchDataReviewSanPham() {

        reviewDataAdapter = new ReviewDataAdapter(getApplicationContext());


        firebaseFirestore.collection("DANHGIA")
                .whereEqualTo("MaSP", maSP)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Listen failed.", e);
                            return;
                        }

                        List<ReViewData> dataReview = new ArrayList<>();
                        for (DocumentSnapshot doc : value.getDocuments()) {

                            String avatar = doc.getString("Avatar");
                            String name = doc.getString("Name");
                            Timestamp timestamp = doc.getTimestamp("NgayDG");

                            Date date = timestamp.toDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String time = sdf.format(date);

                            String content = doc.getString("NDDG");
                            float rating = doc.getDouble("Rating").floatValue();
                            String image = doc.getString("AnhDG");



                            ReViewData reViewData = new ReViewData(avatar, name, time, rating, content, image);
                            Log.d("name", reViewData.getTime());
                            dataReview.add(reViewData);

                        }


                        TongSoDanhGia.setText(String.valueOf(dataReview.size()));

                        float sum = 0;

                        for (ReViewData review : dataReview){
                            sum += review.getRating();
                        }

                        TrungBinh.setText(String.valueOf(sum/dataReview.size()));

                        reviewDataAdapter.setData(dataReview);
                        data_recyclerview.setAdapter(reviewDataAdapter);

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
                    avatarUri = snapshot.getString("avatar");
                    nameUser = snapshot.getString("fullName");

                    Picasso.get().load(avatarUri).into(imageAvatar);
                    txtNameUser.setText(nameUser);

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
            data.put("Avatar", avatarUri);
            data.put("Name", nameUser);


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