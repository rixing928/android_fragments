package com.example.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.example.R;
import com.example.android.fragments.nested.ParentFragment;

public class ParentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.list_frag, new ParentFragment()).commit();
        }
    }
}
