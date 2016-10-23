package cn.ucai.fulihome.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.Result;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.Login)
    TextView Login;
    @BindView(R.id.LoginName)
    TextView LoginName;
    @BindView(R.id.LoginPassword)
    TextView LoginPassword;

    LoginActivity mContext;
    String name ;
    String password ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.e("LoginActivity.onCreate");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.LoginBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.LoginButton, R.id.ToRegisterButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LoginButton:
                checkedInput();
                break;
            case R.id.ToRegisterButton:
                MFGT.gotoRegisterActivity(this);
                break;
        }
    }

    private void checkedInput() {
        String name = LoginName.getText().toString().trim();
        String password = LoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
            LoginName.requestLayout();
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showLongToast(R.string.password_connot_be_empty);
            LoginPassword.requestLayout();
            return;
        }
        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        NetDao.login(mContext, name, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null) {
                    CommonUtils.showLongToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
//                        User user = result.getRetData();
                        MFGT.finish(mContext);
                    } else {
                        if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                            CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                        } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                            CommonUtils.showShortToast(R.string.login_fail_error_password);
                        } else {
                            CommonUtils.showShortToast(R.string.login_fail);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER) {
            String name = data.getStringExtra(I.User.USER_NAME);
            LoginName.setText(name);
        }
    }
}
