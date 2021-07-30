package com.example.mstdnpublic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.mstdnResponseEntities.Error;
import com.example.mstdnResponseEntities.Status;
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
//    private MessageListAdapter messageListAdapter;
    private TweetListAdapter tweetListAdapter = new TweetListAdapter();
    private ResponseHandler responseHandler = new ResponseHandler();
    MSTDNService mstdnService;
    boolean isBound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("service", "connecting ...");
            MSTDNService.MSTDNRestfulBinder binder = (MSTDNService.MSTDNRestfulBinder) service;
            mstdnService = binder.getService();
            isBound = true;
            mstdnService.setHandler(responseHandler);
            Log.i("service", "connected");
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
            String body = bundle.getString("body");
            Status[] statuses = null;
            Error error = null;
            Log.i("handler", String.valueOf(statusCode));
            Log.i("handler", String.valueOf(isJson));
            Log.i("handler", body);
            if (statusCode == 200 && isJson && isSucceed) {
                Gson gson = new Gson();
                try {
                    statuses = gson.fromJson(body, Status[].class);
                } catch (JsonParseException e) {
                    error = gson.fromJson(body, Error.class);
                }
                if (statuses != null) {
//                    int size = statuses.length;
//                    String[] avatarUrls = new String[size];
//                    String[] contents = new String[size];
//                    String[] displayNames = new String[size];
//                    for (int i=0; i<statuses.length; i++) {
//                        avatarUrls[i] = statuses[i].account.avatar;
//                        contents[i] = statuses[i].content;
//                        displayNames[i] = statuses[i].account.display_name;
//                    }
//                    messageListAdapter = new MessageListAdapter(avatarUrls, displayNames, contents);
                    ArrayList<Status> newItems = new ArrayList<>();
                    List<Status> currentItems = tweetListAdapter.getCurrentList();
                    newItems.addAll(Arrays.asList(statuses));
                    newItems.addAll(currentItems);
                    tweetListAdapter.submitList(newItems);
                    recyclerView.setAdapter(tweetListAdapter);
//                    recyclerView.setAdapter(messageListAdapter);
                } else {
                    Toast.makeText(getActivity(), error.error, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "none 200 response", Toast.LENGTH_SHORT).show();
            }
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
        this.host = getArguments().getString("host");
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
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.message_recycle);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mstdnService.callApi(host, PORT, PROTOCOL, "TimelinePublic");
                }
            }
        });
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
}