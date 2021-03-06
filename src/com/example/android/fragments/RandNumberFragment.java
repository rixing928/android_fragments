package com.example.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.R;

public class RandNumberFragment extends Fragment {

    private String text;
    private View view;
    private long randNumber;

    public static RandNumberFragment newInstance(String text) {
        RandNumberFragment f = new RandNumberFragment();
        Bundle args = new Bundle();
        args.putString(DetailsFragment.EXTRA_TEXT, text);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            text = args.getString(DetailsFragment.EXTRA_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rand_number_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("text");
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) view.findViewById(R.id.text);
                tv.setText(text);
            }
        });

        Button rand = (Button) view.findViewById(R.id.rand_btn);
        rand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randNumber = Math.round(Math.random() * 100);
                Toast.makeText(getActivity(), String.valueOf(randNumber), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", text);
    }

    public void updateDetailsFragment() {
        if (randNumber > 0) {
            ((DetailsFragment) getTargetFragment()).setText(String.valueOf(randNumber), getTargetRequestCode());
        }
    }
}
