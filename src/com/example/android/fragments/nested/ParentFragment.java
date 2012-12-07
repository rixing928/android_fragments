package com.example.android.fragments.nested;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.R;

public class ParentFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.parent_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ChildOneFragment childOne = new ChildOneFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child_one, childOne);

        ChildTwoFragment childTwo = new ChildTwoFragment();
        transaction.add(R.id.child_two, childTwo).commit();
    }

    public void replaceFragment() {
        ChildNextFragment childNext = new ChildNextFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Doesn't work with nested fragments :(
        /*transaction.addToBackStack(null);*/
        transaction.add(R.id.child_one, childNext).commit();
    }
}
