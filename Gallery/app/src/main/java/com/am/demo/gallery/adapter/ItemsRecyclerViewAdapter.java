package com.am.demo.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.demo.gallery.R;
import com.am.demo.gallery.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malbor806 on 09.04.2017.
 */

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder> {
    private List<Item> items;
    private List<String> rateList;
    private OnItemClickListener onItemClickListener;

    public ItemsRecyclerViewAdapter() {
        items = new ArrayList<>();
        rateList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //przerobienie xml na obiekt javowy ktory zwraca mi widok do tego obiektu
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setClickListener(onItemClickListener);
        viewHolder.setContext(view.getContext());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), rateList);
    }

    @Override
    public int getItemCount() {
        return items.size() > 0 ? items.size() : 0;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setRatingList(List<String> rateList) {
        this.rateList = rateList;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pictureImageView;
        TextView rateTextView;
        TextView titleTextView;
        private Item item;
        private OnItemClickListener clickListener;
        private Context context;

        public ViewHolder(final View itemView) {
            super(itemView);
            pictureImageView = (ImageView) itemView.findViewById(R.id.iv_imgIcon);
            rateTextView = (TextView) itemView.findViewById(R.id.tv_rate);
            titleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onItemClick(item);
                }
            });
        }


        public void setClickListener(OnItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        void bind(Item item, List<String> rateList) {
            this.item = item;
            Picasso.with(context).load(item.getImageResId()).resize(200,200).centerCrop().into(pictureImageView);
            titleTextView.setText(item.getTitle());
            rateTextView.setText(rateList.get(item.getId()));

        }


        public void setContext(Context context) {
            this.context = context;
        }
    }
}
