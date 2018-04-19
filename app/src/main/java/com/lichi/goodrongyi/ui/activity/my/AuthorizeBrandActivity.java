package com.lichi.goodrongyi.ui.activity.my;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.mvp.presenter.AuthorizeBrandPresenter;
import com.lichi.goodrongyi.mvp.view.AuthorizeBrandView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.Constants;
import com.lichi.goodrongyi.view.SpacingTextView;

/**
 * Created by test on 2018/3/1.
 */

public class AuthorizeBrandActivity extends BaseActivity<AuthorizeBrandView, AuthorizeBrandPresenter> {
    TextView title;
    int vipLevel;
    String nickname = "";

    @Override
    public AuthorizeBrandPresenter initPresenter() {
        return new AuthorizeBrandPresenter();
    }

    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_brand);
        title = (TextView) findViewById(R.id.title);
        // title.setLetterSpacing(10f);
        vipLevel = getIntent().getIntExtra(Constants.IntentParams.ID, 0);
        nickname = getIntent().getStringExtra(Constants.IntentParams.ID2);
        switch (vipLevel) {
            case 1: //铜牌
                title.setText("    兹授权铜牌用户张拥有重庆骊驰文化传播有限公司融开心APP代理权。");
                break;
            case 2: //银牌
                title.setText("    兹授权白金用户张拥有重庆骊驰文化传播有限公司融开心APP代理权。");
                break;
            case 3: //金牌
                title.setText("    兹授权白金用户张拥有重庆骊驰文化传播有限公司融开心APP代理权。");
                break;
            case 4: //白金
                //title.setText("兹授权白金用户" + nickname + "拥有重庆骊驰文化传播有限公司融开心APP代理权。");兹授权xxx拥有重庆骊驰文化传播有限公司融开心白金（具体等级）级代理权
                //str = "兹授权<font color='#FF0000'>白金</font>用户<font color='#FF0000'>" + nickname + "</font>拥有重庆骊驰文化传播有限公司融开心APP代理权。";
                str = "&nbsp;&nbsp;&nbsp;&nbsp;兹授权<font color='#FF0000'>" + nickname + "</font>拥有重庆骊驰文化传播有限公司融开心<font color='#FF0000'>白金</font>级代理权。";
                title.setTextSize(14);
                title.setText(Html.fromHtml(str));
                break;
            case 5: //砖石
                // title.setText("兹授权钻石用户" + nickname + "拥有重庆骊驰文化传播有限公司融开心APP代理权。");
                str = "&nbsp;&nbsp;&nbsp;&nbsp;兹授权<font color='#FF0000'>" + nickname + "</font>拥有重庆骊驰文化传播有限公司融开心<font color='#FF0000'>钻石</font>级代理权。";
                title.setTextSize(14);
                title.setText(Html.fromHtml(str));
                break;
            case 6: //皇冠
                // title.setText("兹授权皇冠用户" + nickname + "拥有重庆骊驰文化传播有限公司融开心APP代理权。");
                str = "&nbsp;&nbsp;&nbsp;&nbsp;兹授权<font color='#FF0000'>" + nickname + "</font>拥有重庆骊驰文化传播有限公司融开心<font color='#FF0000'>皇冠</font>级代理权。";
                title.setTextSize(14);
                title.setText(Html.fromHtml(str));
                //setTextColors(title, "兹授权皇冠用户" + nickname + "拥有重庆骊驰文化传播有限公司融开心APP代理权。", 3, 5, R.color.sq_red_color);
                // setTextColors(title,"兹授权皇冠用户" + nickname + "拥有重庆骊驰文化传播有限公司融开心APP代理权。", 7, 7 + nickname.length(), R.color.sq_red_color);
                break;
        }
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
