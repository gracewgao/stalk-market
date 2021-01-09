package cooldudes.stalkmarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import cooldudes.stalkmarket.model.Stalk;
import cooldudes.stalkmarket.model.Transaction;
import static cooldudes.stalkmarket.MainActivity.farmer;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private static final String TAG = TransactionAdapter.class.getSimpleName();

    private static int mPoints;

    private List<Transaction> transactionList;
    public MainActivity main;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // views in card
        public TextView pointsTv, titleTv, timeTopTv, andTv;
        public ImageView iconImg;
        public LinearLayout cardBack;
        public android.widget.Button doneButton;
        public CardView pointsCard;

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            pointsTv = v.findViewById(R.id.card_points);
            titleTv = v.findViewById(R.id.card_title);
            timeTopTv = v.findViewById(R.id.card_time_top);
            andTv = v.findViewById(R.id.card_sender);
            iconImg = v.findViewById(R.id.mission_icon);
            doneButton = v.findViewById(R.id.done_button);
            cardBack = v.findViewById(R.id.cardback);
            pointsCard = v.findViewById(R.id.top_card);
        }
    }

    // constructor
    public TransactionAdapter(List<Transaction> transactions, MainActivity m) {
        transactionList = transactions;
        main = m;
    }

    // create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new card view
        View card = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_row, parent, false);
        MyViewHolder vh = new MyViewHolder(card);
        return vh;
    }

    // replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Transaction t = transactionList.get(position);
        final String stalkId = t.getsId();
        final int action = t.getAction();
        final int cost = t.getCost();

        // retrieves info from mission template
        fireRef.child("users").child(farmer.getuId()).child(stalkId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Stalk s = dataSnapshot.getValue(Stalk.class);

                holder.titleTv.setText(s.getBuyPrice());
                holder.pointsTv.setText(cost);

                // card header depending on type
                String header = "";
                switch (action){
                    case 0:
                        header = "BUY";
                        //todo: add icons?
                        break;
                    case 1:
                        header = "SELL";
                        break;
                }

                holder.timeTopTv.setText(header);

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
        return transactionList.size();
    }



}