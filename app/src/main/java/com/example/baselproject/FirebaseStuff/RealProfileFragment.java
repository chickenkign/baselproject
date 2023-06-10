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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.baselproject.Navigator.MainListFragment;
import com.example.baselproject.R;
import com.example.baselproject.Recycler.RecyclerViewFragment;
import com.example.baselproject.Useless.PrivateHomeStuffFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RealProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RealProfileFragment extends Fragment {
    private FirebaseAuth auth ;
    private FirebaseFirestore db ;
    TextView tvemail , tvname , tvphone , tvusers , tvadd , tvlogout;
    ImageView ivUser , ivAdd , ivLogOut ;
    String email,name,phone,loggedemail , image ;
    CircularImageView iv ;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RealProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RealProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RealProfileFragment newInstance(String param1, String param2) {
        RealProfileFragment fragment = new RealProfileFragment();
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
        return inflater.inflate(R.layout.fragment_real_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        auth = FirebaseAuth.getInstance() ;
        db = FirebaseFirestore.getInstance() ;
        loggedemail = auth.getCurrentUser().getEmail() ;
        tvusers = getView().findViewById(R.id.TVUsers);
        tvlogout = getView().findViewById(R.id.TVLogOut);
        tvadd = getView().findViewById(R.id.TVAdd);
        ivAdd = getView().findViewById(R.id.IVAdd);
        ivUser = getView().findViewById(R.id.IVUsers);
        ivLogOut = getView().findViewById(R.id.IVLogOut);
        tvemail = getView().findViewById(R.id.RealProfileEmail);
        tvname = getView().findViewById(R.id.RealProfileUsername);
        tvphone = getView().findViewById(R.id.RealProfilePhone);
        iv = getView().findViewById(R.id.RealProfileIV);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new RecyclerViewFragment());
                ft.commit();
            }
        });
        tvusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new RecyclerViewFragment());
                ft.commit();
            }
        });
        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new LoginFragment());
                ft.commit();

            }
        });
        ivLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new LoginFragment());
                ft.commit();

            }
        });
        tvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new PrivateHomeStuffFragment());
                ft.commit();
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new PrivateHomeStuffFragment());
                ft.commit();
            }
        });


        if(auth.getCurrentUser() !=null )
        {
            loggedemail = auth.getCurrentUser().getEmail() ;
        } else Toast.makeText(getActivity(), "no user found", Toast.LENGTH_SHORT).show();

        db.collection("users").document(loggedemail).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot != null && documentSnapshot.exists())
                    {
                        email = documentSnapshot.getString("email");
                        phone = documentSnapshot.getString("phone");
                        name = documentSnapshot.getString("username");
                        image = documentSnapshot.getString("image");
                        StorageReference imageRef = storageRef.child(image);
                        tvemail.setText(email);
                        tvname.setText(name);
                        tvphone.setText(phone);
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}