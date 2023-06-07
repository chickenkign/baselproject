package com.example.baselproject.Recycler;

import static com.example.baselproject.BluetoothConnectors.MainActivity2.connectedThread;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.baselproject.Navigator.MainListFragment;
import com.example.baselproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RgbFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RgbFragment extends Fragment {
    ImageView iv1,iv2 ;
    MediaPlayer mp ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RgbFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RgbFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RgbFragment newInstance(String param1, String param2) {
        RgbFragment fragment = new RgbFragment();
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
        return inflater.inflate(R.layout.fragment_rgb, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        final ImageView red,blue,green ;
        red = getView().findViewById(R.id.IVRedLight);
        blue = getView().findViewById(R.id.IVBlueLight);
        green = getView().findViewById(R.id.IVGreenlight);
        iv1 = getView().findViewById(R.id.IVGoBack1);
        iv2 = getView().findViewById(R.id.IVGoBack2);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Red();
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Blue();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Green();
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goback();
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goback();
            }
        });
    }
    public void Red()
    {
        if(connectedThread !=null) {
            String cmdText = null;
            cmdText = "r";
            connectedThread.write(cmdText);
            play();
        }else Toast.makeText(getActivity(), "Bluetooth not connected", Toast.LENGTH_SHORT).show();
    }
    public void Green()
    {
        if(connectedThread !=null) {
            String cmdText = null;
            cmdText = "g";
            connectedThread.write(cmdText);
            play();
        }else Toast.makeText(getActivity(), "Bluetooth not connected", Toast.LENGTH_SHORT).show();
    }
    public void Blue()
    {
        if(connectedThread !=null) {
            String cmdText = null;
            cmdText = "b";
            connectedThread.write(cmdText);
            play();
        }else Toast.makeText(getActivity(), "Bluetooth not connected", Toast.LENGTH_SHORT).show();
    }
    public void Goback() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new MainListFragment());
        ft.commit();
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
}