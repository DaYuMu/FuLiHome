package cn.ucai.fulihome.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.view.DisplayUtils;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.LoginBack)
    ImageView LoginBack;
    @BindView(R.id.Login)
    TextView Login;
    @BindView(R.id.UserName)
    EditText UserName;
    @BindView(R.id.Phone)
    EditText UserPhone;
    @BindView(R.id.Street)
    EditText UserStreet;

    OrderActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        mContext = this;
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        // 设置要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "cnp", "bfb"});
        //设置是否支持外卡支付， true：支持， false：不支持， 默认不支持外卡
        PingppOne.SUPPORT_FOREIGN_CARD = true;
        //提交数据的格式，默认格式为json
        //PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";
        //设置APP_ID和PUBLISHABLE_KEY(应用快捷支付需要)
        PingppOne.APP_ID = "Ping++ App ID";
        PingppOne.PUBLISHABLE_KEY = "Ping++ Publishable Key";
        //是否开启日志
        PingppLog.DEBUG = true;
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, "支付页面");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.ToBuying)
    public void Buying() {
        User user = FuLiHomeApplication.getUser();
        if (user == null) {
            finish();
        } else {
            String Waiting = UserName.getText().toString();
            if (Waiting == null) {
                CommonUtils.showShortToast(R.string.Charg_no_empty);
                UserName.requestFocus();
            } else {
                String Phone = UserPhone.getText().toString();
                if (Phone == null) {
                    CommonUtils.showShortToast(R.string.Charg_no_empty);
                    UserPhone.requestFocus();
                } else if (Phone.matches("[\\d]{11}")) {
                    CommonUtils.showShortToast(R.string.Charg_Phone_error);
                    UserPhone.requestFocus();
                } else {
                    String Street = UserStreet.getText().toString();
                    if (Street == null) {
                        CommonUtils.showShortToast(R.string.Charg_no_empty);
                        UserStreet.requestFocus();
                    } else {
                        CommonUtils.showShortToast(R.string.Charg_success);
                    }
                }
            }
        }

    }

    @OnClick(R.id.LoginBack)
    public void onClick() {
        MFGT.finish(mContext);
    }
}
