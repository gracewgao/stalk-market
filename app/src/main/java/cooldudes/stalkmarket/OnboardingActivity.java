package cooldudes.stalkmarket;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cooldudes.stalkmarket.model.Farmer;

import static cooldudes.stalkmarket.LoginActivity.user;

public class OnboardingActivity extends AppCompatActivity {

    private Button joinButton, createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        joinButton = findViewById(R.id.button_join);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    protected void createUser() {
        final Farmer f = new Farmer(user.getUid(), user.getDisplayName());
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.child(user.getUid()).setValue(f).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // successfully saved
                Intent i = new Intent(OnboardingActivity.this, MainActivity.class);
                OnboardingActivity.this.startActivity(i);
                OnboardingActivity.this.finish();
                MainActivity.farmer = f;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // failed to save
            }
        });
    }


}
