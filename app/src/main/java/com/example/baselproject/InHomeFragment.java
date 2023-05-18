package com.example.baselproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InHomeFragment extends Fragment {
    Button btnsoon , TheButton , LivingRoom , UpStairs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InHomeFragment newInstance(String param1, String param2) {
        InHomeFragment fragment = new InHomeFragment();
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
        return inflater.inflate(R.layout.fragment_in_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connenctcomponents();
    }

    private void connenctcomponents() {
        btnsoon = getView().findViewById(R.id.BTNInHomeSoon);
        TheButton = getView().findViewById(R.id.BTNDontClickIt);
        LivingRoom = getView().findViewById(R.id.BTNLivingRoom);
        btnsoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        TheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Dont touch here :3", Toast.LENGTH_SHORT).show();
            }
        });
        LivingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "welcome to your living Room", Toast.LENGTH_SHORT).show();
            }
        });
    }
}