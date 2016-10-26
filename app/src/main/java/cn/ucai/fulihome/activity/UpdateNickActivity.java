package cn.ucai.fulihome.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.Result;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.dao.UserDao;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.utils.ResultUtils;
import cn.ucai.fulihome.view.DisplayUtils;

public class UpdateNickActivity extends BaseActivity {
    private static final String TAG = UpdateNickActivity.class.getSimpleName();
    @BindView(R.id.EditNick)
    EditText EditNick;

    User user = null;
    UpdateNickActivity mContext;
    String usernick =null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
        L.e(TAG+"UpdateNickActivity.onCreate.end");
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,"修改昵称");
    }

    @Override
    protected void initData() {
        user = FuLiHomeApplication.getUser();
        if (user != null) {
            EditNick.setText(user.getMuserNick());
        } else {
            finish();
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.LoginBack, R.id.UpdateNick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LoginBack:
                MFGT.finish(this);
                break;
            case R.id.UpdateNick:
                usernick = EditNick.getText().toString();
                if (usernick == null) {
                    CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
                } else if (usernick.equals(user.getMuserNick())) {
                    CommonUtils.showShortToast(R.string.nick_name_nofind);
                } else {
                    UpdateNick();
                }
                break;
        }
    }

    private void UpdateNick() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.updateing));
        pd.show();
        L.e(TAG+"UpdateNick.pd.show()");
        NetDao.updatanick(mContext, user.getMuserName(), usernick, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e(TAG+"NetDao.updateNick.onSuccess()");
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result == null) {
                    CommonUtils.showLongToast(R.string.update_fail);
                } else {
                    if (result.isRetMsg()) {
                        User user = result.getRetData();
                        L.e(TAG+"得到的用户信息"+user);
                        // 调用UserDao中的保存用户的方法。
                        UserDao userDao = new UserDao(mContext);
                        boolean isSuccess = userDao.saveUser(user);
                        if (isSuccess) {
//                            SharePreferenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                            FuLiHomeApplication.setUser(user);
                            setResult(RESULT_OK);
                            MFGT.finish(mContext);
                        } else {
                            CommonUtils.showShortToast(R.string.user_database_error);
                        }
                    } else {
                        if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                        } else if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                            CommonUtils.showShortToast(R.string.update_fail);
                        } else {
                            CommonUtils.showShortToast(R.string.update_fail);
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
}
