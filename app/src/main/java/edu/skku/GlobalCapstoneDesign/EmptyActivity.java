package edu.skku.GlobalCapstoneDesign;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button calories = findViewById(R.id.calories);
        Button exit = findViewById(R.id.exit);
    }

}
