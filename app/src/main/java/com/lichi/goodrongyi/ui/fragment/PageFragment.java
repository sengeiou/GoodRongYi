package com.lichi.goodrongyi.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lichi.goodrongyi.R;

/**
 * Created by test on 2017/12/13.
 */

@SuppressLint("ValidFragment")
public class PageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private boolean IS_LOADED = false;
    private static int mSerial=0;
    private int mTabPos=0;
    private boolean isFirst = true;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            Log.e("tag", "IS_LOADED="+IS_LOADED);
            if(!IS_LOADED){
                IS_LOADED = true;
                //这里执行加载数据的操作
                Log.e("tag", "我是page"+(mTabPos+1));
            }
            return;
        };
    };

    public PageFragment(int serial){
        mSerial = serial;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("tag","onCreate()方法执行");

    }

    public void sendMessage(){
        Message message = handler.obtainMessage();
        message.sendToTarget();
    }

    public void setTabPos(int mTabPos) {
        this.mTabPos = mTabPos;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("tag","onCreateView()方法执行");
        View view = inflater.inflate(R.layout.fragment_loans, container, false);
        //TextView textView = (TextView) view.findViewById(R.id.content);
        //textView.setText("我是page" + (mTabPos+1));
        //设置页和当前页一致时加载，防止预加载
        if (isFirst && mTabPos==mSerial) {
            isFirst = false;
            sendMessage();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("tag","onDestroyView()方法执行");
    }
}
