package cooldudes.stalkmarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cooldudes.stalkmarket.R;

public class Onboarding1Activity extends AppCompatActivity {

    private ImageButton nextButton;
    private TextView funFactTv;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_one);

        funFactTv = findViewById(R.id.insert_fun_fact);

        // todo: make random number
        int num = 1;

        // finds user's stocks
        fireRef.child("facts").child(String.valueOf(num)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fact = dataSnapshot.getValue(String.class);
                funFactTv.setText(fact);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Onboarding1Activity.this, Onboarding2Activity.class);
                Onboarding1Activity.this.startActivity(i);
                Onboarding1Activity.this.finish();
            }
        });
    }

}
