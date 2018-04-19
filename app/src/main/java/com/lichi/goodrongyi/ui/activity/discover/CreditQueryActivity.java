package com.lichi.goodrongyi.ui.activity.discover;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lichi.goodrongyi.R;
import com.lichi.goodrongyi.adapter.WrapAdapter;
import com.lichi.goodrongyi.bean.QueryUserBean;
import com.lichi.goodrongyi.mvp.presenter.CreditQueryPresenter;
import com.lichi.goodrongyi.mvp.view.CreditQueryView;
import com.lichi.goodrongyi.ui.base.BaseActivity;
import com.lichi.goodrongyi.utill.T;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CreditQueryActivity extends BaseActivity<CreditQueryView, CreditQueryPresenter> {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_current_city)
    TextView mTvCurrentCity;
    @BindView(R.id.ll_current_city)
    LinearLayout mLlCurrentCity;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_confirm_code)
    EditText mEtConfirmCode;
    @BindView(R.id.iv_agreement_check)
    ImageView mIvAgreementCheck;
    @BindView(R.id.tv_agreement_title)
    TextView mTvAgreementTitle;
    @BindView(R.id.btn_quick_query)
    Button mBtnQuickQuery;


    private boolean mAgreementChecked;
    private List<String> mCities;

    private PopupWindow mPopupWindow;
    private ListView mListView;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_query);
        type = getIntent().getIntExtra("type", 0);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {
        mAgreementChecked = false;
        mIvAgreementCheck.setImageResource(R.mipmap.checked_false);

        View view = getLayoutInflater().inflate(R.layout.popup_city, null);

        mListView = (ListView) view.findViewById(R.id.lv_city);
        mListView.setAdapter(new WrapAdapter<String>(this, mCities, R.layout.popu_item) {
            @Override
            public void convert(ViewHolder helper, String item, int position) {
                TextView textView = helper.getView(R.id.tv_popu);
                textView.setText(item);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
                mTvCurrentCity.setText(mCities.get(position));

            }
        });


        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    private void initData() {
        mCities = new ArrayList<>();
        String json = "";
        try {
            InputStream input = getAssets().open("city.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);

            JSONArray provinceList = jsonObject.optJSONArray("provinces");
            for (int i = 0; i < provinceList.length(); i++) {
                JSONArray cityList = ((JSONObject) provinceList.get(i)).optJSONArray("citys");
                for (int j = 0; j < cityList.length(); j++) {
                    mCities.add(((JSONObject) cityList.get(j)).optString("citysName"));
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "IOException", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(this, "JSONException", Toast.LENGTH_SHORT).show();
        }
        ;
    }

    @Override
    public CreditQueryPresenter initPresenter() {
        return new CreditQueryPresenter();
    }

    @OnClick({R.id.tv_back, R.id.ll_current_city, R.id.iv_agreement_check, R.id.tv_agreement_title, R.id.btn_quick_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                CreditQueryActivity.this.finish();
                break;
            case R.id.ll_current_city:
                mPopupWindow.showAsDropDown(view, this.getWindowManager().getDefaultDisplay().getHeight(), this.getWindowManager().getDefaultDisplay().getWidth() - 500);
                break;
            case R.id.iv_agreement_check:
                if (mAgreementChecked == false) {
                    mIvAgreementCheck.setImageResource(R.mipmap.checked_true);
                    mAgreementChecked = true;
                } else {
                    mIvAgreementCheck.setImageResource(R.mipmap.checked_false);
                    mAgreementChecked = false;
                }
                break;
            case R.id.tv_agreement_title:
                startActivity(new Intent(CreditQueryActivity.this, AuthorizeAgreementActivity.class));
                break;
            case R.id.btn_quick_query:
                getInfo();

                break;
        }
    }


    public void getInfo() {
        String cityName = mTvCurrentCity.getText().toString();
        String account = mEtAccount.getText().toString().trim();
        String password = mEtPassword.getText().toString();
        String code = mEtConfirmCode.getText().toString().trim();
        if (TextUtils.isEmpty(cityName)) {
            T.showShort(CreditQueryActivity.this, "请选择账户所在的城市");
            return;
        }
        if (TextUtils.isEmpty(account)) {
            T.showShort(CreditQueryActivity.this, "请输入查询的账户");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            T.showShort(CreditQueryActivity.this, "请输入账户密码");
            return;
        }
        if (!mAgreementChecked) {
            T.showShort(CreditQueryActivity.this, "请先阅读并同意授权协议");
            return;

        }
        QueryUserBean bean = new QueryUserBean();
        bean.setCityName(cityName);
        bean.setCode(code);
        bean.setPassWord(password);
        bean.setUserName(account);
        Intent intent = new Intent(CreditQueryActivity.this, DataListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",bean);
        intent.putExtras(bundle);
        startActivity(intent);
       // getNet();
    }

    public void getNet() {
        new AlertDialog.Builder(CreditQueryActivity.this).setMessage("正在查询，请稍后...").show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettach();
    }
}
