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

import com.am.demo.catsandjokes.R;
import com.am.demo.catsandjokes.model.cats.Cat;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by malbor806 on 24.05.2017.
 */

public class ImageGridViewAdapter extends ArrayAdapter<Cat> {
    private Context context;
    private int layoutResourceId;
    private List<Cat> data;

    public ImageGridViewAdapter(Context context, int layoutResourceId, List<Cat> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.catPicture = (ImageView) row.findViewById(R.id.iv_picture);
            row.setTag(holder);
        } else {
            holder = (ImageHolder) row.getTag();
        }

        Picasso.with(context)
                .load(data.get(position).getUrl())
                .resize(300, 300)
                .centerCrop()
                .into(holder.catPicture);
        return row;
    }

    static class ImageHolder {
        ImageView catPicture;
    }
}
