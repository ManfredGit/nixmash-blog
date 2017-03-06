package com.nixmash.blog.jsoup.base;

import java.io.Serializable;

/**
 * Created by daveburke on 5/22/16.
 */
@SuppressWarnings("WeakerAccess")
public class JsoupImage implements Serializable{

    private static final long serialVersionUID = 1772136151299547608L;

    public String src;
    public String alt;
    public Integer height;
    public Integer width;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "JsoupImage{" +
                "src='" + src + '\'' +
                ", alt='" + alt + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
