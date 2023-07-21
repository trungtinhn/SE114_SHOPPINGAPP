package com.example.shoppingapp.StaffView.MyProduct.Activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.Categories.Adapter.adapter_category_list;
import com.example.shoppingapp.StaffView.Categories.CategoryItem;
import com.example.shoppingapp.StaffView.Color.Color;
import com.example.shoppingapp.StaffView.Color.adapter_color;
import com.example.shoppingapp.StaffView.Size.Size;
import com.example.shoppingapp.StaffView.Size.adapter_size;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_add_new_product extends AppCompatActivity {

    private List<Size> sizeList;

    private List<CategoryItem> categoryItemList;
    private List<Color> colorList;
    private adapter_size sizeAdapter;

    private adapter_category_list categoryAdapter;
    private RecyclerView recyclerView_size;
    private RecyclerView recyclerView_color;
    private RecyclerView recyclerView_category;
    private adapter_color colorAdapter;
    private FirebaseFirestore db_size, db_color, db_category;



    // XỬ LÝ ADD SẢN PHẨM MỚI
    private ImageView imageView;
    private List<Uri> selectedImageUris;
    private Uri selectedImageUri; // URI của ảnh đã chọn
    private Button btn_addnew;
    private ImageButton button_back;
    private EditText productName, decriptions, price, delivery_fee, amount;
    private CheckBox check_color, check_size, check_category;
    private static final int REQUEST_IMAGE_PICK = 1;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    public activity_add_new_product() {
    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);


        db_size = FirebaseFirestore.getInstance();
        db_color = FirebaseFirestore.getInstance();
        db_category = FirebaseFirestore.getInstance();

        recyclerView_size = findViewById(R.id.RCV_sizePicker);
        recyclerView_size.setLayoutManager(new GridLayoutManager(this, 3));

        recyclerView_color = findViewById(R.id.RCV_colorPicker);
        recyclerView_color.setLayoutManager(new GridLayoutManager(this, 1));

        recyclerView_category = findViewById(R.id.RCV_category_picker);
        recyclerView_category.setLayoutManager(new GridLayoutManager(this, 1));

        colorList = new ArrayList<>();
        sizeList = new ArrayList<>();
        categoryItemList = new ArrayList<>();

        sizeAdapter = new adapter_size(sizeList);
        colorAdapter = new adapter_color(colorList);
        categoryAdapter = new adapter_category_list(categoryItemList);

        recyclerView_size.setAdapter(sizeAdapter);
        recyclerView_color.setAdapter(colorAdapter);
        recyclerView_category.setAdapter(categoryAdapter);

        // Thay thế đoạn này bằng cách lấy danh sách Size từ Firestore thông qua MaDM

        getSizeListFromFirestore();
        getColorListFromFirestore();
        getCategoryListFromFirestore();
        // CODE CHÍNH
        selectedImageUris = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();

        price = findViewById(R.id.prices);
        decriptions = findViewById(R.id.decriptions);
        delivery_fee = findViewById(R.id.delivery_fee);
        productName = findViewById(R.id.editTextCategoryName);
        amount= findViewById(R.id.soluong);
        btn_addnew = findViewById(R.id.btn_add_new);
        button_back = findViewById(R.id.btn_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_add_new_product.this, activity_MyProduct.class);
                startActivity(intent);
            }
        });
        imageView = findViewById(R.id.imageView);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        btn_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy các giá trị từ các trường nhập liệu
                String TenSP = productName.getText().toString().trim();
                String MoTaSP = decriptions.getText().toString().trim();
                String GiaSPString = price.getText().toString().trim();
                String PhiVanChuyenString = delivery_fee.getText().toString().trim();
                String SoLuongSPString = amount.getText().toString().trim();

                // Kiểm tra các trường thông tin có hợp lệ hay không
                if (selectedImageUris == null || TenSP.isEmpty() || MoTaSP.isEmpty() || GiaSPString.isEmpty() || PhiVanChuyenString.isEmpty() || SoLuongSPString.isEmpty()) {
                    Toast.makeText(activity_add_new_product.this, "Vui lòng điền đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }
                long SoLuongSP = Long.parseLong(SoLuongSPString);
                long GiaSP = Long.parseLong(GiaSPString);
                long PhiVanChuyen = Long.parseLong(PhiVanChuyenString);

                long SoLuongDaBan = 0;
                long SoLuongConLai = SoLuongSP - SoLuongDaBan;
                long SoLuongYeuThich =0;
                String TrangThai = "Inventory";
                String Trending = "false";
                // Tạo một DocumentReference cho sản phẩm mới trong collection "SANPHAM"
                DocumentReference productRef = firestore.collection("SANPHAM").document();

                // Tạo một HashMap để lưu các thuộc tính của sản phẩm
                Map<String, Object> product = new HashMap<>();
                // Lấy ID của document và gán cho MaSP
                String MaSP = productRef.getId();
                product.put("MaSP", MaSP);
                product.put("TenSP", TenSP);
                product.put("MoTaSP", MoTaSP);
                product.put("GiaSP", GiaSP);
                product.put("PhiVanChuyen",PhiVanChuyen);
                product.put("SoLuongSP", SoLuongSP);
                product.put("SoLuongConLai", SoLuongConLai);
                product.put("SoLuongDaBan", SoLuongDaBan);
                product.put("SoLuongYeuThich", SoLuongYeuThich);
                product.put("TrangThai", TrangThai);
                product.put("Trending",Trending);
                // Thêm hình ảnh vào storage và lấy URL
                uploadImagesToStorage(selectedImageUris, new OnImageUploadListener() {
                    @Override
                    public void onSuccess(List<String> imageUrls) {
                        // Thêm URL của hình ảnh vào thuộc tính của sản phẩm
                        product.put("HinhAnhSP", imageUrls);
                        // Thêm màu sắc được chọn vào thuộc tính của sản phẩm
                        List<String> selectedColors = colorAdapter.getSelectedColors();
                        product.put("MauSac", selectedColors);

                        // Thêm size được chọn vào thuộc tính của sản phẩm
                        List<String> selectedSizes = sizeAdapter.getSelectedSizes();
                        product.put("Size", selectedSizes);

                        // Lấy danh mục được chọn
                        String selectedCategory = categoryAdapter.getSelectedCategory();
                        product.put("MaDM", selectedCategory);

                        // Thêm sản phẩm vào Firestore
                        productRef.set(product)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        increaseCategoryProductCount(selectedCategory);
                                        Toast.makeText(activity_add_new_product.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(activity_add_new_product.this, activity_MyProduct.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(activity_add_new_product.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(activity_add_new_product.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
    public interface OnImageUploadListener {
        void onSuccess(List<String> imageUrls);

        void onFailure(String errorMessage);
    }

    private void uploadImagesToStorage(List<Uri> imageUris, OnImageUploadListener listener) {
        List<String> imageUrls = new ArrayList<>();
        int totalImages = imageUris.size();
        final int[] uploadedCount = {0};

        for (Uri imageUri : imageUris) {
            // Tạo một StorageReference cho hình ảnh trong Firebase Storage
            StorageReference imageRef = storageReference.child("ImageSanPham").child(imageUri.getLastPathSegment());

            // Tải lên hình ảnh lên Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Lấy URL của hình ảnh đã tải lên
                            imageRef.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            imageUrls.add(imageUrl);
                                            uploadedCount[0]++;

                                            // Kiểm tra nếu đã tải lên đủ số lượng hình ảnh
                                            if (uploadedCount[0] == totalImages) {
                                                listener.onSuccess(imageUrls);
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            listener.onFailure("Lỗi khi lấy URL hình ảnh");
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFailure("Lỗi khi tải lên hình ảnh");
                        }
                    });
        }
    }


    private void getCategoryListFromFirestore() {
        db_category.collection("DANHMUC")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        categoryItemList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String TenDM = document.getString("TenDM");
                            int SoLuongSP = Math.toIntExact(document.getLong("SoLuongSP"));
                            String MaDM = document.getId();
                            boolean isSelected = false;
                            CategoryItem categoryItem = new CategoryItem(TenDM, isSelected, MaDM, SoLuongSP);
                            categoryItemList.add(categoryItem);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(activity_add_new_product.this, "Failed to fetch category list", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void increaseCategoryProductCount(String selectedCategory) {
        DocumentReference categoryRef = db_category.collection("DANHMUC").document(selectedCategory);
        categoryRef.update("SoLuongSP", FieldValue.increment(1))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Số lượng sản phẩm trong danh mục đã được tăng lên 1
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Có lỗi xảy ra khi cập nhật số lượng sản phẩm trong danh mục
                    }
                });
    }
    private void getColorListFromFirestore() {
        db_color.collection("MAUSAC")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        colorList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String TenMau = document.getString("TenMau");
                            String colorCode = document.getString("MaMau");
                            String MaMau = document.getId();
                            boolean checked = false;

                            Color color = new Color(TenMau, colorCode, MaMau, checked);
                            colorList.add(color);
                        }
                        colorAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(activity_add_new_product.this, "Failed to fetch color list", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getSizeListFromFirestore() {
        db_size.collection("SIZE")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sizeList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String sizeName = document.getString("Size");
                            String maSize = document.getId();
                            boolean checked = false;

                            Size size = new Size(sizeName, maSize, checked);
                            sizeList.add(size);
                        }
                        sizeAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(activity_add_new_product.this, "Failed to fetch size list", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // Xóa danh sách hình ảnh đã chọn trước đó
            selectedImageUris.clear();

            // Lấy danh sách URI của hình ảnh đã chọn
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    selectedImageUris.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImageUris.add(imageUri);
            }

            // Hiển thị hình ảnh đầu tiên
            if (!selectedImageUris.isEmpty()) {
                Uri firstImageUri = selectedImageUris.get(0);
                imageView.setImageURI(firstImageUri);
            }
        }
    }
    // Phần khai báo và các phương thức khác trong activity_add_new_product.java không thay đổi

}

