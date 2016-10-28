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
