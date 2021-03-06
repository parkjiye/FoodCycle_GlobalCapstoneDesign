package edu.skku.GlobalCapstoneDesign;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AftereatingActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    ImageView img;
    String cameraFilePath=null;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftereating);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sp = getSharedPreferences("FED_pref", Context.MODE_PRIVATE);
        img = findViewById(R.id.beforeimage);
        Button upload = findViewById(R.id.upload);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);

        checkPermission();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AftereatingRequest req=new AftereatingRequest(AftereatingActivity.this);
                req.asyncTask.execute(1);


            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                File cameraFile=null;
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap image = BitmapFactory.decodeStream(in);
                    img.setImageBitmap(image);
                    cameraFile=createImageFile2();
                    cameraFilePath=cameraFile.getAbsolutePath();
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("data_path",cameraFilePath);
                    editor.commit();
                    Log.e("asdf",sp.getString("data_path",cameraFilePath));

                    File tempFile=new File(sp.getString("path",cameraFilePath),"food.png");
                    Log.e("asdf", sp.getString("path","defaultValue")+"/food.png");
                    try{
                        tempFile.createNewFile();
                        FileOutputStream out=new FileOutputStream(tempFile);
                        image.compress(Bitmap.CompressFormat.PNG,100,out);
                        out.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public File createImageFile2() throws IOException{
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="Test_"+timeStamp+"_";
        File storageDir= getApplication().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        Log.e("asdf",storageDir.toString());
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("path",storageDir.toString());
        editor.commit();
        File image=File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    public void checkPermission(){
        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissioninfo == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"SDCard ?????? ?????? ??????",Toast.LENGTH_SHORT).show();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(),"????????? ????????? ??????",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String str = null;
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                str = "SD Card ???????????? ??????";
            else str = "SD Card ???????????? ??????";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
