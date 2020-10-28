package com.example.retrofittest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class uploadImage extends AppCompatActivity {

    private ImageView pix;
    private Button upload;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        pix = findViewById(R.id.image_view);
        upload = findViewById(R.id.upload);

        pix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImage();

            }
        });
    }

    private void sendImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imgByte, Base64.DEFAULT);

        Call<SaveImageModel> call = RetrofitClient.getInstance().getAPI().sendImage(encodedImage);
        call.enqueue(new Callback<SaveImageModel>() {
            @Override
            public void onResponse(Call<SaveImageModel> call, Response<SaveImageModel> response) {
                Toast.makeText(uploadImage.this, response.body().getRemarks(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<SaveImageModel> call, Throwable t) {
                Toast.makeText(uploadImage.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                pix.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}