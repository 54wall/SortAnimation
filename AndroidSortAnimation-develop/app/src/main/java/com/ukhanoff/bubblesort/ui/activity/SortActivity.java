package com.ukhanoff.bubblesort.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ukhanoff.bubblesort.R;
import com.ukhanoff.bubblesort.ui.fragment.SortFragment;

public class SortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        if (savedInstanceState == null) {
            SortFragment fragment = new SortFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment)
                    .commit();
        }
    }
}
