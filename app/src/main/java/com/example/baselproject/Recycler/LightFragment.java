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
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.baselproject.Navigator.MainListFragment;
import com.example.baselproject.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LightFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LightFragment extends Fragment {
    ImageView iv ;
    private boolean swich = false ;
    GifImageView walking ;
    String cmdText = null;
    final Handler handler = new Handler();
    MediaPlayer mp ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LightFragment newInstance(String param1, String param2) {
        LightFragment fragment = new LightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LightFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        final Button btn = getView().findViewById(R.id.BTNTurnOnLight);
        iv = getView().findViewById(R.id.IVLightGoBack);
        walking = getView().findViewById(R.id.walking);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnState = btn.getText().toString().toLowerCase();
                if(connectedThread != null) {
                    switch (btnState) {
                        case "turn on":
                            play();
                            animation();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btn.setText("Turn Off");
                                    cmdText = "n";
                                    if (swich == false) {
                                        iv.setX(680);
                                        iv.setImageResource(R.drawable.on);
                                        swich = true;
                                    }
                                }
                            }, 5800);
                            break;
                        case "turn off":

                            play();
                            animation2();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btn.setText("Turn On");
                                    cmdText = "d";
                                    if (swich == true) {
                                        iv.setX(80);
                                        iv.setImageResource(R.drawable.off);
                                        walking.setImageResource(R.drawable.up);
                                        swich = false;
                                    }
                                }
                            }, 5800);
                            break;
                    }
                    // Send command to Arduino board
                    connectedThread.write(cmdText);
                } else {
                    Toast.makeText(getActivity(), "Bluetooth not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
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
    public void animation2()
    {
        walking.setImageResource(R.drawable.walking);
        TranslateAnimation animation = new TranslateAnimation(0.0f, -620.0f,
                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(6000);  // animation duration
        walking.startAnimation(animation);
        iv.startAnimation(animation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                walking.setX(80);
            }
        }, 5800);
    }
    public void animation()
    {
        walking.setImageResource(R.drawable.walking);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 620.0f,
                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(6000);  // animation duration
        walking.startAnimation(animation);  // start animation
        iv.startAnimation(animation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                walking.setImageResource(R.drawable.up);
                walking.setX(680);
            }
        }, 5800);
    }
}