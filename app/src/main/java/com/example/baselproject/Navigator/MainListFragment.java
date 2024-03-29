package com.example.baselproject.Navigator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselproject.FirebaseStuff.RealProfileFragment;
import com.example.baselproject.Recycler.HomeRecyclerFragment;
import com.example.baselproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainListFragment extends Fragment {
    BottomNavigationView bottomNavigationView ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public MainListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainListFragment newInstance(String param1, String param2) {
        MainListFragment fragment = new MainListFragment();
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
        return inflater.inflate(R.layout.fragment_main_list, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        replaceFragment(new HomeRecyclerFragment());
        bottomNavigationView = getView().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment SelectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.NavigatorMain:
                            replaceFragment(new HomeRecyclerFragment());
                            break;
                        case R.id.NavigatorHome:
                            replaceFragment(new HomeFragment());
                            break;
                        case R.id.NavigatorProfile:
                            replaceFragment(new RealProfileFragment());
                            break;
                    }
                    return true ;
                }
            } ;
    private void replaceFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutNavigator , fragment).commit() ;
    }

}