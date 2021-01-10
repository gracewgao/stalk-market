package cooldudes.stalkmarket;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import cooldudes.stalkmarket.model.StalkTemplate;

import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    // called when the AlarmReceiver reaches the scheduled time
    @Override
    public void onReceive(Context context, Intent i) {
        updatePrices();
    }

    public static void setReset(Context context){

        // intent
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // schedules to update every hour
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        long start = cal.getTimeInMillis();
        long interval = AlarmManager.INTERVAL_HOUR;

        // repeat every hour
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(
                AlarmManager.RTC,
                start,
                interval,
                pendingIntent);
    }

    public static void updatePrices(){
        final DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

        fireRef.child("templates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    // todo: update template prices
                    StalkTemplate template = s.getValue(StalkTemplate.class);
                    float price = getPrice(template.getRealStalk());
                    fireRef.child("templates").child(String.valueOf(template.gettId())).child("price").setValue(price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    // calls an API to update "stalk" prices
    private static float getPrice(String stalk){
        return 0;
    }



}
