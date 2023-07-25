package com.example.shoppingapp.StaffView.Categories.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class activity_add_new_category extends AppCompatActivity {
    private ImageButton button_back;
    private ImageView imageView;
    private Uri selectedImageUri; // URI của ảnh đã chọn

    private Button btn_addnew;
    private static final int REQUEST_IMAGE_PICK = 1;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;

    private TextView TenDM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String MaSP = getIntent().getStringExtra("MaSP");
        setContentView(R.layout.activity_create_new_category);
        button_back = findViewById(R.id.btn_back);
        btn_addnew = findViewById(R.id.btn_add_new);
        TenDM = findViewById(R.id.editTextCategoryName);
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_add_new_category.this, activity_categories.class);
                startActivity(intent);
            }
        });
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK );
            }
        });

        btn_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageUri != null) {
                    uploadCategory(selectedImageUri);
                } else {
                    Toast.makeText(activity_add_new_category.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadCategory(Uri imageUri) {
        // Tạo tham chiếu tới vị trí lưu trữ trên Firebase Storage
        StorageReference imageRef = storageReference.child("ImageDanhMuc").child(imageUri.getLastPathSegment());

        try {
            // Chuyển đổi ảnh thành byte array
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();

            // Tải lên ảnh lên Firebase Storage
            UploadTask uploadTask = imageRef.putBytes(imageData);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Lấy URL của ảnh đã tải lên
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Lưu thông tin của category vào Firestore
                    Map<String, Object> category = new HashMap<>();
                    category.put("AnhDM", uri.toString());
                    category.put("MaDM", "");
                    category.put("SoLuongSP", 0);
                    category.put("TenDM", TenDM.getText().toString());

                    firestore.collection("DANHMUC")
                            .add(category)
                            .addOnSuccessListener(documentReference -> {
                                // Cập nhật MaDM với id của tài liệu vừa tạo
                                String MaDM = documentReference.getId();
                                documentReference.update("MaDM", MaDM)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(activity_add_new_category.this, "Category added successfully", Toast.LENGTH_SHORT).show();
                                            // Reset các trường dữ liệu và hiển thị ảnh mặc định
                                            selectedImageUri = null;
                                            imageView.setImageResource(R.drawable.add_category);
                                            TenDM.setText("");
                                            Intent intent = new Intent(activity_add_new_category.this, activity_categories.class);
                                            startActivity(intent);
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(activity_add_new_category.this, "Failed to update category", Toast.LENGTH_SHORT).show());
                            })
                            .addOnFailureListener(e -> Toast.makeText(activity_add_new_category.this, "Failed to add category", Toast.LENGTH_SHORT).show());
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(activity_add_new_category.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            if (data != null) {
                Uri newImageUri = data.getData();
                if (newImageUri != null) {
                    try {
                        // Lấy hình ảnh từ URI và hiển thị trong ImageView
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), newImageUri);
                        imageView.setImageBitmap(bitmap);
                        selectedImageUri = newImageUri;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
