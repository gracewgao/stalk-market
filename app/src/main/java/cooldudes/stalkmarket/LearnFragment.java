package cooldudes.stalkmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LearnFragment extends Fragment {

    private static final String TAG = BankFragment.class.getSimpleName();

    private View view;

    private MainActivity main;
    private ImageButton diversityButton, supplyButton, compoundButton;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_learn, null);

        main = (MainActivity) getActivity();

        diversityButton = view.findViewById(R.id.diversify_button);
        supplyButton = view.findViewById(R.id.supply_button);
        compoundButton = view.findViewById(R.id.compound_button);

        diversityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main, DiversifyActivity.class);
                main.startActivity(i);
            }
        });
        supplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main, SupplyActivity.class);
                main.startActivity(i);
            }
        });
        compoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main, CompoundActivity.class);
                main.startActivity(i);
            }
        });

        return view;
    }

}
