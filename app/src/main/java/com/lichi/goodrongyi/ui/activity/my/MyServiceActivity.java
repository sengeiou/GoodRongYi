package com.lichi.goodrongyi.ui.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.CommonAdapter;
import com.lichi.goodrongyi.adapter.OnItemClickListener;
import com.lichi.goodrongyi.adapter.ViewHolder;
import com.lichi.goodrongyi.bean.ChatMessageBean;
import com.lichi.goodrongyi.mvp.presenter.MyServicePresenter;
import com.lichi.goodrongyi.mvp.view.MyServiceView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.CommonUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MyServiceActivity extends BaseActivity<MyServiceView,MyServicePresenter> {
    @BindView(R.id.my_service_chat)       // 绑定聊天界面的recycleView
            RecyclerView mRecyclerView;

    @BindView(R.id.my_service_edit_text)
    EditText mEditText;                    // 绑定聊天输入框


    @OnEditorAction(R.id.my_service_edit_text)         // 绑定点击发送按钮后的事件
    public boolean sendMessage(TextView v, int actionId, KeyEvent event) {
        if(actionId == 0){
            // 发送消息
             if(v.getText().length()!=0) {
                 ChatMessageBean bean = new ChatMessageBean();
                 bean.setMessage(v.getText().toString());
                 bean.setType(0);
                 List<ChatMessageBean> list = new ArrayList<>();
                 list.add(bean);
                 mEditText.getText().clear();
                 mAdapter.addDatas(list);
                 //
                 mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
             }
               return false;
        }
        return false;
    }

    @OnClick(R.id.iv_back)                 // 绑定返回事件
    public void back() {
        finish();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeKeyboard();
        return super.onTouchEvent(event);
    }

    @Override
    public MyServicePresenter initPresenter() {
        return new MyServicePresenter();
    }

    private CommonAdapter<ChatMessageBean> mAdapter;
    private List<ChatMessageBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);
        ButterKnife.bind(this);
        testData();
        setAdapter();
    }

    public void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommonAdapter<ChatMessageBean>(this, R.layout.my_service_chat, mData) {
            @Override
            public void convert(ViewHolder holder, ChatMessageBean item, int position) {
                ImageView myHeadPic = holder.getView(R.id.my_head_pic);
                TextView myMessage = holder.getView(R.id.my_message);
                ImageView serviceHeadPic = holder.getView(R.id.service_head_pic);
                TextView serviceMessage = holder.getView(R.id.service_message);
                myHeadPic.setVisibility(View.INVISIBLE);
                myMessage.setVisibility(View.INVISIBLE);
                serviceHeadPic.setVisibility(View.INVISIBLE);
                serviceMessage.setVisibility(View.INVISIBLE);
                if (item.getType() == 0) {
                      myHeadPic.setVisibility(View.VISIBLE);
                      myMessage.setVisibility(View.VISIBLE);
                      // 填充我方消息信息
                      myMessage.setText(item.getMessage());
                      //
                } else {
                      serviceHeadPic.setVisibility(View.VISIBLE);
                      serviceMessage.setVisibility(View.VISIBLE);
                      // 填充对方消息信息
                      serviceMessage.setText(item.getMessage());
                      //
                }
            }
        };
        // 触摸recycleView时隐藏软键盘
       mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               closeKeyboard();
               return false;
           }
       });
       mAdapter.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(ViewGroup parent, View view, Object o, int position) {
               closeKeyboard();
           }

           @Override
           public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
               return false;
           }
       });
        //
        mRecyclerView.setAdapter(mAdapter);
    }

    private void testData() {
       ChatMessageBean bean1 = new ChatMessageBean();
       bean1.setMessage("jjjj");
       bean1.setType(0);
       ChatMessageBean bean2 = new ChatMessageBean();
       bean2.setMessage("uuuuuu");
       bean2.setType(1);
       mData.add(bean1);
       mData.add(bean2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
