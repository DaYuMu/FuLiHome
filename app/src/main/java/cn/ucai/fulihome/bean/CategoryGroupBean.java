package cn.ucai.fulihome.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class CategoryGroupBean implements Serializable {


    /**
     * id : 345
     * parentId : 344
     * name : 热门
     * imageUrl : cat_image/256_1.png
     */

    private int id;
    private String name;
    private String imageUrl;

    public CategoryGroupBean() {
    }


    /**
     * 七、下载分类首页大类的数据
     * @param id
     * @param name
     * @param imageUrl
     */
    public CategoryGroupBean(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CategoryGroupBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }


}
