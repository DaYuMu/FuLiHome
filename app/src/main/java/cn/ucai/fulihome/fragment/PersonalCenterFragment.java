package cn.ucai.fulihome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.activity.MainActivity;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.utils.ImageLoader;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();
    @BindView(R.id.UserAvatar)
    ImageView UserAvatar;
    @BindView(R.id.UserName)
    TextView UserName;

    MainActivity mContext;
    User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, null);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user = FuLiHomeApplication.getUser();
        L.e(TAG+"user"+user);
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatar(user),mContext,UserAvatar);
            UserName.setText(user.getMuserNick());
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiHomeApplication.getUser();
        L.e(TAG+"user"+user);
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatar(user),mContext,UserAvatar);
            UserName.setText(user.getMuserName());
        }
    }

    @OnClick({R.id.Title_Settings,R.id.ToSetting})
    public void onClick() {
        L.e(TAG+"@OnClick({R.id.Title_Settings,R.id.ToSetting})start");
        MFGT.gotoSettingActivity(mContext);
    }
}
