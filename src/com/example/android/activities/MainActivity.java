package com.example.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.example.android.fragments.MyListFragment;
import com.example.R;

public class MainActivity extends FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.list_frag, new MyListFragment()).commit();
        }
    }
}
