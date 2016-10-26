package cn.ucai.fulihome.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.activity.MainActivity;
import cn.ucai.fulihome.bean.MessageBean;
import cn.ucai.fulihome.bean.Result;
import cn.ucai.fulihome.bean.User;
import cn.ucai.fulihome.dao.UserDao;
import cn.ucai.fulihome.net.NetDao;
import cn.ucai.fulihome.net.OkHttpUtils;
import cn.ucai.fulihome.utils.ImageLoader;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;
import cn.ucai.fulihome.utils.ResultUtils;

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
    @BindView(R.id.CollectBao)
    LinearLayout CollectBao;
    @BindView(R.id.CollectCount)
    TextView CollectCount;

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
        L.e(TAG + "user" + user);
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatar(user), mContext, UserAvatar);
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
        L.e(TAG + "user" + user);
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatar(user), mContext, UserAvatar);
            UserName.setText(user.getMuserNick());
            syncUserInfo();
            syncCollectCount();
        }
    }

    @OnClick({R.id.Title_Settings, R.id.ToSetting})
    public void toSettings() {
        L.e(TAG + "@OnClick({R.id.Title_Settings,R.id.ToSetting})start");
        MFGT.gotoSettingActivity(mContext);
    }

    @OnClick(R.id.CollectBao)
    public void gotoCollectActivity() {
        MFGT.gotoCollectActivity(mContext);
    }

    private void syncUserInfo() {
        NetDao.syncUserInfo(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getListResultFromJson(s, User.class);
                if (result != null) {
                    User u = result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiHomeApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatar(user), mContext, UserAvatar);
                            UserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void syncCollectCount() {
        NetDao.getCollectCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    CollectCount.setText(result.getMsg());
                } else {
                    CollectCount.setText(String.valueOf(0));
                }
            }

            @Override
            public void onError(String error) {
                CollectCount.setText(String.valueOf(0));
            }
        });
    }

}
