package cn.ucai.fulihome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.dao.SharePreferenceUtils;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.ImageLoader;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.utils.OnSetAvatarListener;
import cn.ucai.fulihome.view.DisplayUtils;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.Default_Avatar)
    ImageView DefaultAvatar;
    @BindView(R.id.SettingUsername)
    TextView SettingUsername;
    @BindView(R.id.SettingUserNick)
    TextView SettingUserNick;
    User user;
    SettingActivity mContext;
    OnSetAvatarListener mOnSetAvatarListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        mContext = this;
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
    }

    private void showinfo() {
        user = FuLiHomeApplication.getUser();
        if (user == null) {
            ImageLoader.setAvatar(ImageLoader.getAvatar(user), mContext, DefaultAvatar);
            SettingUsername.setText(user.getMuserName());
            SettingUserNick.setText(user.getMuserNick());
        }
    }
    @Override
    protected void initData() {
        if (user == null) {
            finish();
            return;
        }
        showinfo();
    }

    @Override
    protected void setListener() {

    }


    @OnClick({R.id.BackPCA, R.id.AvatarSetting, R.id.UserNameSetting, R.id.UserNickSetting, R.id.ButtonSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BackPCA:
                MFGT.finish(this);
                break;
            case R.id.AvatarSetting:
                mOnSetAvatarListener = new OnSetAvatarListener(mContext, R.id.SettingLayout,
                        user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.UserNameSetting:
                CommonUtils.showShortToast(R.string.user_name_connot_be_setting);
                break;
            case R.id.UserNickSetting:
                MFGT.gotoUpdateNickActivity(mContext);
                break;
            case R.id.ButtonSetting:
                logout();
                break;
        }
    }

    private void logout() {
        if (user != null) {
            SharePreferenceUtils.getInstance(mContext).removeuser();
            FuLiHomeApplication.setUser(null);
            MFGT.gotoLoginActivity(mContext);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showinfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CommonUtils.showShortToast(R.string.update_user_nick_sucess);
    }
}
