package com.example.baselproject.FirebaseStuff;

import static android.app.Activity.RESULT_OK;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.baselproject.DataXAdapters.HomeStuff;
import com.example.baselproject.DataXAdapters.User;
import com.example.baselproject.Navigator.MainListFragment;

import com.example.baselproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    EditText username, email, phone;
    Button bt;
    private FirebaseServices fbs;
    ImageView iv;
    private final int galerry = 1000;
    private static final int PICK_IMAGE_REQUEST = 1;
    private DatabaseReference mDatabase;
    Uri imageUri;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReference();

    // Specify the path to the image file


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

    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        iv = getView().findViewById(R.id.IVChooseImage);
        username = getView().findViewById(R.id.ETPusername);
        phone = getView().findViewById(R.id.etpPhone);
        email = getView().findViewById(R.id.ETPemail);
        bt = getView().findViewById(R.id.BTPSignIn);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String TheEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        fbs = FirebaseServices.getInstance();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().isEmpty() || phone.getText().toString().trim().isEmpty() || username.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "fill everything pls", Toast.LENGTH_SHORT).show();
                    return;
                }
                String path = uploadImageToFirebaseStorage();
                if (path == null)
                    return;

                User user = new User(email.getText().toString(), phone.getText().toString(), username.getText().toString(), path);
                fbs.getFire().collection("users").document(TheEmail).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        AddReadyData();
                        Toast.makeText(getActivity(), "done my friendo", Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayoutMain, new MainListFragment());
                        ft.commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "sorry but something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image's URI
            imageUri = data.getData();
            iv.setImageURI(imageUri);
            // Do something with the imageUri, such as upload it to Firebase Storage
        }
    }

    private String uploadImageToFirebaseStorage() {
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap Image = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference ref = fbs.getStorage().getReference("listingPictures/" + UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error with the picture", e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        return ref.getPath();
    }
    public void AddReadyData(){
        fbs = FirebaseServices.getInstance();
        String TheEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        HomeStuff rgb = new HomeStuff("rgb" , "click here if you want to turn on your rgb and choose any colors you like" , "/listingPictures/c8ec6edb-b052-4d83-8b46-340ea069806c") ;
        HomeStuff temp = new HomeStuff("temp reader" , "click here any time you like to check the temp of the room" , "/listingPictures/860cf501-86fb-46da-901e-4a94ffd2f3d0");
        HomeStuff lights = new HomeStuff("In home Lights" , "just click here so you can turn on / off in house Lights any time you want" , "/listingPictures/24f6753d-a6cf-4f86-b197-2420c395eee1");
        HomeStuff song = new HomeStuff("christmas song" , "click here any time you like to play the christmas song" , "/listingPictures/230af05e-db08-4b2c-9d36-b420ef66b833");
        HomeStuff door = new HomeStuff("Door" , "click here to open the door any time you want" , "/listingPictures/fbbf63c1-4982-4069-b903-93c8e8866957");
        HomeStuff reset = new HomeStuff("reset" , "visit this page if the sensors stucked" , "/listingPictures/75748a2a-4b88-4e40-b4a5-23d5216e2f0f");
        fbs.getFire().collection("users").document(TheEmail).collection("Home").document("rgb").set(rgb).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        fbs.getFire().collection("users").document(TheEmail).collection("Home").document("temp").set(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        fbs.getFire().collection("users").document(TheEmail).collection("Home").document("lights").set(lights).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        fbs.getFire().collection("users").document(TheEmail).collection("Home").document("christmas song").set(song).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        fbs.getFire().collection("users").document(TheEmail).collection("Home").document("Door").set(door).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        fbs.getFire().collection("users").document(TheEmail).collection("Home").document("reset").set(reset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}