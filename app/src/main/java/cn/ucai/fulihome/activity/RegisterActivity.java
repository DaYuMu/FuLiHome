package cn.ucai.fulihome.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.Result;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.view.DisplayUtils;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.RegisterName)
    TextView RegisterName;
    @BindView(R.id.RegisterNick)
    TextView RegisterNick;
    @BindView(R.id.RegisterPassword)
    TextView RegisterPassword;
    @BindView(R.id.RegisterPasswordqr)
    TextView RegisterPasswordqr;
    @BindView(R.id.RegisterButton)
    Button RegisterButton;
    @BindView(R.id.Login)
    TextView Login;

    String username;
    String usernick;
    String userpassword;

    RegisterActivity mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "账号注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }


    @OnClick(R.id.RegisterButton)
    public void onRegisterChilk() {
        username = RegisterName.getText().toString().trim();
        usernick = RegisterNick.getText().toString().trim();
        userpassword = RegisterPassword.getText().toString().trim();
        String userpasswordqr = RegisterPasswordqr.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            RegisterName.requestLayout();
            return;
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            CommonUtils.showShortToast(R.string.illegal_user_name);
            RegisterName.requestLayout();
            return;
        } else if (TextUtils.isEmpty(usernick)) {
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            RegisterNick.requestLayout();
            return;
        } else if (TextUtils.isEmpty(userpassword)) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            RegisterPassword.requestLayout();
            return;
        } else if (TextUtils.isEmpty(userpasswordqr)) {
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            RegisterPasswordqr.requestLayout();
            return;
        } else if (!userpassword.equals(userpasswordqr)) {
            CommonUtils.showShortToast(R.string.two_input_password);
            RegisterPasswordqr.requestLayout();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        NetDao.register(mContext, username, usernick, userpassword, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null) {
                    CommonUtils.showLongToast(R.string.register_fail);
                } else {
                    if (result.isRetMsg()) {


                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(mContext);
                    } else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        RegisterName.requestLayout();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG,"Register.onError");
            }
        });
    }
}