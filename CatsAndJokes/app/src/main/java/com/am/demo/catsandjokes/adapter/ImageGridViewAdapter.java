package com.am.demo.catsandjokes.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.am.demo.catsandjokes.R;
import com.am.demo.catsandjokes.model.cats.Cat;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class ImageGridViewAdapter extends ArrayAdapter<Cat> {
    private Context context;
    private int layoutResourceId;
    private List<Cat> catsList;

    public ImageGridViewAdapter(Context context, int layoutResourceId, List<Cat> catsList) {
        super(context, layoutResourceId, catsList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.catsList = catsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = createHolder(row);
            row.setTag(holder);
        } else {
            holder = (ImageHolder) row.getTag();
        }
        addPicture(position, holder);
        return row;
    }

    @NonNull
    private ImageHolder createHolder(View row) {
        ImageHolder holder;
        holder = new ImageHolder();
        holder.catPicture = (ImageView) row.findViewById(R.id.iv_picture);
        holder.progressBar = (ProgressBar) row.findViewById(R.id.pb_progress);
        return holder;
    }

    private synchronized void addPicture(int position, ImageHolder holder) {
        Picasso.with(context)
                .load(catsList.get(position).getUrl())
                .fit()
                .centerInside()
                .into(holder.catPicture, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getContext(), R.string.error_while_downloading, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private static class ImageHolder {
        ImageView catPicture;
        ProgressBar progressBar;
    }
}
