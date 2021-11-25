package edu.skku.GlobalCapstoneDesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth=null;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth=FirebaseAuth.getInstance();

        editTextName=findViewById(R.id.Name_textview);
        editTextEmail=findViewById(R.id.email_textview);
        editTextPassword=findViewById(R.id.password_textview);

        register=findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextPassword.getText().toString().equals("") && !editTextEmail.getText().toString().equals("")){
                    Log.e("Asdf",editTextEmail.getText().toString());
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString());
                }else{
                    Toast.makeText(RegisterActivity.this, "Check the input", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(String email, String password, String name){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register Succesful", Toast.LENGTH_SHORT).show();
                            Intent mainactivity=new Intent(getBaseContext(), MainActivity.class);
                            startActivity(mainactivity);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
