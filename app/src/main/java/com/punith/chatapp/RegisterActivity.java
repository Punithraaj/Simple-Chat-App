package com.punith.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username, email,passwd;
    Button btn_register;
    FirebaseAuth auth;
    DatabaseReference dbReferrence;
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Register");
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        passwd = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        TextView login = (TextView)findViewById(R.id.lnkLogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String user_email = email.getText().toString();
                String pwd = passwd.getText().toString();


                if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(user_email) || TextUtils.isEmpty(pwd))
                {
                    Toast.makeText(RegisterActivity.this, "Please fill all the fileds", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidPassword(pwd)){
                    Toast.makeText(RegisterActivity.this, "Password must contain at least one uppercase letter, one lower case letter, one number,special characters and Minimum length of eight character", Toast.LENGTH_SHORT).show();
                }else{
                    register(userName,user_email,pwd);
                }
            }
        });

    }

    private void register(final String userName, String email, String password){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        assert firebaseUser != null;
                        String userId = firebaseUser.getUid();

                        dbReferrence = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                        HashMap<String, String> registerMap = new HashMap<>();
                        registerMap.put("id",userId);
                        registerMap.put("username",userName);
                        registerMap.put("imageURL","default");

                        dbReferrence.setValue(registerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(RegisterActivity.this, "You cant register with this email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    public boolean isValidPassword(String s) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return !TextUtils.isEmpty(s) && pattern.matcher(s).matches();
    }

}



