package com.example.shoppingapp.StaffView.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class activity_admin_detail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn_edit, btn_remove;
    User object;
    User admin;
    int position;
    ProgressDialog progressDialog;
    //ArrayList<User> AdminList;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    DocumentReference docRef;
    StorageReference storageReference;
    private String imagePath;
    private ImageView edImg;
    private String ImageUrl;
    private String oldImageUrl;
    private Uri Staff_imagepath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Intent intent = getIntent();
        admin = (User) intent.getSerializableExtra("Admin");
        object = (User) intent.getSerializableExtra("Data");
        position = intent.getIntExtra("Position",0);
        Bundle args = intent.getBundleExtra("Bundle");
        //AdminList = (ArrayList<User>) args.getSerializable("ArrayList");

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        docRef = FirebaseFirestore.getInstance()
                .collection("NGUOIDUNG").document(object.getMaND());
        ((TextView) findViewById(R.id.txt_admin_name)).setText(object.getFullName());
        ((TextView) findViewById(R.id.txt_name)).setText(object.getFullName());
        ((TextView) findViewById(R.id.txt_sex)).setText(object.getGioitinh());
        ((TextView) findViewById(R.id.txt_dob)).setText(object.getDayOfBirth());
        ((TextView) findViewById(R.id.txt_phone)).setText(object.getPhoneNumber());
        ((TextView) findViewById(R.id.txt_mail)).setText(object.getEmail());
        ((TextView) findViewById(R.id.txt_location)).setText(object.getDiachi());
        ((TextView) findViewById(R.id.txt_status)).setText(object.getStatus());
        ImageUrl = object.getAvatar();
        try{
            if(ImageUrl.isEmpty()) {}
            else {
                Picasso.get().load(ImageUrl).into((ImageView) findViewById(R.id.img_staff));
                storageReference = firebaseStorage.getReferenceFromUrl(ImageUrl);
            }
        }
        catch (Exception e)
        {

        }
        oldImageUrl = ImageUrl;

        ((TextView) findViewById(R.id.adminName)).setText(admin.getFullName());
        ((TextView) findViewById(R.id.adminID)).setText(admin.getMaND());
        String uri=admin.getAvatar();
        try{
            if(uri.isEmpty()) {}
            else Picasso.get().load(uri).into((ImageView) findViewById(R.id.img_avt));
        }
        catch (Exception e)
        {

        }
        //((TextView) findViewById(R.id.txt_pass)).setText(());

        findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventEdit();

            }
        });
        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventRemove();
            }
        });
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void EventEdit()
    {
        DialogPlus dialogPlus = DialogPlus.newDialog( this)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.activity_admin_detail_edit))
                .setExpanded(false)
                .create();

        View holderView = (ScrollView) dialogPlus.getHolderView();

        edImg = holderView.findViewById(R.id.img_imageView);
        EditText edName = holderView.findViewById(R.id.txt_name);
        Spinner edSex = holderView.findViewById(R.id.txt_sex);
        TextView edDob = holderView.findViewById(R.id.txt_dob);
        EditText edPhone = holderView.findViewById(R.id.txt_phone);
        EditText edAddr = holderView.findViewById(R.id.txt_address);
        Button btnUpdate = holderView.findViewById(R.id.btn_update);
        Button btnCancel = holderView.findViewById(R.id.btn_cancel);


        ArrayAdapter<CharSequence> gender = ArrayAdapter.createFromResource(this,R.array.gender,
                R.layout.spinner_item);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edSex.setAdapter(gender);
        edSex.setOnItemSelectedListener(this);


        edName.setText(object.getFullName());
        String gt = object.getGioitinh();
        try{
            if(gt.isEmpty()) edSex.setSelection(0);
            else if(gt.equals("Male")) edSex.setSelection(1);
            else if(gt.equals("Female")) edSex.setSelection(2);
        }
        catch (Exception e){

        }
        edAddr.setText(object.getDiachi());
        edDob.setText(object.getDayOfBirth());
        edPhone.setText(object.getPhoneNumber());
        try{
            if(ImageUrl.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"null is recieved",Toast.LENGTH_SHORT).show();
            }
            else Picasso.get().load(ImageUrl).into(edImg);
        }
        catch (Exception e)
        {

        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
                try {
                    if (!ImageUrl.equals(oldImageUrl)) {
                        try {
                            if (!ImageUrl.isEmpty()) {
                                DeleteOldImg(ImageUrl);
                            }}
                        catch (Exception d)
                        {
                        }
                    }
                }
                catch (Exception d)
                {
                }
            }
        });
        edImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(activity_admin_detail.this)
                        .crop()  // optionally enable image cropping
                        .start();
            }
        });

        edDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        activity_admin_detail.this,
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
                map.put("fullName",edName.getText().toString());
                map.put("gioitinh",edSex.getSelectedItem().toString());
                map.put("dayOfBirth",edDob.getText().toString());
                map.put("phoneNumber",edPhone.getText().toString());
                map.put("diachi",edAddr.getText().toString());
                map.put("avatar",ImageUrl);

                docRef.update(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity_admin_detail.this, "Success updating the data", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                                try {
                                    if (!ImageUrl.equals(oldImageUrl)) {
                                        try {
                                            if (!oldImageUrl.isEmpty()) {
                                                DeleteOldImg(oldImageUrl);
                                            }}
                                        catch (Exception e)
                                        {
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                }
                                Intent intent = new Intent(activity_admin_detail.this,activity_admin_control.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                                Toast.makeText(activity_admin_detail.this, "Fail to update the data", Toast.LENGTH_SHORT).show();
                                try {
                                    if (!ImageUrl.equals(oldImageUrl)) {
                                        try {
                                            if (!ImageUrl.isEmpty()) {
                                                DeleteOldImg(ImageUrl);
                                            }}
                                        catch (Exception d)
                                        {
                                        }
                                    }
                                }
                                catch (Exception d)
                                {
                                }
                            }
                        });
            }
        });

        dialogPlus.show();

    }
    private void EventRemove(){
        progressDialog.setTitle("Removing Staff...");
        progressDialog.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Delete Staff")
                .setMessage("Are you sure want to delete ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.dismiss();
                        docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity_admin_detail.this, "Deleted Staff", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity_admin_detail.this,activity_admin_control.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity_admin_detail.this, "Fail to delete the staff", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.dismiss();
                    }
                });
        builder.show();

    }
    private void DeleteOldImg(String deleteImg){
        progressDialog.setTitle("Waiting...");
        progressDialog.show();
        if(deleteImg != null ){
            StorageReference oldImageRef = firebaseStorage.getReferenceFromUrl(deleteImg);
            oldImageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    // File deleted successfully
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    // Uh-oh, an error occurred
                }
            });
        }
    }
    private void updateImgInStorage(Uri imageUri){
        progressDialog.setTitle("Updating...");
        progressDialog.show();
        StorageReference storageRef = firebaseStorage.getReference();

        String imagePath = "ImageUser/" + UUID.randomUUID().toString() + ".jpg";
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
                                progressDialog.dismiss();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(activity_admin_detail.this, "Fail to send image to firebase", Toast.LENGTH_LONG).show();
                        // Tải ảnh thất bại
                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            // Lấy đường dẫn hình ảnh được chọn
            imagePath = data.getStringExtra(ImagePicker.EXTRA_FILE_PATH);
            Glide.with(this).load(imagePath).into(edImg);

            updateImgInStorage(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
