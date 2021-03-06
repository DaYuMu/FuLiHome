package cn.ucai.fulihome.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulihome.FuLiHomeApplication;
import cn.ucai.fulihome.I;
import cn.ucai.fulihome.R;
import cn.ucai.fulihome.fragment.BoutiqueFragment;
import cn.ucai.fulihome.fragment.CartFragment;
import cn.ucai.fulihome.fragment.CategoryFragment;
import cn.ucai.fulihome.fragment.NewGoodsFragment;
import cn.ucai.fulihome.fragment.PersonalCenterFragment;
import cn.ucai.fulihome.utils.L;
import cn.ucai.fulihome.utils.MFGT;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
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
    BoutiqueFragment mboutiqueFragment;
    CategoryFragment mCategoryFragment;
    CartFragment mCartFragment;
    PersonalCenterFragment mPersonalCenterFragment;

    int currentindex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.e("main", "MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        L.e("main", "MainActivity.onCreate()完成");
    }


    @Override
    protected void initView() {
        radioButtons = new RadioButton[5];
        radioButtons[0] = btnNewGoods;
        radioButtons[1] = btnBoutique;
        radioButtons[2] = btnCategory;
        radioButtons[3] = btnCart;
        radioButtons[4] = btnPersonal;
    }

    @Override
    protected void initData() {
        initFragment();
    }

    private void initFragment() {
        fragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mboutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mCartFragment  =new CartFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        fragments[0] = mNewGoodsFragment;
        fragments[1] = mboutiqueFragment;
        fragments[2] = mCategoryFragment;
        fragments[3] = mCartFragment;
        fragments[4] = mPersonalCenterFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rl, mNewGoodsFragment)
//                .add(R.id.rl, mboutiqueFragment)
//                .add(R.id.rl, mCategoryFragment)
//                .add(R.id.rl,mCartFragment)
//                .hide(mCartFragment)
//                .hide(mboutiqueFragment)
//                .hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    @Override
    protected void setListener() {

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
                if (FuLiHomeApplication.getUser() == null) {
                    MFGT.gotoLoginActivityFromCart(this);
                } else {
                    index = 3;
                }
                break;
            case R.id.btnPersonal:
                L.e("MainActivity.onCheckedChange.btnPersonal:");
                if (FuLiHomeApplication.getUser() == null) {
                    MFGT.gotoLoginActivity(this);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();

    }

    private void setFragment() {
        if (index != currentindex) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragments[currentindex]);
            if (!fragments[index].isAdded()) {
                fragmentTransaction.add(R.id.rl, fragments[index]);
            }
            fragmentTransaction.show(fragments[index]).commit();
            setRadioButtonStatus();
            currentindex = index;
        }
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

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (index == 4 && FuLiHomeApplication.getUser() == null) {
            index = 0;
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG + "MainActivity.onActivityResult.result" + requestCode);
        if (FuLiHomeApplication.getUser() != null) {
            L.e(TAG+"resultcode = "+requestCode);
            if (requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART) {
                index = 3;
            }
        }
    }
}
