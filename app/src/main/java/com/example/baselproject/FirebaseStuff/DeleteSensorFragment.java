package com.example.baselproject.FirebaseStuff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.baselproject.Navigator.MainListFragment;
import com.example.baselproject.R;
import com.example.baselproject.Recycler.RecyclerViewFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteSensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteSensorFragment extends Fragment {
    Button btn ;
    FirebaseFirestore db;
    private FirebaseAuth auth ;
    String loggedemail;
    ImageView iv ;
    EditText et ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteSensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteSensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteSensorFragment newInstance(String param1, String param2) {
        DeleteSensorFragment fragment = new DeleteSensorFragment();
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
        return inflater.inflate(R.layout.fragment_delete_sensor, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        btn = getView().findViewById(R.id.BTNDelete);
        db = FirebaseFirestore.getInstance() ;
        auth = FirebaseAuth.getInstance() ;
        loggedemail = auth.getCurrentUser().getEmail() ;
        et = getView().findViewById(R.id.ETSensorName);
        iv = getView().findViewById(R.id.IVGoBackRn);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new MainListFragment());
                ft.commit();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").document(loggedemail).collection("Home").document(et.getText().toString()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Sensor has been deleted.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to delete the sensor. ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}