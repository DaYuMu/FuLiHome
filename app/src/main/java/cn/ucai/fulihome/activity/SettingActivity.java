package cn.ucai.fulihome.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.utils.ImageLoader;
import cn.ucai.fulihome.view.DisplayUtils;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.Default_Avatar)
    ImageView DefaultAvatar;
    @BindView(R.id.SettingUsername)
    TextView SettingUsername;
    @BindView(R.id.SettingUserNick)
    TextView SettingUserNick;

    SettingActivity mContext;

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

    @Override
    protected void initData() {
        User user = FuLiHomeApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatar(user),mContext,DefaultAvatar);
        } else {
            finish();
        }
        SettingUsername.setText(user.getMuserName());
        SettingUserNick.setText(user.getMuserNick());
    }

    @Override
    protected void setListener() {

    }


    @OnClick({R.id.BackPCA, R.id.AvatarBack, R.id.UserNameSetting, R.id.UserNickSetting, R.id.ButtonSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BackPCA:
                break;
            case R.id.AvatarBack:
                break;
            case R.id.UserNameSetting:
                break;
            case R.id.UserNickSetting:
                break;
            case R.id.ButtonSetting:
                break;
        }
    }
}
