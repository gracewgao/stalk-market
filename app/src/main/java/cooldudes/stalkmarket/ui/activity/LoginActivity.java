package cooldudes.stalkmarket.ui.activity;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import cooldudes.stalkmarket.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public static FirebaseUser user;
    public static String famId;

    public String mUsername;
    private static int RC_SIGN_IN = 1;
    private static String ANONYMOUS = null;

    public static DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null){
            onSignedInInitialize(user);
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.FirebaseLogin)
                            .setIsSmartLockEnabled(false)
                            .setLogo(R.drawable.logo)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build()))
                            .setTosAndPrivacyPolicyUrls(
                                    "https://devpost.com/software/stalkmarket",
                                    "https://devpost.com/software/stalkmarket")
                            .build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                user = mAuth.getCurrentUser();
                onSignedInInitialize(user);
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Please sign in again", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // Signs in the user
    private void onSignedInInitialize(final FirebaseUser user){

        FirebaseUserMetadata metadata = user.getMetadata();
        if (Math.abs(metadata.getCreationTimestamp() - metadata.getLastSignInTimestamp()) < 100) {
            // creates new user
            Intent i = new Intent(this, Onboarding1Activity.class);
            this.startActivity(i);
            this.finish();
            Toast.makeText(getApplicationContext(), "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();

        } else {
            // Existing user
            Intent i = new Intent(this, MainActivity.class);
            this.startActivity(i);
            this.finish();
            Toast.makeText(getApplicationContext(), "Signed in as " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }

    }

    public static void signOut(final Activity c){
        AuthUI.getInstance()
                .signOut(c)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(c, LoginActivity.class);
                        c.startActivity(i);
                        c.finish();
                    }
                });
        user = null;
    }
}