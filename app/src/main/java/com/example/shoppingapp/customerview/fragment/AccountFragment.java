package com.example.shoppingapp.customerview.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.customerview.BottomNavigationCustomActivity;
import com.example.shoppingapp.customerview.activity.OrderActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


public class AccountFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private LinearLayout YourOrder;
    private LinearLayout Changepass;
    private LinearLayout Help;
    private LinearLayout Profile;

    private  LinearLayout Logout;
    private TextView txtFullName, txtUserID;
    private BottomNavigationCustomActivity bottomNavigationCustomActivity;
    private ShapeableImageView imgAvt;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        bottomNavigationCustomActivity = (BottomNavigationCustomActivity) getActivity();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        YourOrder = view.findViewById(R.id.l1);
        Changepass = view.findViewById(R.id.l4);
        Help = view.findViewById(R.id.l2);
        Profile = view.findViewById(R.id.l3);

        Logout = view.findViewById(R.id.l5);
        txtFullName = view.findViewById(R.id.user_name);
        imgAvt = view.findViewById(R.id.img_avt_account);
        // Inflate the layout for this fragment

        setOnCLickHelp();
        setOnClickChangePass();
        setOnClickLogOut();
        if (currentUser != null) {
            String userID = currentUser.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference usersRef = db.collection("NGUOIDUNG");
            usersRef.document(userID).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fullName = documentSnapshot.getString("fullName");
                            String avatarURL = documentSnapshot.getString("avatar");
                            txtFullName.setText(fullName);
                            Picasso.get().load(avatarURL).into(imgAvt);
                        }
                    })
                    .addOnFailureListener(e -> {
                    });
        }


        setOnClick();

        return view;
    }



    private void setOnCLickHelp() {
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoCLickHelp();
            }
        });
    }
    private void setOnClickChangePass() {
        Changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoChangePass();
            }
        });
    }
    private void setOnClickLogOut() {
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoLogOut();

            }
        });
    }

    private void setOnClick() {

        YourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationCustomActivity.gotoOrderActivity();

            }
        });
    }
}