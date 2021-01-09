package cooldudes.stalkmarket;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cooldudes.stalkmarket.model.Mission;

import static cooldudes.stalkmarket.LoginActivity.famId;
import static cooldudes.stalkmarket.MainActivity.member;

public class MissionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MissionsFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    private TextView msgView;
    private View view;

    private List<Mission> missions = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private final int REQUEST_ACCESS_FINE_LOCATION=1;
    private FusedLocationProviderClient fusedLocationClient;
    private MainActivity main;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_missions, null);

        main = (MainActivity) getActivity();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // recycler view set up
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(main);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ItemAdapter(missions, main);
        recyclerView.setAdapter(mAdapter);

        if (famId!=null){
            getMissions();
        }

        return view;
    }

    public void getMissions() {
        swipeRefreshLayout.setRefreshing(true);
        // retrieves info from database
        DatabaseReference missionsRef = fireRef.child("families").child(famId).child("missions");
        missionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // clears the list to fetch new data
                missions.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Mission m = itemSnapshot.getValue(Mission.class);
                    // only displays if the mission is yours and active
                   if (m.getMember().equals(member.getuId()) && m.isActive()) {
                        MissionsFragment.this.missions.add(m);
                    }
                }

                // sorts missions based on time, status
                 Collections.sort(missions);

                // refreshes recycler view
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }

    // reloads when refreshed
    @Override
    public void onRefresh() {
        getMissions();
    }

}
