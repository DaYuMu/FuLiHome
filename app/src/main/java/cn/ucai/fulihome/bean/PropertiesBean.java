package cn.ucai.fulihome.bean;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class PropertiesBean {
    private Albums albums;
    private ColorBean colorBean;

    public PropertiesBean() {
    }

    public PropertiesBean(Albums albums, ColorBean colorBean) {
        this.albums = albums;
        this.colorBean = colorBean;
    }

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

    public ColorBean getColorBean() {
        return colorBean;
    }

    public void setColorBean(ColorBean colorBean) {
        this.colorBean = colorBean;
    }

    @Override
    public String toString() {
        return "PropertiesBean{" +
                "albums=" + albums +
                ", colorBean=" + colorBean +
                '}';
    }
}
