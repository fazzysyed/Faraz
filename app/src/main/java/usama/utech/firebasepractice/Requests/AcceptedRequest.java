package usama.utech.firebasepractice.Requests;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import usama.utech.firebasepractice.Adatpters.PostAdapter;
import usama.utech.firebasepractice.ModelClasses.PostDriver;
import usama.utech.firebasepractice.ModelClasses.PostRider;
import usama.utech.firebasepractice.ModelClasses.RequestsModel;
import usama.utech.firebasepractice.ProfilePageStuff.MyAddedPosts;
import usama.utech.firebasepractice.R;

import static android.content.Context.MODE_PRIVATE;


public class AcceptedRequest extends Fragment {


    String currentUserUid;
    PostAdapter postAdapter;
    ArrayList<PostDriver> postDriverArrayList = new ArrayList<>();
    private MyAddedPosts.OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private DatabaseReference myRef;
    private ArrayList<PostRider> listrider = new ArrayList<>();
    private String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accepted_request, container, false);


        SharedPreferences prefs = getActivity().getSharedPreferences("saveddata", MODE_PRIVATE);
        currentUserUid = prefs.getString("uid", "");
        type = prefs.getString("type", "");

        recyclerView = view.findViewById(R.id.rec_acceptedreq_posts);


        if (type.equals("driver")) {
            myRef = FirebaseDatabase.getInstance().getReference("Accepted Requests").child(currentUserUid);

            postAdapter = new PostAdapter(true, getActivity(), postDriverArrayList, listrider, "not");
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(postAdapter);

            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnap, @Nullable String s) {

                    for (DataSnapshot ds : dataSnap.getChildren()) {

                        RequestsModel mode = ds.getValue(RequestsModel.class);

                        //to avoid nuul exception
                        postDriverArrayList.add(new PostDriver("", mode.getPostid(), "", mode.getEndpoint(), mode.getSendername(), mode.getId(), "", "", "", "", mode.getReciverid(), "", mode.getImgurl(), "", "", mode.getStartpoint(), mode.getSenderid(), ""));


                        listrider.add(new PostRider("", "", "", "", "", "", "", "", "", "", "", "", "", ""));

                        postAdapter.notifyDataSetChanged();
                        System.err.println("driver oput is " + mode.getSendername());

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

            myRef = FirebaseDatabase.getInstance().getReference("Accepted Requests").child(currentUserUid);

            postAdapter = new PostAdapter(false, getActivity(), postDriverArrayList, listrider, "not");
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(postAdapter);


            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    System.err.println("mydata" + dataSnapshot.getKey());
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        //to avoid nuul exception
                        postDriverArrayList.add(new PostDriver("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));


                        RequestsModel mode = ds.getValue(RequestsModel.class);

                        System.err.println("mydata" + dataSnapshot.getValue());

                        listrider.add(new PostRider("", "", mode.getEndpoint(), mode.getSendername(), mode.getId(), "", "", "", "", mode.getImgurl(), "", "", mode.getStartpoint(), mode.getSenderid()));


                        postAdapter.notifyDataSetChanged();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//            myRef.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                        //to avoid nuul exception
//                        postDriverArrayList.add(new PostDriver("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
//
//
//                        RequestsModel mode = ds.getValue(RequestsModel.class);
//
//                        System.err.println("mydata" + mode.getStartpoint());
//
//                        listrider.add(new PostRider("", "", mode.getEndpoint(), mode.getSendername(), mode.getId(), "", "", "", "", mode.getImgurl(), "", "", mode.getStartpoint(), mode.getSenderid()));
//
//
//                        postAdapter.notifyDataSetChanged();
//
//                    }
//
//
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        }


        return view;


    }


}
