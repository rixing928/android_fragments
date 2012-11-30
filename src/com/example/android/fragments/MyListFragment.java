package com.example.android.fragments;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.R;
import com.example.android.UpdateService;
import com.example.android.activities.DetailsActivity;

public class MyListFragment extends Fragment {

    private static MyListFragment Instance = null;

    private View view;
    private UpdateService mBoundService;
    private boolean mIsBound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2"};

        ListView list = (ListView) view.findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, values);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String text = (String) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsFragment.EXTRA_TEXT, text);
                startActivity(intent);
            }
        });

        Button start = (Button) view.findViewById(R.id.start_service);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBoundService == null) {
                    doBindService();
                    getActivity().startService(new Intent(getActivity(), UpdateService.class));
                    updateButton(true);
                } else {
                    getActivity().stopService(new Intent(getActivity(), UpdateService.class));
                    doUnbindService();
                    updateButton(false);
                }
            }
        });
    }

    private void updateButton(final boolean b) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button start = (Button) view.findViewById(R.id.start_service);
                start.setText(b ? R.string.stop_service : R.string.start_service);
            }
        });
    }

    public ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((UpdateService.LocalBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            Toast.makeText(getActivity(), R.string.update_service_disconnected, Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        getActivity().bindService(new Intent(getActivity(), UpdateService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            getActivity().unbindService(mConnection);
            mIsBound = false;
        }

        mBoundService = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getActivity(), UpdateService.class));
        doUnbindService();
    }

    private void refresh() {
        Toast.makeText(getActivity(), "List Updated", Toast.LENGTH_SHORT).show();
    }

    public static class BroadcastListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Instance == null) return;

            if (intent.getAction().equals(UpdateService.ACTION_UPDATE)) {
                Instance.refresh();
            }
        }
    }
}
