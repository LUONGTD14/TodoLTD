package com.example.todoltd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoltd.R;
import com.example.todoltd.interfaceSOLID.showDialogInt1;
import com.example.todoltd.managerObject_Singleton.FirebaseAuthManager;
import com.example.todoltd.utils.CheckValidates;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements showDialogInt1 {
    private TextView tvToRegister, wrongEmailLogin, wrongPassLogin;
    private Button btnLogin;
    private EditText edtEmailLogin, edtPassLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuthManager firebaseAuthManager;
    private CheckValidates checkValidates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleToRegister();
        handleLogin();

//        handleShowWrongText();
        handleHide();
    }

    //click login button
    private void handleLogin() {
        btnLogin.setOnClickListener(v -> {
            String email = edtEmailLogin.getText().toString().trim();
            String pass = edtPassLogin.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                wrongEmailLogin.setText("Email trống");
                wrongEmailLogin.setVisibility(View.VISIBLE);
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                wrongPassLogin.setText(R.string.wrong_password);
                wrongPassLogin.setVisibility(View.VISIBLE);
                return;
            }
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {//fail pass
                                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {//not exist
                                        Log.e("email", "email not exist");
                                        wrongEmailLogin.setText(R.string.email_not_exist);
                                        wrongEmailLogin.setVisibility(View.VISIBLE);
                                    } else {
                                        wrongPassLogin.setText(R.string.check_info_login);
                                        wrongPassLogin.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (!(task.getException() instanceof FirebaseAuthInvalidUserException) &&
                                        !(task.getException() instanceof FirebaseAuthInvalidCredentialsException)) {//other issue
                                    showDialog(
                                            R.string.login_fail,
                                            R.string.check_email_pass,
                                            R.string.try_again, 0);
                                }
                            }
                        }
                    });
        });
    }

    //handle hide wrong text
    private void handleHide() {
        edtEmailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkValidates.isValidEmail(s.toString())) {
                    wrongEmailLogin.setText(R.string.wrong_email_format);
                    wrongEmailLogin.setVisibility(View.VISIBLE);
                } else {
                    wrongEmailLogin.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPassLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wrongPassLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //move to register tab
    private void handleToRegister() {
        tvToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    //map view to controller
    private void init() {
        tvToRegister = findViewById(R.id.tvLoginToRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);
        wrongEmailLogin = findViewById(R.id.wrongEmailLogin);
        wrongPassLogin = findViewById(R.id.wrongPasslLogin);

        firebaseAuth = firebaseAuthManager.getInstance().getFirebaseAuth();
        checkValidates = new CheckValidates();
    }

    @Override
    public void showDialog(int title, int text, int textBtn, int type) {
        Dialog dialog = new Dialog(LoginActivity.this);
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
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAuth = null;
        checkValidates = null;
    }
}