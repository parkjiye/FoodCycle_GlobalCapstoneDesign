package edu.skku.GlobalCapstoneDesign;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AftereatingRequest {
    private final WeakReference<Context> contextRef;
    SharedPreferences sp;

    public AftereatingRequest(Context context){
        contextRef=new WeakReference<>(context);
        sp=context.getSharedPreferences("FED_pref", Context.MODE_PRIVATE);
    }

    AsyncTask<Integer, Double, String> asyncTask=new AsyncTask<Integer, Double, String>() {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(sp.getString("result","defaultValue").contains("leftover")){
                Intent leftover=new Intent(contextRef.get(), LeftoverActivity.class);
                contextRef.get().startActivity(leftover);
                ((Activity)contextRef.get()).finish();
            }
            else{
                Intent empty=new Intent(contextRef.get(), EmptyActivity.class);
                contextRef.get().startActivity(empty);
                ((Activity)contextRef.get()).finish();
            }
        }

        @Override
        protected String doInBackground(Integer... integers) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image","/C:/Users/luna/Pictures/84-841280_download-sesshomaru-inuyasha-wallpaper-full-hd-wallpapers-sesshomaru.jpg",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(sp.getString("path","defaultValue")+"/food.png")))
                    .build();
            Request request = new Request.Builder()
                    .url("http://115.145.179.31:5000/")
                    .method("POST", body)
                    .build();

            String result="";
            try {
                Response response = client.newCall(request).execute();
                //Log.e("asdf", response.body().string());

                String[] array=response.body().string().split(" ");
                Log.e("asdf", array[array.length-1]);

                SharedPreferences.Editor editor=sp.edit();
                editor.putString("result",array[array.length-1]);
                editor.commit();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    };
}
