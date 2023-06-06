package com.example.baselproject;

import static com.example.baselproject.MainActivity2.connectedThread;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecretSantaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecretSantaFragment extends Fragment {
    ImageView iv ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecretSantaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecretSantaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecretSantaFragment newInstance(String param1, String param2) {
        SecretSantaFragment fragment = new SecretSantaFragment();
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
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connentcomponents();
    }

    private void connentcomponents() {
        final ImageView tempReader , SantaTree1 , SantaTree2 , SantaTree3 , SantaTree4 ;
        iv = getView().findViewById(R.id.IVGoBackFromSanta);
        SantaTree1 = getView().findViewById(R.id.SantaTree1);
        SantaTree2 = getView().findViewById(R.id.SantaTree2);
        SantaTree3 = getView().findViewById(R.id.SantaTree3);
        SantaTree4 = getView().findViewById(R.id.SantaTree4);
        tempReader = getView().findViewById(R.id.IVTempReader);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new LivingRoomFragment());
                ft.commit();
            }
        });
        SantaTree1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {PlayChristmas();}}); //play christmas music
        SantaTree2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {PlayChristmas();}}); //play christmas music
        SantaTree3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {PlayChristmas();}}); //play christmas music
        SantaTree4.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {PlayChristmas();}}); //play christmas music
        tempReader.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {Temp();}}); //temp
    }
    public void PlayChristmas()
    {
        String cmdText = null;
        cmdText = "m";
        connectedThread.write(cmdText);
    }

    public void Temp(){
        String cmdText = null;
        cmdText = "t";
        connectedThread.write(cmdText);
    }
}