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
import cooldudes.stalkmarket.model.Stalk;
import cooldudes.stalkmarket.model.StalkTemplate;
import cooldudes.stalkmarket.model.Transaction;
import cooldudes.stalkmarket.ui.activity.MainActivity;

import static cooldudes.stalkmarket.ui.activity.LoginActivity.user;
import static cooldudes.stalkmarket.ui.activity.MainActivity.farmer;

public class TransactionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = TransactionsFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
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
        if (user != null) {
            DatabaseReference transactionsRef = fireRef.child("users").child(user.getUid()).child("balance");
            transactionsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    int balance = snapshot.getValue(Integer.class);
                    balanceButton.setText(String.valueOf(balance));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled: " + databaseError);
                }
            });
        }if (farmer != null) balanceButton.setText(String.valueOf(farmer.getBalance()));

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

    public static void tradeStalk(final boolean isBuy, final int template, final int quantity){

        final DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();
        fireRef.child("templates").child("stocks").child(String.valueOf(template)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                StalkTemplate t = snapshot.getValue(StalkTemplate.class);
                float price = t.getPrice();
                // arbitrary game cost
                final int gameCost = (int) price * quantity;

                final DatabaseReference stalkRef = fireRef.child("users").child(user.getUid()).child("inventory").child(String.valueOf(t.gettId()));
                stalkRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Stalk s = snapshot.getValue(Stalk.class);

                        int buy = 0, currQuant = 0;
                        if (s != null) {
                            buy = s.getBuyPrice();
                            currQuant = s.getQuantity();
                        }

                        if (isBuy){
                            int newBuyPrice = buy + gameCost;
                            stalkRef.child("buyPrice").setValue(newBuyPrice);
                            int newQuantity = currQuant + quantity;
                            stalkRef.child("quantity").setValue(newQuantity);
                            Transaction t = new Transaction(0, -gameCost, farmer.getBalance()-gameCost);
                            t.setsId(t.gettId());
                            addTransaction(t);
                        } else {
                            int newBuyPrice = buy - gameCost;
                            stalkRef.child("buyPrice").setValue(newBuyPrice);
                            int newQuantity = currQuant - quantity;
                            stalkRef.child("quantity").setValue(newQuantity);
                            Transaction t = new Transaction(1, gameCost, farmer.getBalance()+gameCost);
                            t.setsId(t.gettId());
                            addTransaction(t);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });
    }

    public static void addTransaction(Transaction t){

        // records transaction
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        DatabaseReference transactionsRef = userRef.child("transactions");
        String key = transactionsRef.push().getKey();
        t.settId(key);
        transactionsRef.child(key).setValue(t);

        // records cost in database
        final int cost = t.getCost();
        userRef.child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int balance = snapshot.getValue(Integer.class);
                balance += cost;
                userRef.child("balance").setValue(balance);
                farmer.setBalance(balance);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError);
            }
        });

    }

    // reloads when refreshed
    @Override
    public void onRefresh() {
        getTransactions();
    }

}
