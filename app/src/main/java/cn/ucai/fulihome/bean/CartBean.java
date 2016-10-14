package cn.ucai.fulihome.bean;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class CartBean {

    /**
     * id : 35
     * userName : a952702
     * goodsId : 7677
     * goods : null
     * count : 2
     * isChecked : false
     * checked : false
     */

    private int id;
    private String userName;
    private int goodsId;
    private Object goods;
    private int count;
    private boolean isChecked;
    private boolean checked;

    public CartBean() {
    }

    public CartBean(int id, String userName, int goodsId, Object goods,
                    int count, boolean isChecked, boolean checked) {
        this.id = id;
        this.userName = userName;
        this.goodsId = goodsId;
        this.goods = goods;
        this.count = count;
        this.isChecked = isChecked;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Object getGoods() {
        return goods;
    }

    public void setGoods(Object goods) {
        this.goods = goods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", goodsId=" + goodsId +
                ", goods=" + goods +
                ", count=" + count +
                ", isChecked=" + isChecked +
                ", checked=" + checked +
                '}';
    }
}