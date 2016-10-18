package cn.ucai.fulihome.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.fragment.NewGoodsFragment;
import cn.ucai.fulihome.utils.L;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnNewGoods)
    RadioButton btnNewGoods;
    @BindView(R.id.btnBoutique)
    RadioButton btnBoutique;
    @BindView(R.id.btnCategory)
    RadioButton btnCategory;
    @BindView(R.id.btnCart)
    RadioButton btnCart;
    @BindView(R.id.btnPersonal)
    RadioButton btnPersonal;

    int index;
    RadioButton[] radioButtons;
    Fragment[] fragments;

    NewGoodsFragment mNewGoodsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
        L.e("main", "MainActivity.onCreate()");
    }

    private void initFragment() {
        fragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rl, mNewGoodsFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    private void initView() {
        radioButtons = new RadioButton[5];
        radioButtons[0] = btnNewGoods;
        radioButtons[1] = btnBoutique;
        radioButtons[2] = btnCategory;
        radioButtons[3] = btnCart;
        radioButtons[4] = btnPersonal;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.btnNewGoods:
                index = 0;
                break;
            case R.id.btnBoutique:
                index = 1;
                break;
            case R.id.btnCategory:
                index = 2;
                break;
            case R.id.btnCart:
                index = 3;
                break;
            case R.id.btnPersonal:
                index = 4;
                break;
        }
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < radioButtons.length; i++) {
            if (i == index) {
                radioButtons[i].setChecked(true);
            } else {
                radioButtons[i].setChecked(false);
            }
        }
    }
}
