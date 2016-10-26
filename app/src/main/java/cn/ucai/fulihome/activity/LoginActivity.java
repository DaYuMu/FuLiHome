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
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.Result;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.dao.SharePreferenceUtils;
import cn.ucai.fulihome.dao.UserDao;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.utils.ResultUtils;
import cn.ucai.fulihome.view.DisplayUtils;

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
        mContext = this;
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,getResources().getString(R.string.login));
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
        L.e(TAG+"LoginActivity.checkedInput().action");
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
        L.e(TAG+"LoginActivity.checkedInput().end");
        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        L.e(TAG+"login.pd.show()");
        NetDao.login(mContext, LoginName.getText().toString(), LoginPassword.getText().toString(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e(TAG+"NetDao.login.onSuccess()");
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result == null) {
                    CommonUtils.showLongToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        User user = result.getRetData();
                        L.e(TAG+"得到的用户信息"+user);
                        // 调用UserDao中的保存用户的方法。
                        UserDao userDao = new UserDao(mContext);
                        boolean isSuccess = userDao.saveUser(user);
                        if (isSuccess) {
                            SharePreferenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                            L.e(TAG,"username="+user.getMuserName());
                            FuLiHomeApplication.setUser(user);
                            L.e(TAG,"user="+user);
                            MFGT.finish(mContext);
                        } else {
                            CommonUtils.showShortToast(R.string.user_database_error);
                        }
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
                pd.dismiss();
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
