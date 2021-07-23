package com.example.mstdnpublic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MessageListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MessageListAdapter messageListAdapter;
    MessageService messageService;
    boolean isBound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("service", "connecting ...");
            MessageService.MessageBinder binder = (MessageService.MessageBinder) service;
            messageService = binder.getService();
            isBound = true;
            Log.i("service", "connected");
            String[] randoms = messageService.getRandomNumber();
            messageListAdapter = new MessageListAdapter(randoms);
            recyclerView.setAdapter(messageListAdapter);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.i("service", "disconnected");
        }
    };

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public MessageListFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MessageListFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MessageListFragment newInstance(String param1, String param2) {
//        MessageListFragment fragment = new MessageListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Intent intent = new Intent(getActivity(), MessageService.class);
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