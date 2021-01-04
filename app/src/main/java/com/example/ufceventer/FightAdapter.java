package com.example.ufceventer;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ufceventer.db.DatabaseClient;
import com.example.ufceventer.models.PickAndScore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FightAdapter extends RecyclerView.Adapter<FightAdapter.FightViewHolder> {

    private List<FightListItem> fightListItems;
    private Context context;

    public FightAdapter(List<FightListItem> fightListItems, Context context) {
        this.fightListItems = fightListItems;
        this.context = context;
    }

    @Override
    public FightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventItem = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_fight_item, parent, false);
        return new FightViewHolder(eventItem);
    }

    @Override
    public void onBindViewHolder(FightViewHolder holder, int position) {
        FightListItem fightListItem = fightListItems.get(position);

        holder.textViewFighter1Name.setText(fightListItem.getFighter1Name());
        holder.textViewFighter1Record.setText(fightListItem.getFighter1Record());
        Picasso.get().load(fightListItem.getFighter1Pic()).into(holder.imageViewFighter1Pic);
        holder.textViewFighter2Name.setText(fightListItem.getFighter2Name());
        holder.textViewFighter2Record.setText(fightListItem.getFighter2Record());
        Picasso.get().load(fightListItem.getFighter2Pic()).into(holder.imageViewFighter2Pic);
        holder.constraintLayoutFightItem.setOnClickListener(v -> {
            Intent pickAndScore;
            pickAndScore = new Intent(context, PickAndScoreActivity.class);

            pickAndScore.putExtra("isMain", fightListItem.isMainEvent());
            pickAndScore.putExtra("fighter1Name", fightListItem.getFighter1Name());
            pickAndScore.putExtra("fighter1Record", fightListItem.getFighter1Record());
            pickAndScore.putExtra("fighter1Pic", fightListItem.getFighter1Pic());
            pickAndScore.putExtra("fighter2Name", fightListItem.getFighter2Name());
            pickAndScore.putExtra("fighter2Record", fightListItem.getFighter2Record());
            pickAndScore.putExtra("fighter2Pic", fightListItem.getFighter2Pic());
            pickAndScore.putExtra("dbId", fightListItem.getDbId());
            pickAndScore.putExtra("outcome", fightListItem.getOutcome());
            context.startActivity(pickAndScore);
        });
        if (fightListItem.getDbId() != -1) {
            holder.constraintLayoutFightItem.setLongClickable(true);
            // Reference: https://stackoverflow.com/a/23195249
            holder.constraintLayoutFightItem.setOnLongClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        context);
                alert.setTitle("Alert");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePickAndScore(fightListItem.getDbId());
                        fightListItems.remove(fightListItem);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;
            });
            // Reference complete
        }

    }

    public void deletePickAndScore(int dbId) {
        class DeletePickAndScoreAsync extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    PickAndScore pickAndScores = DatabaseClient.getInstance(context).getAppDatabase().getPickAndScoreDAO().getPickAndScoreWithId(dbId);
                    DatabaseClient.getInstance(context).getAppDatabase().getPickAndScoreDAO().delete(pickAndScores);
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(context, String.valueOf(e), Toast.LENGTH_LONG).show();
                }
                return null;
            }

        }
        DeletePickAndScoreAsync saveChanges = new DeletePickAndScoreAsync();
        saveChanges.execute();
    }

    @Override
    public int getItemCount() {
        return fightListItems.size();
    }

    public class FightViewHolder extends RecyclerView.ViewHolder  {

        public TextView textViewFighter1Name;
        public TextView textViewFighter1Record;
        public ImageView imageViewFighter1Pic;
        public TextView textViewFighter2Name;
        public TextView textViewFighter2Record;
        public ImageView imageViewFighter2Pic;
        public ConstraintLayout constraintLayoutFightItem;

        public FightViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewFighter1Name = itemView.findViewById(R.id.fighter1);
            textViewFighter1Record = itemView.findViewById(R.id.fighter1Record);
            imageViewFighter1Pic = itemView.findViewById(R.id.fighter1Pic);
            textViewFighter2Name = itemView.findViewById(R.id.fighter2);
            textViewFighter2Record = itemView.findViewById(R.id.fighter2Record);
            imageViewFighter2Pic = itemView.findViewById(R.id.fighter2Pic);
            constraintLayoutFightItem = itemView.findViewById(R.id.constraintLayoutFightItem);
        }
    }

}
