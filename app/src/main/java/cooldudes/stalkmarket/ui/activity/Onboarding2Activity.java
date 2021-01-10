package cooldudes.stalkmarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import cooldudes.stalkmarket.R;

public class Onboarding2Activity extends AppCompatActivity {

    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_two);

        nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Onboarding2Activity.this, Onboarding3Activity.class);
                Onboarding2Activity.this.startActivity(i);
                Onboarding2Activity.this.finish();
            }
        });
    }


}
