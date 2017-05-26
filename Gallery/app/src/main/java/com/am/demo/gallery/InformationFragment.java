package com.am.demo.gallery;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.am.demo.gallery.model.Item;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {
    private ImageView pictureImageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private RatingBar ratingBar;
    private Item item;
    private ArrayList<String> rateList;
    private ListViewFragment.OnRateChange onRateChange;
    private SharedPreferences shref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        Bundle extras = this.getArguments();//getIntent().getExtras().getParcelable("item");
        if (extras != null) {
            item = extras.getParcelable("item");
            rateList = extras.getStringArrayList("rateList");
            if (item != null) {
                pictureImageView.setImageResource(item.getImageResId());
                titleTextView.setText(item.getTitle());
                ratingBar.setRating((float) item.getRate());
                descriptionTextView.setText(item.getDescription());
            }
        }
        setListener();
    }

    private void setListener() {
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (item != null) {
                rateList.set(item.getId(), Double.toString(rating));
                if (onRateChange != null) {
                    onRateChange.onItemRate();
                    shref = getActivity().getSharedPreferences(ListViewFragment.RATE_LIST, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shref.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(rateList);
                    editor.putString("rateList", json);
                    editor.apply();
                }
            }
        });
    }

    public void setOnRateChange(ListViewFragment.OnRateChange onRateChange) {
        this.onRateChange = onRateChange;
    }

    private void findViews() {
        pictureImageView = (ImageView) getView().findViewById(R.id.iv_picture);
        titleTextView = (TextView) getView().findViewById(R.id.tv_title);
        descriptionTextView = (TextView) getView().findViewById(R.id.tv_description);
        ratingBar = (RatingBar) getView().findViewById(R.id.rb_rate);
    }



}
