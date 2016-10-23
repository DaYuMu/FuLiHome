package cn.ucai.fulihome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.utils.CommonUtils;
import cn.ucai.fulihome.utils.MFGT;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.Login)
    TextView Login;
    @BindView(R.id.RegisterName)
    TextView RegisterName;
    @BindView(R.id.RegisterNick)
    TextView RegisterNick;
    @BindView(R.id.LoginName)
    TextView LoginName;
    @BindView(R.id.LoginPassword)
    TextView LoginPassword;

    String username,usernick,userpassword,userpasswordqr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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

    /*@OnClick({R.id.LoginBack, R.id.RegisterButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LoginBack:
                MFGT.finish(this);
                break;
            case R.id.RegisterButton:
                if (TextUtils.isEmpty("")) {
                    CommonUtils.showShortToast(R.string.);
                }else if ()
                break;
        }
    }*/

}
