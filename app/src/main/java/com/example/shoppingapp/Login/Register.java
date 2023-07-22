package com.example.shoppingapp.Login;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.R;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Base64;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    private EditText emailTextView, userNameTextView, phoneNumberTextView, passwordTextView, confirmpasswordTextView;
    private EditText DayofBirthTextView;
    private Button Btn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://se114-df58a-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // initialising all views through id defined above
        emailTextView = findViewById(R.id.editTextEmail);
        passwordTextView = findViewById(R.id.editTextPassword);
        confirmpasswordTextView = findViewById(R.id.editTextConfirmPassword);
        userNameTextView = findViewById(R.id.editTextFullName);
        phoneNumberTextView = findViewById(R.id.editTextPhone);
        DayofBirthTextView = findViewById(R.id.editTextDayOfBirth);
        DayofBirthTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DayPicker();
                }
            }
        });
        Btn = findViewById(R.id.buttonRegister);
        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = userNameTextView.getText().toString();
                final String emailUTF = emailTextView.getText().toString();
                final String email = Base64.getEncoder().encodeToString(emailUTF.getBytes());
                //String encodedEmailFromDatabase = "bGVkYW5ndGh1b25nMjAwM0BnbWFpbC5jb20=";
                //String decodedEmail = new String(Base64.getDecoder().decode(encodedEmailFromDatabase));
                // Cách mã hóa lại code
                final String password = passwordTextView.getText().toString();
                final String confirmPassword = confirmpasswordTextView.getText().toString();
                final String phoneNumber = phoneNumberTextView.getText().toString();
                final String dayofbirth = DayofBirthTextView.getText().toString();

                if(fullname.isEmpty() || email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty())
                {
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }
                else if(!password.equals(confirmPassword))
                {
                    Toast.makeText(Register.this, "Password is not matching, please check Password and  Confirm Password again", Toast.LENGTH_SHORT).show();

                }
                if (password.length() < 6) {
                    Toast.makeText(Register.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(email))
                            {
                                Toast.makeText(Register.this, "Email has already registered, try another Email", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // sending data to firebase
                                reference.child("Users").child(email).child("Email").setValue(emailUTF);
                                reference.child("Users").child(email).child("LoaiND").setValue("customer");
                                registerNewUser();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    public static String sanitizeEmail(String email) {
        String sanitizedEmail = email.replaceAll(".", ",");
        return sanitizedEmail;
    }


    public void DayPicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Update EditText with selected date
                        EditText editTextDOB = findViewById(R.id.editTextDayOfBirth);
                        editTextDOB.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


    private void registerNewUser()
    {
        // Take the value of two edit texts in Strings
        String email, password, fullname, phonenumber, dayofbirth;
        String avatar = null;
        String diachi = null;
        String gioitinh = null;

        String status = "Online";
        String userID;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        fullname = userNameTextView.getText().toString();
        phonenumber = phoneNumberTextView.getText().toString();
        dayofbirth = DayofBirthTextView.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        // Trong phương thức registerNewUser()


        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userID = task.getResult().getUser().getUid();
                            User user = new User(fullname, email, dayofbirth,phonenumber, userID, avatar,
                                    diachi, gioitinh, status,"customer");
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            CollectionReference usersCollection = db.collection("NGUOIDUNG");

                            usersCollection.document(userID).set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Register.this, "Register Successful, Login Now!", Toast.LENGTH_SHORT).show();
                                            Intent intent
                                                    = new Intent(Register.this,
                                                    LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                        }

                    }
                });
    }

}
