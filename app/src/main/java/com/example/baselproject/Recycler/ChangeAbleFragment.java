package com.example.baselproject.Recycler;

import static com.example.baselproject.BluetoothConnectors.MainActivity2.connectedThread;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.baselproject.Navigator.MainListFragment;
import com.example.baselproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeAbleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeAbleFragment extends Fragment {
    private FirebaseAuth auth ;
    private FirebaseFirestore db ;
    String name , sensor , image;
    ImageView iv , GoBack;
    Button btn  ;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    MediaPlayer mp ;
    StorageReference storageRef = storage.getReference();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String input , path , output ;

    public ChangeAbleFragment(String input) {
        this.input = input ;
    }
    public ChangeAbleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeAbleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeAbleFragment newInstance(String param1, String param2) {
        ChangeAbleFragment fragment = new ChangeAbleFragment();
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
        return inflater.inflate(R.layout.fragment_change_able, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        auth = FirebaseAuth.getInstance() ;
        db = FirebaseFirestore.getInstance() ;
        iv = getView().findViewById(R.id.IVChangeable);
        GoBack = getView().findViewById(R.id.IVGoBackChangeAble);
        btn = getView().findViewById(R.id.BTNChangeAble);
            db.collection("ItemChar").document(input).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            sensor = documentSnapshot.getString("sensor");
                            image = documentSnapshot.getString("iv");
                            StorageReference imageRef = storageRef.child(image);
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
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sensor.equals(""))
                {
                    play2();
                    return;
                }
                else if(connectedThread !=null) {
                    String cmdText = null;
                    cmdText = sensor;
                    connectedThread.write(cmdText);
                    play();
                }else {
                    Toast.makeText(getActivity(), "Bluetooth not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new MainListFragment());
                ft.commit();
            }
        });
    }
    public void play(){
        if(mp == null){
            mp = MediaPlayer.create(getActivity() , R.raw.click);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopplay();
                }
            });
        }
        mp.start();
    }
    public void stopplay() {
        if (mp != null){
            mp.release();
            mp = null ;
        }
    }
    public void play2(){
        if(connectedThread !=null) {
            String m = connectedThread.read();
            Toast.makeText(getActivity(), m , Toast.LENGTH_SHORT).show();
        }else Toast.makeText(getActivity(), output, Toast.LENGTH_SHORT).show();
    }
}