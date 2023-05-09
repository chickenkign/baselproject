package com.example.baselproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    EditText username , password , email , phone ;
    Button bt ;
    private FirebaseServices fbs ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    public void onStart()
    {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        username = getView().findViewById(R.id.ETPusername) ;
        password = getView().findViewById(R.id.ETPPassword) ;
        phone = getView().findViewById(R.id.ETPPhone) ;
        email = getView().findViewById(R.id.ETPemail) ;
        bt = getView().findViewById(R.id.BTPSignIn) ;
        fbs = FirebaseServices.getInstance();
        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                User user = new User(email.getText().toString() , password.getText().toString() , username.getText().toString() , phone.getText().toString()) ;
                if (email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty() ||
                        username.getText().toString().trim().isEmpty() || phone.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "fill everything pls", Toast.LENGTH_SHORT).show();
                    return;
                }
                fbs.getFire().collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "done my friendo", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "sorry but something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });


            }
            });
    }
}