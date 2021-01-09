package cooldudes.stalkmarket;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cooldudes.stalkmarket.model.Farmer;
import cooldudes.stalkmarket.model.Member;
import cooldudes.stalkmarket.model.Mission;

import static cooldudes.stalkmarket.LoginActivity.user;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    final static String TAG = MainActivity.class.getSimpleName();

    // firebase
    public static DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public static Farmer farmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets up nav bar and fragments
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        final MissionsFragment gardenFragment = new MissionsFragment();
        loadFragment(gardenFragment);

        // finds user's stocks
        fireRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                farmer = dataSnapshot.getValue(Farmer.class);
                gardenFragment.getMissions();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // updates stock prices every hour
        AlarmReceiver.setReset(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch(menuItem.getItemId()) {
            case R.id.navigation_missions:
                fragment = new MissionsFragment();
                break;
            case R.id.navigation_family:
                fragment = new FamilyFragment();
                break;
            case R.id.navigation_leaderboard:
                fragment = new TransactionsFragment();
                break;
        }
        return loadFragment(fragment);
    }

    // set up listener for when data changed --> sends relevant notifications to user
    public void setUpNotifs(){

    }

}