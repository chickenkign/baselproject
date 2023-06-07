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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ArrayList<User> userArrayList;
    private final SelectListener listener ;
    public MyAdapter(Context context, ArrayList<User> userArrayList , SelectListener listener) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.listener = listener ;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent, false) ;

        return new MyViewHolder(v , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        User user = userArrayList.get(position);

        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
        holder.phone.setText(user.getPhone());
        StorageReference imageRef = storageRef.child(user.getImage());
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
        TextView username , email , phone;
        ImageView iv ;

        public MyViewHolder(@NonNull View itemView , SelectListener listener) {
            super(itemView);
            username = itemView.findViewById(R.id.tvusername);
            email = itemView.findViewById(R.id.tvemail);
            phone = itemView.findViewById(R.id.tvPhone);
            iv = itemView.findViewById(R.id.IVItem) ;
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
