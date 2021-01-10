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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cooldudes.stalkmarket.R;
import cooldudes.stalkmarket.ui.activity.MainActivity;

import static cooldudes.stalkmarket.ui.activity.LoginActivity.user;
import static cooldudes.stalkmarket.ui.activity.MainActivity.farmer;

public class BankFragment extends Fragment {

    private static final String TAG = BankFragment.class.getSimpleName();

    private View view;
    private Button balanceButton;

    private MainActivity main;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bank, null);
        main = (MainActivity) getActivity();

        balanceButton = view.findViewById(R.id.balance);
        if (user != null) {
            DatabaseReference transactionsRef = fireRef.child("users").child(user.getUid()).child("balance");
            transactionsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    int balance = (int) snapshot.getValue();
                    balanceButton.setText(String.valueOf(balance));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled: " + databaseError);
                }
            });
        }

        return view;
    }

}
