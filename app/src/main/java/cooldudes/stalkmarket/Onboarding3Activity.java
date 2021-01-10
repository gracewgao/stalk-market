package cooldudes.stalkmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cooldudes.stalkmarket.model.Farmer;

import static cooldudes.stalkmarket.LoginActivity.user;

public class Onboarding3Activity extends AppCompatActivity {

    private ImageButton plant1Button, plant2Button, plant3Button;
    private TextView whyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_three);

        plant1Button = findViewById(R.id.plant1_button);
        plant2Button = findViewById(R.id.plant2_button);
        plant3Button = findViewById(R.id.plant3_button);
        whyButton = findViewById(R.id.why_button);

        plant1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(1);
            }
        });
        plant2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(2);
            }
        });
        plant3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(3);
            }
        });
        whyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Onboarding3Activity.this, Onboarding4Activity.class);
                Onboarding3Activity.this.startActivity(i);
            }
        });
    }

    protected void createUser(int choice) {
        final Farmer f = new Farmer(user.getUid(), user.getDisplayName());
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.child(user.getUid()).setValue(f).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // successfully saved
                Intent i = new Intent(Onboarding3Activity.this, MainActivity.class);
                Onboarding3Activity.this.startActivity(i);
                Onboarding3Activity.this.finish();
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
