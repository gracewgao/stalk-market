package cooldudes.stalkmarket;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cooldudes.stalkmarket.model.Stalk;
import cooldudes.stalkmarket.model.Transaction;

import static cooldudes.stalkmarket.LoginActivity.user;

public class StoreFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = StoreFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    private View view;

    private List<Stalk> stalks = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainActivity main;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stalks, null);
        main = (MainActivity) getActivity();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // recycler view set up
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(main);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new StalksAdapter(stalks, main);
        recyclerView.setAdapter(mAdapter);

        getTransactions();

        return view;
    }

    public void getTransactions() {
        swipeRefreshLayout.setRefreshing(true);
        // retrieves info from database
        DatabaseReference transactionsRef = fireRef.child("users").child(user.getUid()).child("transactions");
        transactionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // clears the list to fetch new data
                stalks.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                   Stalk s = itemSnapshot.getValue(Stalk.class);
                   StoreFragment.this.stalks.add(s);
                }

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
        getTransactions();
    }

}
