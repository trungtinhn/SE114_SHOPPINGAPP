package com.example.shoppingapp.StaffView.activity;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.adapter.adapter_admin_control;
import com.example.shoppingapp.itf_RCV_list_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class activity_admin_control extends AppCompatActivity implements itf_RCV_list_item, AdapterView.OnItemSelectedListener, Filterable {

    RecyclerView RCV;
    Button btn_add;
    ArrayList<User> StaffList;
    adapter_admin_control adapterAdmin;
    private FirebaseAuth firebaseAuth;
    SearchView searchView;
    User admin;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    private int admin_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_admin_control);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        RCV = findViewById(R.id.RCV_admin);
        btn_add = findViewById(R.id.btn_add);

        RCV.setHasFixedSize(true);
        RCV.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore = FirebaseFirestore.getInstance();

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

    private void EventAdd()
    {
        DialogPlus dialogPlus = DialogPlus.newDialog( this)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.activity_admin_add))
                .setExpanded(false)
                .create();

        View holderView = (LinearLayout) dialogPlus.getHolderView();

        EditText edName = holderView.findViewById(R.id.txt_name);
        Spinner edSex = holderView.findViewById(R.id.txt_sex);
        Spinner edStatus = holderView.findViewById(R.id.txt_status);
        TextView edDob = holderView.findViewById(R.id.txt_dob);
        EditText edPhone = holderView.findViewById(R.id.txt_phone);
        EditText edMail = holderView.findViewById(R.id.txt_mail);
        EditText edPass = holderView.findViewById(R.id.txt_pass);
        Button btnUpdate = holderView.findViewById(R.id.btn_update);

        ArrayAdapter<CharSequence> gender = ArrayAdapter.createFromResource(this,R.array.gender,
                R.layout.spinner_item);
        gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edSex.setAdapter(gender);
        edSex.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> status = ArrayAdapter.createFromResource(this,R.array.status,
                R.layout.spinner_item);
        status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edStatus.setAdapter(status);
        edStatus.setOnItemSelectedListener(this);

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edName.getText().toString())) {
                    edName.setError("Please fill name!");
                    return;
                }
                if(TextUtils.isEmpty(edDob.getText().toString())){
                    edDob.setError("Please fill date of birth!");
                    return;
                }
                if(TextUtils.isEmpty(edPhone.getText().toString())){
                    edPhone.setError("Please fill phone!");
                    return;
                }
                if(TextUtils.isEmpty(edMail.getText().toString())){
                    edMail.setError("Please fill mail!");
                    return;
                }
                if(TextUtils.isEmpty(edPass.getText().toString())) {
                    edPass.setError("Please fill password!");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("ID",String.valueOf(admin_count));
                map.put("name",edName.getText().toString());
                map.put("sex",edSex.getSelectedItem().toString());
                map.put("dob",edDob.getText().toString());
                map.put("status",edStatus.getSelectedItem().toString());
                map.put("phoneNum",edPhone.getText().toString());
                map.put("email",edMail.getText().toString());
                map.put("pass",edPass.getText().toString());

                firebaseFirestore.collection("ADMIN").add(map)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(activity_admin_control.this, "Success add the data", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                                EventInitListener();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                                Toast.makeText(activity_admin_control.this, "Fail to add the data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialogPlus.show();

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
                    //object.setKey(d.getId());
                    if(!Objects.requireNonNull(object).getMaND().equals(firebaseAuth.getUid())
                            && Objects.equals(object.getLoaiND(), "Staff")) StaffList.add(object);
                    admin_count++;
                    adapterAdmin.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
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
        User object = StaffList.get(position);
        intent.putExtra("Admin", (Serializable) admin);
        intent.putExtra("Data", (Serializable) object);
        intent.putExtra("Position", position);
        Bundle args = new Bundle();
        args.putSerializable("ArrayList",(Serializable) StaffList);
        intent.putExtra("Bundle",args);
        startActivity(intent);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
