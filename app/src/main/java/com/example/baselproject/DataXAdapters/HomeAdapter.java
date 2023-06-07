package com.example.baselproject.DataXAdapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baselproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ArrayList<HomeStuff> userArrayList;

    private final SelectListener listener ;

    public HomeAdapter(Context context, ArrayList<HomeStuff> userArrayList , SelectListener listener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.listener = listener ;
    }

    @NonNull
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.homeitem,parent, false) ;

        return new MyViewHolder(v , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, int position) {
        HomeStuff homeStuff = userArrayList.get(position);
        holder.Description.setText(homeStuff.getDescription());
        holder.name.setText(homeStuff.getName());
        StorageReference imageRef = storageRef.child(homeStuff.getImage());
        imageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Load the image into an ImageView using Glide or Picasso
                        Glide.with(context).load(uri).into(holder.iv);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors that occur during the download
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv ;
        TextView Description , name;
        public MyViewHolder(@NonNull View itemView , SelectListener listener) {
            super(itemView);
            Description = itemView.findViewById(R.id.tvhomeDescription);
            name = itemView.findViewById(R.id.tvhomeitem);
            iv = itemView.findViewById(R.id.IVHomeItem) ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int pos = getAdapterPosition() ;
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClicked(pos);
                        }
                    }
                }
            });
        }
    }
}
