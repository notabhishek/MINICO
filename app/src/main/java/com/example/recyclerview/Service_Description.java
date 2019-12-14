package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Service_Description extends AppCompatActivity {

    private TextView nameView,descView,contactView,priceView,creatorView;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__description);
        Intent intent = getIntent();
        String Title=intent.getExtras().getString("Title");
        String Description=intent.getExtras().getString("Description");
        String Phone=intent.getExtras().getString("Contact");
        String Price=intent.getExtras().getString("Price");
        String Seller=intent.getExtras().getString("Creator's Name");
        String image=intent.getExtras().getString("Image Id");

        nameView = (TextView)findViewById(R.id.title_View);
        descView = (TextView)findViewById((R.id.description_View));
        priceView = (TextView) findViewById(R.id.price_View);
       contactView = (TextView) findViewById(R.id.phone_View);
        img = (ImageView) findViewById(R.id.image_View2);
        creatorView = (TextView)findViewById((R.id.seller));

        nameView.setText(Title);
       descView.setText("Desc : "+Description);
        contactView.setText("Contact : "+ Phone);
        priceView.setText("Rs"+Price);
        creatorView.setText("Creator : "+Seller);

        try {
            StorageReference storageReference ;
            final String fileName = image;

            String fileExtension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                fileExtension = fileName.substring(i+1);
            }

            FirebaseStorage storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference().child("Images").child(fileName);
            final File file = File.createTempFile("image", fileExtension);
            storageReference.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            //imageView.setImageBitmap(bitmap);
                            img.setImageBitmap(bitmap);
                            //holder.img_service_image.setImageBitmap(bitmap);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Info", "Could not load image!!");
                            //Toast.makeText(MainActivity.this, "Image Failed to load!!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Log.i("Downloading image" , fileName);
                        }
                    });

        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i("Info", "Could not load image!!");
        }



    }
}
