package com.example.shoppingapp.customerview.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn_ChangeIf,btn_ChangeAva;
    User user;
    int position;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    DocumentReference docRef;
    ProgressDialog progressDialog;
    private ShapeableImageView imgAvt,imgAvt_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        imgAvt = findViewById(R.id.img_avt_Profile);


        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            db = FirebaseFirestore.getInstance();
            docRef = db.collection("NGUOIDUNG").document(currentUserId);

            docRef.addSnapshotListener((documentSnapshot, e) -> {
                if (e != null) {
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Lấy thông tin người dùng từ Firestore
                    User user = documentSnapshot.toObject(User.class);
                    if (user != null) {
                        // Hiển thị thông tin người dùng lên giao diện
                        ((TextView) findViewById(R.id.name_Profile)).setText(user.getFullName());
                        ((TextView) findViewById(R.id.sex_Profile)).setText(user.getGioitinh());
                        ((TextView) findViewById(R.id.txt_dob_Profile)).setText(user.getDayOfBirth());
                        ((TextView) findViewById(R.id.txt_pnumP7_Profile)).setText(user.getPhoneNumber());
                        ((TextView) findViewById(R.id.txt_email_Profile)).setText(user.getEmail());
                        ((TextView) findViewById(R.id.txt_address_Profile)).setText(user.getDiachi());
                        String uri = user.getAvatar();
                        try {
                            if (uri != null && !uri.isEmpty()) {
                                int width = 200;
                                int height = 200;
                                Picasso.get().load(uri).resize(width, height).into((ImageView) findViewById(R.id.img_avt_Profile));
                            } else {
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    // Xử lý khi tài liệu không tồn tại hoặc bị xóa
                }
            });
        }
        findViewById(R.id.btn_changeInfo_Profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventEdit();

            }
        });
        findViewById(R.id.btn_changeAvt_Profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventChangeAva();
            }
        });
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private static final int REQUEST_IMAGE_PICK = 1;
    private void EventChangeAva() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    // Xử lý kết quả sau khi người dùng đã chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            updateUserAvatar(currentUserId, selectedImageUri);
        }
    }
    private void updateUserAvatar(String userId, Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference avatarRef = storageRef.child("avatars/" + userId + ".jpg");

        avatarRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    avatarRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageURL = uri.toString();
                        db.collection("NGUOIDUNG").document(userId)
                                .update("avatar", imageURL)
                                .addOnSuccessListener(aVoid -> {
                                    int width = 200;
                                    int height = 200;
                                    Picasso.get().load(imageURL).resize(width, height).into(imgAvt);
                                    showUpdateSuccessDialog_ava();
                                    Toast.makeText(ProfileActivity.this, "Avatar updated successfully", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(ProfileActivity.this, "Failed to update avatar", Toast.LENGTH_SHORT).show();
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ProfileActivity.this, "Failed to upload avatar", Toast.LENGTH_SHORT).show();
                });
    }
    private void showUpdateSuccessDialog_ava() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Avatar updated successfully")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    private void EventEdit() {
        DialogPlus dialogPlus = DialogPlus.newDialog( this)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.activity_customer_detail_update))
                .setExpanded(false)
                .create();

        View holderView = (LinearLayout) dialogPlus.getHolderView();

        EditText edName = holderView.findViewById(R.id.txt_name_Profile);
        Spinner edSex = holderView.findViewById(R.id.txt_sex_Profile);
        TextView edDob = holderView.findViewById(R.id.txt_dob_Profile);
        EditText edPhone = holderView.findViewById(R.id.txt_phone_Profile);
        EditText edAdd = holderView.findViewById(R.id.txt_address_Profile);
        Button btnUpdate = holderView.findViewById(R.id.btn_update_Profile);

        ArrayAdapter<CharSequence> gender = ArrayAdapter.createFromResource(this,R.array.gender,
                R.layout.spinner_item);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edSex.setAdapter(gender);
        edSex.setOnItemSelectedListener(this);


        edDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edDob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("fullName", edName.getText().toString());
                map.put("gioitinh", edSex.getSelectedItem().toString());
                map.put("dayOfBirth", edDob.getText().toString());
                map.put("phoneNumber", edPhone.getText().toString());
                map.put("diachi", edAdd.getText().toString());

                docRef.update(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Hiển thị dialog thông báo thành công
                                showSuccessDialog();

                                // Sau khi người dùng nhấn "OK", chuyển hướng đến ProfileActivity
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                                Toast.makeText(ProfileActivity.this, "Fail to update the data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            private void showSuccessDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Success");
                builder.setMessage("Your information has been changed");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        dialogPlus.show();


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}