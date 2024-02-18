package com.ama.trainingtask.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ama.trainingtask.R;
import com.ama.trainingtask.view_model.AppUtil;
import com.ama.trainingtask.view_model.RegisterLocalDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText username, email, password;
    private Button btnRegister;
    private RegisterLocalDatabase mRegisterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Tool bar
        mToolbar = findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegisterDB = new RegisterLocalDatabase(this);

        username = findViewById(R.id.edtUsernameReg);
        email = findViewById(R.id.edtEmailReg);
        password = findViewById(R.id.edtPasswordReg);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNow();
            }
        });
    }

    private void registerNow(){
        String txtUsername = username.getText().toString();
        String txtEmail = email.getText().toString();
        String txtPassword = password.getText().toString();
        if(!txtUsername.isEmpty() && !txtEmail.isEmpty() && !txtPassword.isEmpty()){
            checkRegister(txtEmail, txtPassword, txtUsername);
        } else {
            Toast.makeText(getApplicationContext(), "Please fill all the information", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkRegister(String regEmail, String regPassword, String Username){
        Cursor resultRegister = mRegisterDB.getDataUser(regEmail);

        if (resultRegister.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
        } else {
            newRegistration(regEmail, regPassword, Username);
        }
        resultRegister.close();
    }

    private void newRegistration(String regEmail, String regPassword, String regUsername){

        String userId = AppUtil.getRandomString(8);
        boolean registerNow = mRegisterDB.addData(userId, regEmail, regUsername, regPassword);

        if(registerNow){
            Toast.makeText(getApplicationContext(), "Successful register", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}