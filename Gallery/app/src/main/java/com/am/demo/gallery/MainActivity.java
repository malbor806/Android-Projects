package com.am.demo.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private ListViewFragment listViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.container_fragmentListView) != null) {
            if (savedInstanceState != null) {
                return;
            }

            listViewFragment = new ListViewFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_fragmentListView, listViewFragment, TAG).commit();

        }
    }

}
