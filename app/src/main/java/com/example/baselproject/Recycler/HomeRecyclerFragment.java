package com.example.baselproject.Recycler;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baselproject.DataXAdapters.HomeAdapter;
import com.example.baselproject.DataXAdapters.HomeStuff;
import com.example.baselproject.DataXAdapters.SelectListener;
import com.example.baselproject.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeRecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeRecyclerFragment extends Fragment implements SelectListener {
    RecyclerView recyclerView ;
    ArrayList<HomeStuff> userArrayList;
    HomeAdapter myAdapter ;
    FirebaseFirestore db;

    ProgressDialog progressDialog ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeRecyclerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeRecyclerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeRecyclerFragment newInstance(String param1, String param2) {
        HomeRecyclerFragment fragment = new HomeRecyclerFragment();
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
        return inflater.inflate(R.layout.fragment_home_recycler, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        connentcomponents();
    }

    private void connentcomponents() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        recyclerView = getView().findViewById(R.id.HomeRecycler) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseFirestore.getInstance() ;
        userArrayList = new ArrayList<HomeStuff>();
        myAdapter = new HomeAdapter(getActivity(),userArrayList , this);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("Home")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null)
                        {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Firestore Error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED)
                            {
                                userArrayList.add(dc.getDocument().toObject(HomeStuff.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });
    }

    @Override
    public void onItemClicked(int position) {
        if (position == 0)
        {
            Toast.makeText(getActivity(), "welcome to lights fragment", Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayoutMain, new LightFragment());
            ft.commit();
        }
        if (position == 1)
        {
            Toast.makeText(getActivity(), "welcome to rgb fragment", Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayoutMain, new RgbFragment());
            ft.commit();
        }
        if (position == 2)
        {
            Toast.makeText(getActivity(), "welcome to Temp Reader fragment", Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayoutMain, new TempFragment());
            ft.commit();
        }
        if (position == 3)
        {
            Toast.makeText(getActivity(), "welcome to Christmas Tree fragment", Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayoutMain, new christmasTreeFragment());
            ft.commit();
        }
        if (position == 4)
        {
            Toast.makeText(getActivity(), "welcome to Door fragment", Toast.LENGTH_SHORT).show();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayoutMain, new DoorFragment());
            ft.commit();
        }
    }
}