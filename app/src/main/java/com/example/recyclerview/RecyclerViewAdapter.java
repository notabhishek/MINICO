package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    //private List<service> mData;
    private List<Query> mData;

    // public RecyclerViewAdapter(Context mContext, List<service> mData) {
    public RecyclerViewAdapter(Context mContext, List<Query> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_service_title.setText(mData.get(position).getQuery_name());
        //holder.img_service_image.setImageResource(mData.get(position).getThumbnail());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference ;

        try {
            final String fileName =mData.get(position).getQuery_image_id();
            Log.i("In Adapter" , fileName);
            String fileExtension = "";
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                fileExtension = fileName.substring(i+1);
            }


            storageReference = storage.getReference().child("Images").child(fileName);
            final File file = File.createTempFile("image", fileExtension);
            storageReference.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            //imageView.setImageBitmap(bitmap);
                            holder.img_service_image.setImageBitmap(bitmap);
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

            //holder.img_service_image.setImageResource(mData.get(position).getQuery_image_id());
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i("Info", "Could not load image!!");
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Service_Description.class);
                intent.putExtra("Title",mData.get(position).getQuery_name());
                intent.putExtra("Description",mData.get(position).getQuery_desc());
                intent.putExtra("Creator's Name",mData.get(position).getQuery_creator());
                //intent.putExtra("Category",mData.get(position).getCategory());
                intent.putExtra("Price",Integer.toString(mData.get(position).getQuery_price()));
                intent.putExtra("Contact",mData.get(position).getQuery_contact());
                intent.putExtra("Image Id",mData.get(position).getQuery_image_id());
                mContext.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_service_title;
        ImageView img_service_image;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_service_title = (TextView) itemView.findViewById(R.id.service_title);
            img_service_image = (ImageView) itemView.findViewById(R.id.service_image);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}
