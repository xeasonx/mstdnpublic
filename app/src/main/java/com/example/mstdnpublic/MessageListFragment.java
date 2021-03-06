package com.example.mstdnpublic;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.mstdnResponseEntities.Error;
import com.example.mstdnResponseEntities.Status;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This Fragment uses a RecyclerView to display a list of message posts, contents of the list
 * is held by MessageListAdapter.
 * Message posts data are collected through MSTDNService, which is a bound service, and
 * ResponseHandler class, which is a subclass of Handler.
 */
public class MessageListFragment extends Fragment {
    private String host;
    private final int PORT = 80;
    private final String PROTOCOL = "https";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout refreshLayout;
    private ActionBar actionBar;
    private TweetListAdapter tweetListAdapter = new TweetListAdapter();
    private ResponseHandler responseHandler = new ResponseHandler();
    private MSTDNService mstdnService;
    boolean isBound = false;
    private boolean isRefreshing = true;
    private int tweetItemCount = 0;
//    private int vpos = 0;
//    private Snackbar snackbar;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("service", "connecting ...");
            MSTDNService.MSTDNRestfulBinder binder = (MSTDNService.MSTDNRestfulBinder) service;
            mstdnService = binder.getService();
            isBound = true;
            mstdnService.setHandler(responseHandler);
            Log.i("service", "connected");
//            snackbar.show();
            mstdnService.callApi(host, PORT, PROTOCOL,"TimelinePublic");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.i("service", "disconnected");
        }
    };

    public class ResponseHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int statusCode = bundle.getInt("statusCode");
            boolean isJson = bundle.getBoolean("isJson");
            boolean isSucceed = bundle.getBoolean("isSucceed");
            boolean isResponseStatus = false;
            boolean isResponseError = false;
            String body = bundle.getString("body");
            Status[] statuses = null;
            Error error = null;
//            Log.i("handler", String.valueOf(statusCode));
//            Log.i("handler", String.valueOf(isJson));
//            Log.i("handler", body.toString());
//            snackbar.dismiss();
            refreshLayout.setRefreshing(false);
            if (statusCode == 200 && isJson && isSucceed) {
                Gson gson = new Gson();
                try {
                    statuses = gson.fromJson(body, Status[].class);
                    isResponseStatus = true;
                } catch (JsonParseException e) {}

                if (!isResponseStatus) {
                    try {
                        error = gson.fromJson(body, Error.class);
                        isResponseError = true;
                    } catch (JsonParseException e) {}
                }

                if (isResponseStatus) {
                    if (statuses != null) {
                        ArrayList<Status> newItems = new ArrayList<>();
                        List<Status> currentItems = tweetListAdapter.getCurrentList();
                        newItems.addAll(Arrays.asList(statuses));
                        newItems.addAll(currentItems);
                        tweetItemCount = newItems.size();
                        tweetListAdapter.submitList(newItems);
                        recyclerView.setAdapter(tweetListAdapter);
                        recyclerView.smoothScrollToPosition(20);
                        Snackbar.make(getActivity().findViewById(R.id.fragment_main), R.string.status_updated, Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(getActivity().findViewById(R.id.fragment_main), R.string.status_update_failed, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(R.id.fragment_main), R.string.status_update_failed, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(getActivity().findViewById(R.id.fragment_main), R.string.status_update_failed, Snackbar.LENGTH_SHORT).show();
            }

            isRefreshing = false;
        }
    }

    public MessageListFragment() {
        // Required empty public constructor
    }

    public static MessageListFragment newInstance(String host) {
        MessageListFragment fragment = new MessageListFragment();
        Bundle args = new Bundle();
        args.putString("host", host);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.host = getArguments().getString("host");
        tweetListAdapter.setLocale(getActivity().getResources().getConfiguration().locale);
        Log.i("host", host);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_message_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        snackbar = Snackbar.make(getActivity().findViewById(R.id.fragment_main), R.string.status_updating, Snackbar.LENGTH_INDEFINITE);
        refreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.refresh_container);
        actionBar = ((AppCompatActivity)getActivity()).getActionBar();
        layoutManager = new LinearLayoutManager(getActivity());
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.message_recycle);
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing = true;
                mstdnService.callApi(host, PORT, PROTOCOL, "TimelinePublic");
            }
        });
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                vpos = dy;
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                System.out.format("%d, %b, %d%n", newState, recyclerView.canScrollVertically(-1), vpos);
//                if (!isRefreshing) {
//                    if (tweetItemCount == 0) {
//                        isRefreshing = true;
//                        snackbar.show();
//                        mstdnService.callApi(host, PORT, PROTOCOL, "TimelinePublic");
//                    } else {
//                        if (newState == RecyclerView.SCROLL_STATE_DRAGGING && !recyclerView.canScrollVertically(-1) && vpos < 0) {
//                            isRefreshing = true;
//                            if (vibrator.hasVibrator()) {
//                                vibrator.vibrate(VibrationEffect.EFFECT_HEAVY_CLICK);
//                            }
//                            snackbar.show();
//                            mstdnService.callApi(host, PORT, PROTOCOL, "TimelinePublic");
//                        }
//                    }
//                }
//            }
//        });
        Intent intent = new Intent(getActivity(), MSTDNService.class);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.i("service", String.valueOf(isBound));

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unbindService(connection);
        isBound = false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                if (!isRefreshing) {
                    refreshLayout.setRefreshing(true);
                    mstdnService.callApi(host, PORT, PROTOCOL, "TimelinePublic");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}