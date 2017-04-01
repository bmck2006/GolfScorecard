package com.houndware.golfscorecard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.houndware.golfscorecard.R;
import com.houndware.golfscorecard.model.Hole;
import com.houndware.golfscorecard.ui.MainActivity;


public class ListAdapter extends BaseAdapter {

    private Context context;
    private Hole[] holes;

    public ListAdapter(Context context, Hole[] holes) {
        this.context = context;
        this.holes = holes;
    }
    @Override
    public int getCount() {
        return holes.length;
    }

    @Override
    public Object getItem(int position) {
        return holes[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; // Not used
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
            holder = new ViewHolder();
            holder.holeLabel = (TextView) convertView.findViewById(R.id.holeLabel);
            holder.strokesLabel = (TextView) convertView.findViewById(R.id.strokesLabel);
            holder.parCount = (TextView) convertView.findViewById(R.id.parCount);
            holder.parLabel = (TextView) convertView.findViewById(R.id.parLabel);
            holder.strokeCount = (TextView) convertView.findViewById(R.id.strokeCount);
            holder.addParButton = (Button) convertView.findViewById(R.id.addParButton);
            holder.removeParButton = (Button) convertView.findViewById(R.id.removeParButton);
            holder.removeStrokeButton = (Button) convertView.findViewById(R.id.removeStrokeButton);
            holder.addStrokeButton = (Button) convertView.findViewById(R.id.addStrokeButton);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.holeLabel.setText(holes[position].getLabel());
        holder.parCount.setText(String.valueOf(holes[position].getPar()));
        holder.strokeCount.setText(String.valueOf(holes[position].getStrokeCount()));

        holder.removeParButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedParCount = holes[position].getPar() - 1;
                if (updatedParCount < 0) {
                    updatedParCount = 0;
                }
                else {
                    MainActivity.totalPar -= 1;
                    MainActivity.score = MainActivity.totalStrokes - MainActivity.totalPar;
                }
                holes[position].setPar(updatedParCount);
                holder.parCount.setText(String.valueOf(updatedParCount));
                MainActivity.scoreView.setText(String.valueOf(MainActivity.score));
            }
        });
        holder.addParButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedParCount = holes[position].getPar() + 1;

                MainActivity.totalPar += 1;
                MainActivity.score = MainActivity.totalStrokes - MainActivity.totalPar;
                holes[position].setPar(updatedParCount);
                holder.parCount.setText(String.valueOf(updatedParCount));
                MainActivity.scoreView.setText(String.valueOf(MainActivity.score));
            }
        });

        holder.removeStrokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedStrokeCount = holes[position].getStrokeCount() - 1;
                if (updatedStrokeCount < 0) {
                    updatedStrokeCount = 0;
                }
                else {
                    MainActivity.totalStrokes -= 1;
                    MainActivity.score = MainActivity.totalStrokes - MainActivity.totalPar;
                }
                holes[position].setStrokeCount(updatedStrokeCount);
                holder.strokeCount.setText(String.valueOf(updatedStrokeCount));
                MainActivity.totalStrokesView.setText(String.valueOf(MainActivity.totalStrokes));
                MainActivity.scoreView.setText(String.valueOf(MainActivity.score));
            }
        });
        holder.addStrokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatedStrokeCount = holes[position].getStrokeCount() + 1;

                MainActivity.totalStrokes += 1;
                MainActivity.score = MainActivity.totalStrokes - MainActivity.totalPar;
                holes[position].setStrokeCount(updatedStrokeCount);
                holder.strokeCount.setText(String.valueOf(updatedStrokeCount));
                MainActivity.totalStrokesView.setText(String.valueOf(MainActivity.totalStrokes));
                MainActivity.scoreView.setText(String.valueOf(MainActivity.score));
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView holeLabel;
        TextView strokesLabel;
        TextView strokeCount;
        TextView parCount;
        TextView parLabel;
        Button addParButton;
        Button removeParButton;
        Button removeStrokeButton;
        Button addStrokeButton;
    }
}
