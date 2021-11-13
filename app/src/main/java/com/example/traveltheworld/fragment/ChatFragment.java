package com.example.traveltheworld.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.traveltheworld.R;
import com.example.traveltheworld.adapter.MessageAdapter;
import com.example.traveltheworld.databinding.ActivityChatFragmentBinding;
import com.example.traveltheworld.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    ActivityChatFragmentBinding binding;
    MessageAdapter messageAdapter;
    List<Message> messages;


    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_chat_fragment, container,false);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        binding.rcMessage.setLayoutManager(linearLayoutManager);
        messageAdapter= new MessageAdapter();
        messages= new ArrayList<>();
        messageAdapter.setData(messages);
        binding.rcMessage.setAdapter(messageAdapter);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }


        });
        binding.etMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkKeyboard();
            }
        });

        return binding.getRoot();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void sendMessage() {
            String strMessage = binding.etMessage.getText().toString().trim();
            if (TextUtils.isEmpty(strMessage)){
                return;
            }
            messages.add(new Message(strMessage));
            messageAdapter.notifyDataSetChanged();
            binding.rcMessage.scrollToPosition(messages.size() - 1);
            binding.etMessage.setText("");
    }
    private void checkKeyboard(){
        final View activityRootView = binding.activityroot;
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);

                int height = activityRootView.getRootView().getHeight()- r.height();
                if (height>0.25*activityRootView.getRootView().getHeight()){
                    if (messages.size()>0){
                        binding.rcMessage.scrollToPosition(messages.size()-1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}