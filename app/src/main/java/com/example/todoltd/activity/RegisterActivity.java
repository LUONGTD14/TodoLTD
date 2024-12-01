package com.example.todoltd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoltd.R;
import com.example.todoltd.entity.User;
import com.example.todoltd.interfaceSOLID.showDialogInt1;
import com.example.todoltd.managerObject_Singleton.FireBaseDbManager;
import com.example.todoltd.managerObject_Singleton.FirebaseAuthManager;
import com.example.todoltd.utils.CheckValidates;
import com.example.todoltd.utils.PasswordEncoderDecoder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity implements showDialogInt1 {
    private TextView tvToLogin, wrEmailReg, wrPwReg, noMatPwReg, tvSuccess;;
    private FirebaseAuth fireBaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText nameReg, emailReg, pwReg, confrimPwReg;
    private Button btnReg;
    private CheckValidates checkValidates;
    private FirebaseAuthManager firebaseAuthManager;
    private FireBaseDbManager fireBaseDbManager;
    private PasswordEncoderDecoder passwordEncoderDecoder;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fireBaseAuth.getCurrentUser();
        if (currentUser != null) {
//            reload();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleToLogin();
        showValidEmail();
        showValidPw();
        showConfirmPw();
        handleResgister();
    }

    //click button register
    private void handleResgister() {
        btnReg.setOnClickListener(v -> {
            String name = nameReg.getText().toString().trim();
            String email = emailReg.getText().toString().trim();
            String pw1 = pwReg.getText().toString().trim();
            String pw2 = confrimPwReg.getText().toString().trim();

            if (checkValidates.isValidEmail(email) && checkValidates.isPasswordValid(pw1) && checkValidates.isValidConfirmPw(pw1, pw2)) {
                fireBaseAuth.createUserWithEmailAndPassword(email, pw1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = fireBaseAuth.getCurrentUser();
                                    Toast.makeText(getApplicationContext(), "Account created.", Toast.LENGTH_SHORT).show();

                                    // Save user information to the database
                                    saveUserInfoToDatabase(user.getUid(), name, email, pw1);
                                    showDialog(R.string.successfully, R.string.have_account_go_to_login, R.string.login, 1);
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(getApplicationContext(), "Email address is already in use.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }

        });
    }

    //add new user to realtime database
    private void saveUserInfoToDatabase(String userId, String name, String email, String pw) {
        databaseReference = firebaseDatabase.getReference("User");
        User newUser = new User(userId, name, email, passwordEncoderDecoder.encodePassword(pw));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(newUser);
                Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //show wrong email format
    private void showValidEmail() {
        emailReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidates.isValidEmail(s.toString())) {
                    wrEmailReg.setVisibility(View.VISIBLE);
                } else {
                    wrEmailReg.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //show wrong strong password
    private void showValidPw() {
        pwReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidates.isPasswordValid(s.toString())) {
                    wrPwReg.setVisibility(View.VISIBLE);
                } else {
                    wrPwReg.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //show wrong confirm password
    private void showConfirmPw() {
        confrimPwReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String p1 = pwReg.getText().toString().trim();
                if (!checkValidates.isValidConfirmPw(p1, s.toString())) {
                    noMatPwReg.setVisibility(View.VISIBLE);
                } else {
                    noMatPwReg.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //handle mo to login tab
    private void handleToLogin() {
        tvToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }



    public void clearInput() {
        tvSuccess.setVisibility(View.VISIBLE);
        nameReg.setText(null);
        nameReg.clearFocus();
        emailReg.setText(null);
        emailReg.clearFocus();
        pwReg.setText(null);
        pwReg.clearFocus();
        confrimPwReg.setText(null);
        confrimPwReg.clearFocus();
        wrEmailReg.setVisibility(View.INVISIBLE);
        wrPwReg.setVisibility(View.INVISIBLE);
        noMatPwReg.setVisibility(View.INVISIBLE);
    }



    //map view to controller
    private void init() {
        tvToLogin = findViewById(R.id.tvRegisterToLogin);
        nameReg = findViewById(R.id.edtNameUserRegister);
        emailReg = findViewById(R.id.edtEmailUerRegister);
        pwReg = findViewById(R.id.edtPwUerRegister);
        confrimPwReg = findViewById(R.id.edtPwConfirmUerRegister);
        wrEmailReg = findViewById(R.id.tvWrEmailRegister);
        wrPwReg = findViewById(R.id.tvWrPassRegister);
        noMatPwReg = findViewById(R.id.tvNoMatPwRegister);
        tvSuccess = findViewById(R.id.tvsuccess);
        btnReg = findViewById(R.id.btnRegister);

        fireBaseAuth = firebaseAuthManager.getInstance().getFirebaseAuth();
        firebaseDatabase = fireBaseDbManager.getInstance().getFirebaseAuth();

        checkValidates = new CheckValidates();
        passwordEncoderDecoder = new PasswordEncoderDecoder();
    }

    @Override
    public void showDialog(int title, int text, int textBtn, int type) {
        Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(R.layout.dialog1_button);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.setCancelable(false);
        ConstraintLayout constraintLayout = dialog.findViewById(R.id.bgDialog);
        Button btnDialog = dialog.findViewById(R.id.btnDialog);
        TextView tvTitle = dialog.findViewById(R.id.titleDialog);
        TextView tvText = dialog.findViewById(R.id.textDialog);
        tvTitle.setText(title);
        tvText.setText(text);
        btnDialog.setText(textBtn);

        if(type == 0){
            constraintLayout.setBackgroundTintList(getResources().getColorStateList(R.color.red1));
            btnDialog.setTextColor(getResources().getColor(R.color.red1));
        }else{
            constraintLayout.setBackgroundTintList(getResources().getColorStateList(R.color.primaryTextColor));
            btnDialog.setTextColor(getResources().getColor(R.color.primaryTextColor));
        }


        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 0){
                    clearInput();
                }
                dialog.dismiss();
                Toast.makeText(RegisterActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}