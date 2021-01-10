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
import cooldudes.stalkmarket.model.Transaction;
import cooldudes.stalkmarket.ui.activity.MainActivity;

import static cooldudes.stalkmarket.ui.activity.LoginActivity.user;
import static cooldudes.stalkmarket.ui.activity.MainActivity.farmer;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private static final String TAG = TransactionAdapter.class.getSimpleName();

    private List<Transaction> transactionList;
    public MainActivity main;

    DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // views in card
        public TextView amtTv, nameTv, balanceTv;
        public ImageView iconImg;

        public MyViewHolder(View v) {
            super(v);
            amtTv = v.findViewById(R.id.amt);
            nameTv = v.findViewById(R.id.itemname);
            balanceTv = v.findViewById(R.id.balance);
            iconImg = v.findViewById(R.id.transaction_image);
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
                .inflate(R.layout.transaction_card, parent, false);
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
        final int balance = t.getBalance();

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
            case 2:
                header = "WATER";
                break;
        }

        holder.nameTv.setText(header);
        holder.amtTv.setText('$' + String.valueOf(cost));
        holder.balanceTv.setText('$' + String.valueOf(balance));

    }

    // returns size of list
    @Override
    public int getItemCount() {
        return transactionList.size();
    }


}