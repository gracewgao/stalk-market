package cooldudes.stalkmarket.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import cooldudes.stalkmarket.R;
import cooldudes.stalkmarket.helper.TransactionAdapter;
import cooldudes.stalkmarket.model.Transaction;
import cooldudes.stalkmarket.ui.activity.MainActivity;

import static cooldudes.stalkmarket.ui.activity.LoginActivity.user;
import static cooldudes.stalkmarket.ui.activity.MainActivity.farmer;

public class TransactionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = TransactionsFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    private TextView msgView;
    private View view;

    private List<Transaction> transactions = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainActivity main;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();
    private Button balanceButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transactions, null);
        main = (MainActivity) getActivity();

        balanceButton = view.findViewById(R.id.balance);
        if (farmer != null) balanceButton.setText(String.valueOf(farmer.getBalance()));

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // recycler view set up
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(main);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TransactionAdapter(transactions, main);
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
                transactions.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                   Transaction t = itemSnapshot.getValue(Transaction.class);
                   TransactionsFragment.this.transactions.add(t);
                }

                // sorts based on time
                 Collections.sort(transactions);

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
