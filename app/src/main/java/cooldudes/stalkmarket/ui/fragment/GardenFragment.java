package cooldudes.stalkmarket.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Collections;

import cooldudes.stalkmarket.R;
import cooldudes.stalkmarket.ui.activity.MainActivity;
import cooldudes.stalkmarket.model.Transaction;

import static cooldudes.stalkmarket.ui.activity.LoginActivity.user;
import static cooldudes.stalkmarket.ui.activity.MainActivity.farmer;
import static cooldudes.stalkmarket.ui.fragment.TransactionsFragment.addTransaction;

public class GardenFragment extends Fragment {

    private static final String TAG = BankFragment.class.getSimpleName();

    private View view;
    private TextView balanceButton;
    private LinearLayout waterPlant;

    private MainActivity main;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_garden, null);

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
        }

        waterPlant = view.findViewById(R.id.water_button);
        waterPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterPlants();
            }
        });

        return view;
    }

    public static void waterPlants(){
        Transaction t = new Transaction(3, -50, farmer.getBalance());
        addTransaction(t);
    }

}
