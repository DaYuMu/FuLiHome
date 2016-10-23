package cn.ucai.fulihome.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.view.DisplayUtils;

public class RegisterActivity extends BaseActivity {

    String username, usernick, userpassword, userpasswordqr;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this,"账号注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }


    @OnClick(R.id.RegisterButton)
    public void onClick() {
    }
}
