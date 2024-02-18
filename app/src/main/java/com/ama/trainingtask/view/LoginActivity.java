package com.ama.trainingtask.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ama.trainingtask.R;
import com.ama.trainingtask.model.UserModel;
import com.ama.trainingtask.view_model.NoteLocalDatabase;
import com.ama.trainingtask.view_model.RegisterLocalDatabase;
import com.ama.trainingtask.view_model.UserManager;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextView registerTxt;
    private EditText logEmail, logPassword;
    private Button btnLogin;
    private RegisterLocalDatabase mRegisterDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRegisterDB = new RegisterLocalDatabase(this);
        registerTxt = findViewById(R.id.txtRegister);
        logEmail = findViewById(R.id.edtEmail);
        logPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDataEnter();
            }
        });

        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void checkDataEnter(){
        String txtLogEmail = logEmail.getText().toString();
        String txtLogPassword = logPassword.getText().toString();

        if(!txtLogEmail.isEmpty() && !txtLogPassword.isEmpty()){
            checkRegister(txtLogEmail, txtLogPassword);
        } else {
            Toast.makeText(getApplicationContext(), "Please fill all the information", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkRegister(String emailLog, String passwordLog){
        Cursor resultRegister = mRegisterDB.getDataUser(emailLog);
        Log.d("LoginTest", resultRegister.toString());

        if (resultRegister.moveToFirst()) {
            String userid = resultRegister.getString(1);
            String userName = resultRegister.getString(2);
            String email = resultRegister.getString(3);
            String password = resultRegister.getString(4);

            if(Objects.equals(password, passwordLog)){
                UserModel user = new UserModel(userid, email, password, userName);
                UserManager.getInstance(this).userLogin(user);

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        } else {
            // The email does not exist in the database
            Toast.makeText(getApplicationContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
        }
        resultRegister.close();
    }
}