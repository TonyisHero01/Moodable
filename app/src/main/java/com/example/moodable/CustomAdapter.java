package com.example.moodable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private List<Data> localDataSet;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
        }

        public ImageView getImageView() {
            return imageView;
        }
        public TextView getTextView() {
            return  textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CustomAdapter(List<Data> dataSet) {
        localDataSet = dataSet;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dome, viewGroup, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TextView textView = viewHolder.getTextView();
        String day = localDataSet.get(position).day+"-";
        if (day.equals("-1-")) {
            day = "";
        }
        String timeString = day+localDataSet.get(position).month+"-"+localDataSet.get(position).year;
        textView.setText(timeString);
        ImageView imageView = viewHolder.getImageView();
        imageView.setImageResource(localDataSet.get(position).drawable);
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}