package neo.photogridart.stickers;

import android.graphics.Matrix;
import java.io.Serializable;

public class StickerPropertyModel implements Serializable {
    private static final long serialVersionUID = 3800737478616389410L;
    private float degree;
    private int horizonMirror;
    private boolean isActive;
    private int order;
    private float scaling;
    private long stickerId;
    private Matrix stickerMatrix;
    private int stickerPath;
    private String stickerURL;
    private String text;
    private float xLocation;
    private float yLocation;

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive2) {
        this.isActive = isActive2;
    }

    public Matrix getStickerMatrix() {
        return this.stickerMatrix;
    }

    public void setStickerMatrix(Matrix stickerMatrix2) {
        this.stickerMatrix = stickerMatrix2;
    }

    public int getHorizonMirror() {
        return this.horizonMirror;
    }

    public void setHorizonMirror(int horizonMirror2) {
        this.horizonMirror = horizonMirror2;
    }

    public String getStickerURL() {
        return this.stickerURL;
    }

    public void setStickerURL(String stickerURL2) {
        this.stickerURL = stickerURL2;
    }

    public int getStickerPath() {
        return this.stickerPath;
    }

    public void setStickerPath(int stickerPath2) {
        this.stickerPath = stickerPath2;
    }

    public long getStickerId() {
        return this.stickerId;
    }

    public void setStickerId(long stickerId2) {
        this.stickerId = stickerId2;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public float getxLocation() {
        return this.xLocation;
    }

    public void setxLocation(float xLocation2) {
        this.xLocation = xLocation2;
    }

    public float getyLocation() {
        return this.yLocation;
    }

    public void setyLocation(float yLocation2) {
        this.yLocation = yLocation2;
    }

    public float getDegree() {
        return this.degree;
    }

    public void setDegree(float degree2) {
        this.degree = degree2;
    }

    public float getScaling() {
        return this.scaling;
    }

    public void setScaling(float scaling2) {
        this.scaling = scaling2;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order2) {
        this.order = order2;
    }
}
