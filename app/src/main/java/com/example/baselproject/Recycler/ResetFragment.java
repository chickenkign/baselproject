package com.example.baselproject.Recycler;

import static com.example.baselproject.BluetoothConnectors.MainActivity2.connectedThread;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.baselproject.Navigator.MainListFragment;
import com.example.baselproject.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetFragment extends Fragment {
    Button btn ;
    GifImageView iv ;
    final Handler handler = new Handler();
    ImageView back ;

    MediaPlayer mp ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetFragment newInstance(String param1, String param2) {
        ResetFragment fragment = new ResetFragment();
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
        return inflater.inflate(R.layout.fragment_reset, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        back =getView().findViewById(R.id.IVBackReset);
        btn = getView().findViewById(R.id.BTNReset);
        iv = getView().findViewById(R.id.gifReset);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goback();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rest();
            }
        });
    }
    public void Rest()
    {
        if(connectedThread !=null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv.setImageResource(R.drawable.reset);;
                }
            }, 3320);
            iv.setImageResource(R.drawable.resetbutton);
            String cmdText = null;
            cmdText = "l";
            connectedThread.write(cmdText);
            play();
        }else Toast.makeText(getActivity(), "Bluetooth not connected", Toast.LENGTH_SHORT).show();
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
    public void Goback() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new MainListFragment());
        ft.commit();
    }
}