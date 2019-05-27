package usama.utech.firebasepractice.ProfilePageStuff;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import usama.utech.firebasepractice.Adatpters.PostAdapter;
import usama.utech.firebasepractice.ModelClasses.PostDriver;
import usama.utech.firebasepractice.ModelClasses.PostRider;
import usama.utech.firebasepractice.R;

import static android.content.Context.MODE_PRIVATE;


public class MySentRequests extends Fragment {



    private MyAddedPosts.OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    String currentUserUid;
    private DatabaseReference myRef;
    PostAdapter postAdapter;
    ArrayList<PostDriver> postDriverArrayList = new ArrayList<>();
    private ArrayList<PostRider> listrider = new ArrayList<>();
    private String type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_sent_requests, container, false);


        SharedPreferences prefs = getActivity().getSharedPreferences("saveddata", MODE_PRIVATE);
        currentUserUid = prefs.getString("uid", "");
        type = prefs.getString("type", "");

        recyclerView = view.findViewById(R.id.rec_my_requests);

        if (type.equals("driver")) {
            myRef = FirebaseDatabase.getInstance().getReference("PostsAsDriver");

            postAdapter = new PostAdapter(getActivity(), postDriverArrayList, listrider, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(postAdapter);

            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    //to avoid nuul exception
                    listrider.add(new PostRider("", "", "", "", "", "", "", "", "", "", "", "", "", ""));


                    PostDriver value = dataSnapshot.getValue(PostDriver.class);

                    if (currentUserUid.equals(value.getUid())) {
                        postDriverArrayList.add(value);
                        postAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (type.equals("rider")) {

            myRef = FirebaseDatabase.getInstance().getReference("PostsAsPassenger");

            postAdapter = new PostAdapter(getActivity(), postDriverArrayList, listrider, false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(postAdapter);

            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //to avoid nuul exception
                    postDriverArrayList.add(new PostDriver("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));


                    PostRider value = dataSnapshot.getValue(PostRider.class);

                    if (currentUserUid.equals(value.getUid())) {
                        listrider.add(value);
                        postAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



        return view;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
