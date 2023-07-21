package com.example.shoppingapp.StaffView.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.Login.User;
import com.example.shoppingapp.R;
import com.example.shoppingapp.StaffView.item.admin_object;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class activity_admin_detail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn_edit, btn_remove;
    User object;
    User admin;
    int position;
    ProgressDialog progressDialog;
    ArrayList<admin_object> AdminList;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    DocumentReference docRef;
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
        AdminList = (ArrayList<admin_object>) args.getSerializable("ArrayList");

        db = FirebaseFirestore.getInstance();
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
        String uri=object.getAvatar();
        try{
            if(uri.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"null is recieved",Toast.LENGTH_SHORT).show();
            }
            else Picasso.get().load(uri).into((ImageView) findViewById(R.id.img_staff));
        }
        catch (Exception e)
        {

        }

        ((TextView) findViewById(R.id.adminName)).setText(admin.getFullName());
        ((TextView) findViewById(R.id.adminID)).setText(admin.getMaND());
        uri=admin.getAvatar();
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
    }
    private void EventEdit()
    {
        DialogPlus dialogPlus = DialogPlus.newDialog( this)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.activity_admin_detail_edit))
                .setExpanded(false)
                .create();

        View holderView = (LinearLayout) dialogPlus.getHolderView();

        EditText edName = holderView.findViewById(R.id.txt_name);
        Spinner edSex = holderView.findViewById(R.id.txt_sex);
        TextView edDob = holderView.findViewById(R.id.txt_dob);
        EditText edPhone = holderView.findViewById(R.id.txt_phone);
        EditText edMail = holderView.findViewById(R.id.txt_mail);
        Spinner edStatus = holderView.findViewById(R.id.txt_status);
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

//        edName.setText(object.getName());
//        edStatus.setSelection(object.getStatusIndex());
//        edSex.setSelection(object.getSexIndex());
//        edDob.setText(object.getDob());
//        edPhone.setText(object.getPhoneNum());
//        edMail.setText(object.getEmail());
//        edPass.setText(object.getPass());

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
                map.put("name",edName.getText().toString());
                map.put("sex",edSex.getSelectedItem().toString());
                map.put("status",edStatus.getSelectedItem().toString());
                map.put("dob",edDob.getText().toString());
                map.put("phoneNum",edPhone.getText().toString());
                map.put("email",edMail.getText().toString());
                map.put("pass",edPass.getText().toString());

                docRef.update(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity_admin_detail.this, "Success updating the data", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                                Intent intent = new Intent(activity_admin_detail.this,activity_admin_control.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                                Toast.makeText(activity_admin_detail.this, "Fail to update the data", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
