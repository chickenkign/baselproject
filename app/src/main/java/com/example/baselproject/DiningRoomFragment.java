package com.example.baselproject;

import static com.example.baselproject.MainActivity2.connectedThread;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiningRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiningRoomFragment extends Fragment {
    ImageView door ;
    Boolean b = true ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DiningRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiningRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiningRoomFragment newInstance(String param1, String param2) {
        DiningRoomFragment fragment = new DiningRoomFragment();
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
        return inflater.inflate(R.layout.fragment_dining_room, container, false);

    }
    @Override
    public void onStart() {
        super.onStart();
        connenctcomponents();
    }

    private void connenctcomponents() {
        door = getView().findViewById(R.id.IVDinningRoomDoor);
        final ImageView iv = getView().findViewById(R.id.IVDinningRoomLamp3);
        final ImageView iv1 = getView().findViewById(R.id.IVDinningRoomLamp2);
        final ImageView iv2 = getView().findViewById(R.id.IVDinningRoomLamp1);
        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goback();
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                if (b == true) {
                    b = false;
                    cmdText = "a";
                } else
                {
                    b = true;
                    // Command to turn off LED on Arduino. Must match with the command in Arduino code
                    cmdText = "b";
                    //cmdText = "<turn off>";
                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                if (b == true) {
                    b = false;
                    cmdText = "a";
                } else
                {
                    b = true;
                    // Command to turn off LED on Arduino. Must match with the command in Arduino code
                    cmdText = "b";
                    //cmdText = "<turn off>";
                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmdText = null;
                if (b == true) {
                    b = false;
                    cmdText = "a";
                } else
                {
                    b = true;
                    // Command to turn off LED on Arduino. Must match with the command in Arduino code
                    cmdText = "b";
                    //cmdText = "<turn off>";
                }
                // Send command to Arduino board
                connectedThread.write(cmdText);
            }
        });

    }

    public void goback()
    {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new InHomeFragment());
        ft.commit();
    }
    public void Message()
    {
        Toast.makeText(getActivity(), "Bluetooth still not wroking so wait for now", Toast.LENGTH_SHORT).show();
    }

}