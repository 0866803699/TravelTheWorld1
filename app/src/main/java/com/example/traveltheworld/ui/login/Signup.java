package com.example.traveltheworld.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.traveltheworld.R;

import com.example.traveltheworld.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            binding= DataBindingUtil.setContentView(this, R.layout.activity_signup);
            progressDialog = new ProgressDialog(this);
            binding.tvSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onclickback();
                }
            });
            binding.btnSignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
////                    callAPIRequest();
////                    Toast.makeText(Signup.this, "đăng kí thành công quay lại đăng nhập", Toast.LENGTH_SHORT).show();
                    String userName = binding.etUsername.getText().toString().trim();
                    String userPassword = binding.etPassword.getText().toString().trim();
                    String rePass= binding.etNewPassword.getText().toString().trim();
//
//
                    if (userName.equals("") || userPassword.equals("")||rePass.equals("")){
                        Toast.makeText(Signup.this, "Vui lòng nhập đầy đủ thông tin đăng kí", Toast.LENGTH_SHORT).show();
                    }else {
                        if(userPassword.equals(rePass)){
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            progressDialog.show();
                            mAuth.createUserWithEmailAndPassword(userName, userPassword)
                                    .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Signup.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                                onclickback();
                                            } else {
                                                Toast.makeText(Signup.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(Signup.this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                            }
                    }
                }
            });
    }

    private void onclickback() {
        Intent intent = new Intent(Signup.this,Login.class);
        startActivity(intent);
        finishAffinity();
    }



//    private void callAPIRequest() {
//        String userLoginID = binding.etFullName.getText().toString().trim();
//        String userNumberPhone = binding.etPhoneNumber.getText().toString().trim();
//        String userName = binding.etUsername.getText().toString().trim();
//        String userPassword = binding.etPassword.getText().toString().trim();
//
//        RequestBody requestBodyLoginID = RequestBody.create(MediaType.parse("multipart/from-data"),userLoginID);
//        RequestBody requestBodyNumberPhone = RequestBody.create(MediaType.parse("multipart/from-data"),userNumberPhone);
//        RequestBody requestBodyName = RequestBody.create(MediaType.parse("multipart/from-data"),userName);
//        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/from-data"),userPassword);
//
//        ApiService.apiService.RequestBody(requestBodyLoginID,requestBodyNumberPhone,requestBodyName,requestBodyPassword).enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//               List<User> user=  response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(Signup.this, "Call API Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}