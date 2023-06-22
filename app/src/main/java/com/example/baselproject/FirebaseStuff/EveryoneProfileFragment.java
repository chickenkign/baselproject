package com.example.baselproject.FirebaseStuff;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselproject.R;
import com.example.baselproject.Recycler.HomeRecyclerFragment;
import com.example.baselproject.Recycler.RecyclerViewFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EveryoneProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EveryoneProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String email , name , phone , path ;
    TextView TVemail , TVname , TVphone , visit ;
    ImageView iv , Goback;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    static int PERMISSION_CODE = 100 ;
    StorageReference storageRef = storage.getReference();

    public EveryoneProfileFragment(String email , String name , String phone , String path) {
        this.email = email ;
        this.name = name ;
        this.phone = phone ;
        this.path = path ;
    }
    public EveryoneProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EveryoneProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EveryoneProfileFragment newInstance(String param1, String param2) {
        EveryoneProfileFragment fragment = new EveryoneProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_everyone_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponenets();
    }

    private void connectcomponenets() {
        TVemail = getView().findViewById(R.id.EveryoneProfileEmail) ;
        TVname = getView().findViewById(R.id.EveryoneProfileUsername) ;
        Goback = getView().findViewById(R.id.IVGOBackEveryoneProfile) ;
        visit = getView().findViewById(R.id.TVVisitSensors) ;
        TVphone = getView().findViewById(R.id.EveryoneProfilePhone) ;
        iv = getView().findViewById(R.id.IVEveryoneProfile) ;
        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new RecyclerViewFragment());
                ft.commit();
            }
        });
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new HomeRecyclerFragment(email , ""));
                ft.commit();
            }
        });

        TVemail.setText(email);
        TVname.setText(name);
        TVphone.setText(phone);
        StorageReference imageRef = storageRef.child(path);
        imageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Load the image into an ImageView using Glide or Picasso
                        Glide.with(getActivity()).load(uri).into(iv);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors that occur during the download
                    }
                });
    }
}