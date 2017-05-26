package com.am.demo.gallery;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.demo.gallery.adapter.ItemsRecyclerViewAdapter;
import com.am.demo.gallery.model.Item;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {
    public static final int REQUEST_CODE = 100;
    public static final String ITEMS = "items";
    private static final String MY_ITEMS = "myItems";
    public static final String RATE_LIST = "rateList";
    public static final String ITEM = "item";
    private RecyclerView itemsRecyclerView;
    private ItemsRecyclerViewAdapter adapter;
    private ArrayList<Item> items;
    private ArrayList<String> rateList;
    private SharedPreferences shref;
    private InformationFragment informationFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        items = new ArrayList<>();
        addNewItemsToList();
        shref = getActivity().getSharedPreferences(RATE_LIST, 0);
        Gson gson = new Gson();
        String response = shref.getString(RATE_LIST, "rateList");
        try {
            rateList = gson.fromJson(response,
                    new TypeToken<List<String>>() {
                    }.getType());
        } catch (JsonSyntaxException e) {
            rateList = new ArrayList<>();
            for (int i = 0; i < items.size(); i++)
                rateList.add("0.0");
        }
        for (int i = 0; i < items.size(); i++)
            items.get(i).setRate(Double.valueOf(rateList.get(i)));

        setupRecyclerView();

    }


    public void setupRecyclerView() {
        itemsRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_items);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemsRecyclerViewAdapter();
        itemsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::showInformationFragment);
        adapter.setRatingList(rateList);
        adapter.setItems(items);
    }

    private void showInformationFragment(Item item) {
        informationFragment = new InformationFragment();
        informationFragment.setOnRateChange(() -> adapter.notifyDataSetChanged());
        Bundle args = new Bundle();
        args.putParcelable(ITEM, item);
        args.putStringArrayList(RATE_LIST, rateList);
        informationFragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(isLandscape() ? R.id.container_fragmentInformation : R.id.container_fragmentListView,
                informationFragment, MainActivity.TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean isLandscape() {
        return getActivity().findViewById(R.id.container_fragmentInformation) != null;
    }

    private void addNewItemsToList() {
        String[] descriptions = getResources().getStringArray(R.array.descriptions);
        items.add(new Item(0, "Śpiąca myszka", 0, R.drawable.spiacamyszka, descriptions[0]));
        items.add(new Item(1, "i znowu myszka", 0, R.drawable.bigmouse, descriptions[1]));
        items.add(new Item(2, "Z przyjacielem", 0, R.drawable.mousewithbear, descriptions[2]));
        items.add(new Item(3, "Na huśtawce", 0, R.drawable.hamster2, descriptions[3]));
        items.add(new Item(4, "Chomik w filiżance", 0, R.drawable.hamsterincup, descriptions[4]));
        items.add(new Item(5, "Serowa myszka", 0, R.drawable.mousecheese, descriptions[5]));
        items.add(new Item(6, "Chomik", 0, R.drawable.hellohamster, descriptions[6]));
        items.add(new Item(7, "Kolejny chomik", 0, R.drawable.hamster3, descriptions[7]));
        items.add(new Item(8, "Myszka", 0, R.drawable.pluszmysz, descriptions[8]));
        items.add(new Item(9, "Chomik muzykant", 0, R.drawable.hamsterpiano, descriptions[9]));
        items.add(new Item(10, "Chomik sportowiec", 0, R.drawable.hamster1, descriptions[10]));
    }

    @Override
    public void onStop() {
        super.onStop();
        shref = getActivity().getSharedPreferences(MY_ITEMS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("items", json);
        editor.apply();
    }

    public interface OnRateChange {
        void onItemRate();
    }
}
