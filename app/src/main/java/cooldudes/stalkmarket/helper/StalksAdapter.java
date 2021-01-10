package cooldudes.stalkmarket.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import cooldudes.stalkmarket.R;
import cooldudes.stalkmarket.model.Stalk;
import cooldudes.stalkmarket.ui.activity.MainActivity;

import static cooldudes.stalkmarket.ui.activity.MainActivity.farmer;


public class StalksAdapter extends RecyclerView.Adapter<StalksAdapter.MyViewHolder> {
    private static final String TAG = StalksAdapter.class.getSimpleName();

    private List<Stalk> stalkList;
    public MainActivity main;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // views in card
        public TextView titleTv, priceTv, balanceTv;
        public ImageView iconImg;

        public MyViewHolder(View v) {
            super(v);
            titleTv = v.findViewById(R.id.card_title);
            priceTv = v.findViewById(R.id.card_price);
        }
    }

    // constructor
    public StalksAdapter(List<Stalk> stalks, MainActivity m) {
        stalkList = stalks;
        main = m;
    }

    // create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new card view
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stalk_card, parent, false);
        MyViewHolder vh = new MyViewHolder(card);
        return vh;
    }

    // replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Stalk s = stalkList.get(position);
        final String stalkId = s.getsId();

        // retrieves info from mission template
        fireRef.child("users").child(farmer.getuId()).child(stalkId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Stalk s = dataSnapshot.getValue(Stalk.class);

                holder.priceTv.setText(s.getCurrentPrice());
                // todo: change later
                holder.titleTv.setText("placeholder");

//                holder.amtTv.setText(cost);
//
//                // card header depending on type
//                String header = "";
//                switch (action){
//                    case 0:
//                        header = "BUY";
//                        //todo: add icons?
//                        break;
//                    case 1:
//                        header = "SELL";
//                        break;
//                }
//
//                holder.balanceTv.setText(header);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(main.getApplicationContext(), "Oops! Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    // returns size of list
    @Override
    public int getItemCount() {
        return stalkList.size();
    }


}