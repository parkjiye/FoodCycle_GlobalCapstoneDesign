package edu.skku.GlobalCapstoneDesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button beforeeating=findViewById(R.id.beforeeating);
        Button aftereating=findViewById(R.id.aftereating);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        beforeeating.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), edu.skku.GlobalCapstoneDesign.BeforeeaingActivity.class);
                startActivity(intent);
            }
        });

        aftereating.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), edu.skku.GlobalCapstoneDesign.AftereatingActivity.class);
                startActivity(intent);
            }
        });
    }


}