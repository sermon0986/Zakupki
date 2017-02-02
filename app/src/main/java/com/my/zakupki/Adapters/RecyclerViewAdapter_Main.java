package com.my.zakupki.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.my.zakupki.DealClasses.Deal;
import com.my.zakupki.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_Main extends RecyclerView.Adapter<RecyclerViewAdapter_Main.ViewHolder> {

    private List<Deal> records;
    private List<Deal> mOriginalValues;
//    private ArrayFilter mFilter;

    public RecyclerViewAdapter_Main(List<Deal> SortedList) {
        records = SortedList;
        mOriginalValues = new ArrayList<>(records);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_item_main, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        final Deal record = records.get(i);

        viewHolder.number.setText(record.Number);
        viewHolder.publisher.setText(record.Publisher);
        viewHolder.price.setText(record.Price);
        viewHolder.currency.setText(record.Currency);

        viewHolder.publish_type.setText(record.PublishType);
        viewHolder.current_status.setText(record.CurrentStatus);
        viewHolder.description.setText(record.Description);
        viewHolder.publish_date.setText(record.PublishDate);
        viewHolder.update_date.setText(record.UpdateDate);

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void remove(Deal asset) {
        int position = records.indexOf(asset);
        records.remove(position);

        int orig_position = mOriginalValues.indexOf(asset);
        mOriginalValues.remove(orig_position);

        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView number;
        private TextView publisher;
        private TextView price;
        private TextView currency;
        private TextView publish_type;
        private TextView current_status;
        private TextView description;
        private TextView publish_date;
        private TextView update_date;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card);
            number = (TextView) itemView.findViewById(R.id.number);
            publisher = (TextView) itemView.findViewById(R.id.publisher);
            price = (TextView) itemView.findViewById(R.id.price);
            currency = (TextView) itemView.findViewById(R.id.currency);

            publish_type = (TextView) itemView.findViewById(R.id.publish_type);
            current_status = (TextView) itemView.findViewById(R.id.current_status);
            description = (TextView) itemView.findViewById(R.id.description);
            publish_date = (TextView) itemView.findViewById(R.id.publish_date);
            update_date = (TextView) itemView.findViewById(R.id.update_date);

        }
    }
}