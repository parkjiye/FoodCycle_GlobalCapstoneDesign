package edu.skku.GlobalCapstoneDesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class FirstPage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button login=findViewById(R.id.login);
        Button register=findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent=new Intent(getBaseContext(), edu.skku.GlobalCapstoneDesign.LoginActivity.class);
                startActivity(login_intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent=new Intent(getBaseContext(), edu.skku.GlobalCapstoneDesign.RegisterActivity.class);
                startActivity(register_intent);

            }
        });
    }
}
