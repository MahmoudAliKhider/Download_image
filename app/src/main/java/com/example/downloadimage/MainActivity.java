package com.example.downloadimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
  EditText urlEditText;
  ImageView imageView;
  Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        urlEditText=findViewById( R.id.editText);
        imageView= findViewById( R.id.image );
        button =findViewById( R.id.downloadButton );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownLoad();
            }
        } );

    }
    public void DownLoad(){
        if(!urlEditText.getText().toString().isEmpty()){
            Toast.makeText( MainActivity.this, "StartDownLoad", Toast.LENGTH_SHORT ).show();
            new DownLoadAsync().execute(urlEditText.getText().toString());
        }
    }
     private class DownLoadAsync extends AsyncTask<String,Void, Bitmap> {
         @Override
         protected Bitmap doInBackground(String... url) {
             Bitmap result = null;
             result = downLoad( url[0] );
             return result;
         }


         @Override
         protected void onPostExecute(Bitmap bitmap) {
             if (bitmap != null)
                 imageView.setImageBitmap( bitmap );
         }
     }

    private Bitmap downLoad(String s){
        Bitmap result=null;
        try {
            URL url=new URL( s );
            HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
            connection.connect();
            result = BitmapFactory.decodeStream( connection.getInputStream() );

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}

