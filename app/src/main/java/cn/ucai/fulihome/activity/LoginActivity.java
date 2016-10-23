package cn.ucai.fulihome.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.Login)
    TextView Login;
    @BindView(R.id.LoginName)
    TextView LoginName;
    @BindView(R.id.LoginPassword)
    TextView LoginPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.e("LoginActivity.onCreate");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
                break;
            case R.id.ToRegisterButton:
                MFGT.gotoRegisterActivity(this);
                break;
        }
    }
}
