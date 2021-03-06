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
import com.example.traveltheworld.Activityes.Profile;
import com.example.traveltheworld.R;
import com.example.traveltheworld.databinding.ActivityChangePasswordBinding;
import com.example.traveltheworld.databinding.ActivityChatFragmentBinding;
import com.example.traveltheworld.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
            ActivityChangePasswordBinding binding;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        progressDialog = new ProgressDialog(this);
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), Profile.class));
            }
        });
        binding.btnSaveNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener();
            }
        });
    }

    private void onClickListener() {
        String newPass = binding.etNewPass.getText().toString().trim();
        String reNewPass = binding.etReNewPass.getText().toString().trim();

        if ( newPass.equals("")||reNewPass.equals("")) {
            Toast.makeText(ChangePassword.this, "Vui l??ng nh???p ?????y ????? th??ng tin ????ng k??", Toast.LENGTH_SHORT).show();
        }else {
            if(newPass.equals(reNewPass)){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                progressDialog.show();
                user.updatePassword(newPass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePassword.this, "Thay ?????i m???t kh???u th??nh c??ng", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(), Login.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }else{
                                    Toast.makeText(ChangePassword.this, "Thay ?????i m???t kh???u kh??ng th??nh c??ng vui l??ng ????ng nh???p l???i t??i kho???n", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getBaseContext(),Login.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            }
                        });
            }else{
                Toast.makeText(ChangePassword.this, "M???t kh???u kh??ng tr??ng kh???p", Toast.LENGTH_SHORT).show();
            }
        }
    }
}