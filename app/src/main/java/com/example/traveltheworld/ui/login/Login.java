package com.example.traveltheworld.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.traveltheworld.Activityes.MainActivity;
import com.example.traveltheworld.R;

import com.example.traveltheworld.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });
        binding.tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clickForgotPass();
            }
        });

    }

    private void clickForgotPass() {
        Intent intent2 = new Intent(Login.this,ForgotPassword.class);
        startActivity(intent2);
    }

    private void clickLogin() {

        String userName = binding.etUsername.getText().toString().trim();
        String userPassword = binding.etPassword.getText().toString().trim();
        if (userName.equals("")||userPassword.equals("")){
            Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
            }
            else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(userName, userPassword)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent= new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        }






//        if (userList == null || userList.isEmpty()){
//            return;
//        }
//        boolean isHasUser= false;
//        for (User user:userList){
//            if (userName.equals(user.getUserName())&&userPassword.equals(user.getUserPassword())){
//                isHasUser=true;
//                break;
//            }
//        }
//        if (isHasUser){
//            Intent intent1= new Intent(Login.this, MainActivity.class);
//            startActivity(intent1);
//        }
//        else {
//          Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
//        }
    }

//    private void getListUsers() {
//        ApiService.apiService.getListUsersLogin().enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                userList = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(Login.this, "Call API Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
