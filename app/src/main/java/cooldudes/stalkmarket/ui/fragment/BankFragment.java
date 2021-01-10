package cooldudes.stalkmarket.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cooldudes.stalkmarket.R;
import cooldudes.stalkmarket.ui.activity.MainActivity;

public class BankFragment extends Fragment {

    private static final String TAG = BankFragment.class.getSimpleName();

    private View view;

    private MainActivity main;

    // Firebase
    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bank, null);

        main = (MainActivity) getActivity();

        return view;
    }

}
