package com.example.baselproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    EditText email1 , code ;
    Button login ;
    private FirebaseServices fbs;
    TextView forgotpassword , TVsignUp ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment login.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
    private void connectcomponents() {
        TVsignUp = getView().findViewById(R.id.TVGTSignUp);
        fbs = FirebaseServices.getInstance();
        email1 = getView().findViewById(R.id.ETPemail);
        code = getView().findViewById(R.id.ETPPassword);
        login = getView().findViewById(R.id.BTPSignIn);
        forgotpassword = getView().findViewById(R.id.ForgotPasswordLogin);
        Button button = getView().findViewById(R.id.BTPSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email1.getText().toString();
                String password = code.getText().toString();
                if (email.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "fill everything pls", Toast.LENGTH_SHORT).show();
                    return;
                }
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

// onClick of button perform this simplest code.
                if (!email.matches(emailPattern)) {
                Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                 return;
            }
                if (!isValidPassword(password)) {
                    Toast.makeText(getActivity(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                fbs.getAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser G = FirebaseAuth.getInstance().getCurrentUser();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "welcome to the new world", Toast.LENGTH_SHORT).show();
                                    gotothemainplace();
                                } else {
                                    Toast.makeText(getActivity(), "Wrong email or password my guy", Toast.LENGTH_SHORT).show();
                                }
                                return;
                            }

                        });


            }
        });

        TVsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new SignUpFragment());
                ft.commit();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new ForgotPassword());
                ft.commit();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            gotothemainplace();
        }
    }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {

                View view = inflater.inflate(R.layout.fragment_login,
                        container, false);
                return view;
            }

            @Override
            public void onStart() {
                super.onStart();
                connectcomponents();
            }


            public static boolean isValidPassword(final String password) {

                Pattern pattern;
                Matcher matcher;
                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                pattern = Pattern.compile(PASSWORD_PATTERN);
                matcher = pattern.matcher(password);

                return matcher.matches();
            }
            public void gotothemainplace()
            {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new MainListFragment());
                ft.commit();
            }
        }