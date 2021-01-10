package cooldudes.stalkmarket.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import cooldudes.stalkmarket.R;
import cooldudes.stalkmarket.ui.activity.MainActivity;

import static cooldudes.stalkmarket.ui.activity.MainActivity.farmer;

public class GardenFragment extends Fragment {

    private static final String TAG = BankFragment.class.getSimpleName();

    private View view;
    private TextView balanceButton;

    private MainActivity main;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_garden, null);

        main = (MainActivity) getActivity();

        balanceButton = view.findViewById(R.id.balance);
        if (farmer != null) balanceButton.setText(String.valueOf(farmer.getBalance()));

        return view;
    }

}
