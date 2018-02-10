package com.example.root.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    final static int SELECT_IMAGE = 1;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = (ImageView) findViewById(R.id.image);
    }

    public void setImage(View view) {
        changeBrightness(30);
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE);
    }

    ColorMatrix effectMatrix = new ColorMatrix();

    public void setEffect(){
        ColorFilter filter = new ColorMatrixColorFilter(effectMatrix);
        mImage.setColorFilter(filter);
    }

    public void changeBrightness(int d){
        ColorMatrix brMatrix = new ColorMatrix(
                 new float[]{
                      1, 0, 0, 0, d,
                      0, 1, 0, 0, d,
                      0, 0, 1, 0, d,
                      0, 0, 0, 1, 0
                 });
        effectMatrix.postConcat(brMatrix);
        setEffect();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Image loaded", Toast.LENGTH_SHORT);
        if (resultCode == RESULT_OK){
            Bitmap bitmap = null;
            if (requestCode == SELECT_IMAGE) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int scaleWidth = 1000;
                int scaleHeight = 1000 * bitmap.getHeight() / bitmap.getWidth();
                bitmap = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true);
                mImage.setImageBitmap(bitmap);
                Toast.makeText(this, "Image loaded", Toast.LENGTH_SHORT);
            }
        }
    }

}
