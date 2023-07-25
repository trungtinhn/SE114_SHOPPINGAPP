package com.example.shoppingapp.StaffView.activity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.adapter.adapter_admin_control;
import com.example.shoppingapp.itf_RCV_list_item;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class activity_admin_control extends AppCompatActivity implements itf_RCV_list_item, AdapterView.OnItemSelectedListener, Filterable {

    RecyclerView RCV;
    private static int PICK_IMAGE=123;
    private Uri Staff_imagepath;
    private String imagePath;
    private ImageView ivImg;
    DatabaseReference reference = FirebaseDatabase.getInstance().
            getReferenceFromUrl("https://se114-df58a-default-rtdb.firebaseio.com/");

    Button btn_add;
    ArrayList<User> StaffList;
    adapter_admin_control adapterAdmin;
    private FirebaseAuth firebaseAuth;
    SearchView searchView;
    User admin;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    private int admin_count;
    private String ImageUrl;
    static private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_admin_control);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        //setupAdminList();

        admin_count = 0 ;

        firebaseFirestore.collection("NGUOIDUNG").document(firebaseAuth.getUid()).
                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        admin = documentSnapshot.toObject(User.class);
                        ((TextView) findViewById(R.id.adminName)).setText(admin.getFullName());
                        ((TextView) findViewById(R.id.adminID)).setText(admin.getMaND());
                        String uri=admin.getAvatar();
                        try{
                            if(uri.isEmpty())
                            {
                            }
                            else Picasso.get().load(uri).into((ImageView) findViewById(R.id.img_avt));
                        }
                        catch (Exception e)
                        {

                        }
                        return;
                    }
                });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        RCV = findViewById(R.id.RCV_admin);
        btn_add = findViewById(R.id.btn_add);

        RCV.setHasFixedSize(true);
        RCV.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EventInitListener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        StaffList = new ArrayList<>();
        adapterAdmin = new adapter_admin_control(this, StaffList,this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventAdd();
            }
        });

        EventInitListener();
        RCV.setAdapter(adapterAdmin);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EventInitListener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapterAdmin.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterAdmin.getFilter().filter(s);
                return false;
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
    private void EventAdd() {

        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.activity_admin_add))
                .setExpanded(false)
                .create();

        View holderView = (ScrollView) dialogPlus.getHolderView();

        EditText edName = holderView.findViewById(R.id.txt_name);
        Spinner edSex = holderView.findViewById(R.id.txt_sex);
        EditText edAddr = holderView.findViewById(R.id.txt_address);
        TextView edDob = holderView.findViewById(R.id.txt_dob);
        EditText edPhone = holderView.findViewById(R.id.txt_phone);
        EditText edMail = holderView.findViewById(R.id.txt_mail);
        EditText edPass = holderView.findViewById(R.id.txt_pass);
        EditText edPass_2 = holderView.findViewById(R.id.txt_pass_2);
        ivImg = holderView.findViewById(R.id.img_imageView);
        Button btnUpdate = holderView.findViewById(R.id.btn_update);
        Button btnCancel = holderView.findViewById(R.id.btn_cancel);

        ArrayAdapter<CharSequence> gender = ArrayAdapter.createFromResource(this, R.array.gender,
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
                        activity_admin_control.this,
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
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(activity_admin_control.this)
                        .crop()  // optionally enable image cropping
                        .start();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
                DeleteOldImg(ImageUrl);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edName.getText().toString())) {
                    edName.setError("Please fill name!");
                    return;
                }
                if (TextUtils.isEmpty(edAddr.getText().toString())) {
                    edAddr.setError("Please fill Address!");
                    return;
                }
                if (TextUtils.isEmpty(edPass_2.getText().toString())) {
                    edPass_2.setError("Please re-enter your pass!");
                    return;
                }
                if (TextUtils.isEmpty(edDob.getText().toString())) {
                    edDob.setError("Please fill date of birth!");
                    return;
                }
                if (TextUtils.isEmpty(edPhone.getText().toString())) {
                    edPhone.setError("Please fill phone!");
                    return;
                }
                if (TextUtils.isEmpty(edMail.getText().toString())) {
                    edMail.setError("Please fill mail!");
                    return;
                }
                if (TextUtils.isEmpty(edPass.getText().toString())) {
                    edPass.setError("Please fill password!");
                    return;
                }
                if (edPass.getText().toString().length() < 6) {
                    Toast.makeText(activity_admin_control.this, "Password must be at least 6 characters!",
                            Toast.LENGTH_SHORT).show();
                }
                if (!edPass.getText().toString().equals(edPass_2.getText().toString())) {
                    edPass_2.setError("Your password is not matching!");
                    return;
                }
                final String emailUTF = edMail.getText().toString();
                String password = edPass.getText().toString();
                String fullname = edName.getText().toString();
                String sex = edSex.getSelectedItem().toString();
                String dayofbirth = edDob.getText().toString();
                String phonenumber = edPhone.getText().toString();
                String diachi = edAddr.getText().toString();
                String status = "Offline";


                final String email = Base64.getEncoder().encodeToString(emailUTF.getBytes());
                reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(email)) {
                            Toast.makeText(activity_admin_control.this, "Email has already registered, try another Email", Toast.LENGTH_SHORT).show();
                        } else {
                            // sending data to firebase
                            firebaseAuth
                                    .createUserWithEmailAndPassword(emailUTF, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task)
                                        {
                                            if (task.isSuccessful()) {
                                                reference.child("Users").child(email).child("Email").setValue(emailUTF);
                                                reference.child("Users").child(email).child("LoaiND").setValue("Staff");
                                                String userID = task.getResult().getUser().getUid();
                                                User user = new User(fullname, emailUTF, dayofbirth,phonenumber, userID, ImageUrl,
                                                        diachi, sex, status,"Staff");
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                CollectionReference usersCollection = db.collection("NGUOIDUNG");


                                                usersCollection.document(userID).set(user)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(activity_admin_control.this, "Success to register new staff", Toast.LENGTH_SHORT).show();
                                                                firebaseAuth.updateCurrentUser(firebaseUser);
                                                                adapterAdmin.notifyDataSetChanged();
                                                                EventInitListener();
                                                                dialogPlus.dismiss();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                dialogPlus.dismiss();
                                                                Toast.makeText(activity_admin_control.this, "Failed to register new staff", Toast.LENGTH_SHORT).show();
                                                                try{
                                                                    if(!ImageUrl.isEmpty())
                                                                    {
                                                                        DeleteOldImg(ImageUrl);
                                                                    }
                                                                }
                                                                catch (Exception d)
                                                                {
                                                                }
                                                            }
                                                        });
                                                dialogPlus.dismiss();
                                            }
                                            else {

                                                // Registration failed
                                                Toast.makeText(
                                                                getApplicationContext(),
                                                                "Adding failed!!"
                                                                        + " Please try again later",
                                                                Toast.LENGTH_LONG)
                                                        .show();
                                                try{
                                                    if(!ImageUrl.isEmpty())
                                                    {
                                                        DeleteOldImg(ImageUrl);
                                                    }
                                                }
                                                catch (Exception d)
                                                {
                                                }
                                                // hide the progress bar
                                            }

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            return;
                    }
                });

            }
        });
        dialogPlus.show();
    }

    private void DeleteOldImg(String deleteImg){
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        if(deleteImg != null ){
            StorageReference oldImageRef = firebaseStorage.getReferenceFromUrl(deleteImg);
            oldImageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred
                }
            });
        }
        progressDialog.dismiss();
    }
    private void sendImgToStorage(Uri imageUri){
        progressDialog.setTitle("Storing...");
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
                        // Tải ảnh thất bại
                    }
                });

    }
    private void EventInitListener() {
        progressDialog.setTitle("Loading data...");
        progressDialog.show();
        firebaseFirestore.collection("NGUOIDUNG").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();
                StaffList.clear();
                for(DocumentSnapshot d : task.getResult())
                {
                    User object = d.toObject(User.class);
                    if(Objects.equals(object.getLoaiND(), "Staff")) StaffList.add(object);
                    admin_count++;
                }
                adapterAdmin.notifyDataSetChanged();
                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(activity_admin_control.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(activity_admin_control.this, activity_admin_detail.class);
        final User object = StaffList.get(position);
        intent.putExtra("Admin", admin);
        intent.putExtra("Data",  object);
        intent.putExtra("Position", position);
        Bundle args = new Bundle();
        args.putSerializable("ArrayList",(Serializable) StaffList);
        intent.putExtra("Bundle",args);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            // Lấy đường dẫn hình ảnh được chọn
            imagePath = data.getStringExtra(ImagePicker.EXTRA_FILE_PATH);
            Glide.with(this).load(imagePath).into(ivImg);
            sendImgToStorage(data.getData());
            Staff_imagepath = data.getData();
            // Sử dụng đường dẫn hình ảnh để hiển thị hoặc xử lý theo nhu cầu của bạn
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

}

