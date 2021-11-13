package com.example.traveltheworld.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.traveltheworld.Activityes.MainActivity;
import com.example.traveltheworld.R;
import com.example.traveltheworld.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        progressDialog = new ProgressDialog(this);
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        binding.btnFogotPa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onclick();
            }
        });
    }

    private void onclick() {
        String Email = binding.etEmail.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (Email.equals("")){
            Toast.makeText(ForgotPassword.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.show();
            auth.sendPasswordResetEmail(Email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(ForgotPassword.this, "Vui lòng vào Email thay đổi mật khẩu", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getBaseContext(),Login.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                            else {
                                Toast.makeText(ForgotPassword.this, "Không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
        }
        );

    }
}
}