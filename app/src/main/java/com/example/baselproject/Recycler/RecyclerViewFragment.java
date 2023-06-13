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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.baselproject.DataXAdapters.MyAdapter;
import com.example.baselproject.DataXAdapters.SelectListener;
import com.example.baselproject.DataXAdapters.User;
import com.example.baselproject.FirebaseStuff.EveryoneProfileFragment;
import com.example.baselproject.Navigator.MainListFragment;
import com.example.baselproject.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment implements SelectListener {
    RecyclerView recyclerView ;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter ;
    FirebaseFirestore db;
    EditText Username ;
    Button search ;
    String email , name , path , phone ;
    ImageView iv ;
    static int PERMISSION_CODE=100;
    ProgressDialog progressDialog ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerViewFragment newInstance(String param1, String param2) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
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
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
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
        recyclerView = getView().findViewById(R.id.rvMain) ;
        Username = getView().findViewById(R.id.ETUSERNAME) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseFirestore.getInstance() ;
        userArrayList = new ArrayList<User>();
        myAdapter = new MyAdapter(getActivity(),userArrayList , this);
        iv = getView().findViewById(R.id.IVGOBACKRecycler);
        search = getView().findViewById(R.id.BTNSearch);
        recyclerView.setAdapter(myAdapter);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new MainListFragment());
                ft.commit();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                For();
            }
        });

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("users")
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

                    for (DocumentChange dc : value.getDocumentChanges()) {


                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                userArrayList.add(dc.getDocument().toObject(User.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                    }


            }
        });
    }
    public void For()
    {
        String m = Username.getText().toString() ;
        int i ;
        for (i = 0 ; i<userArrayList.size() ; i++)
        {
            String f = userArrayList.get(i).getUsername() ;
            if(m.equals(f))
            {
                email = userArrayList.get(i).getEmail() ;
                name = userArrayList.get(i).getUsername() ;
                phone = userArrayList.get(i).getPhone() ;
                path = userArrayList.get(i).getImage() ;
                Toast.makeText(getActivity(), f, Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, new EveryoneProfileFragment(email , name , phone , path));
                ft.commit();
            }
        }
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getActivity(), userArrayList.get(position).getUsername(), Toast.LENGTH_SHORT).show();
    }
}