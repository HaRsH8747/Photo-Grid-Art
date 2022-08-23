package neo.photogridart.utils;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class MirrorMode {
    public int count;
    private Rect drawBitmapSrc = new Rect();
    public Matrix matrix1;
    public Matrix matrix2;
    public Matrix matrix3;
    public RectF rect1;
    public RectF rect2;
    public RectF rect3;
    public RectF rect4;
    public RectF rectTotalArea;
    private RectF srcRect;
    public int touchMode;

    public MirrorMode(int count2, RectF sR, RectF rect12, RectF rect22, Matrix matrix, int tM, RectF rta) {
        this.count = count2;
        this.srcRect = sR;
        this.srcRect.round(this.drawBitmapSrc);
        this.rect1 = rect12;
        this.rect2 = rect22;
        this.matrix1 = matrix;
        this.touchMode = tM;
        this.rectTotalArea = rta;
    }

    public void setSrcRect(RectF src) {
        this.srcRect.set(src);
        updateBitmapSrc();
    }

    public RectF getSrcRect() {
        return this.srcRect;
    }

    public Rect getDrawBitmapSrc() {
        return this.drawBitmapSrc;
    }

    public void updateBitmapSrc() {
        this.srcRect.round(this.drawBitmapSrc);
    }

    public MirrorMode(int c, RectF sR, RectF r1, RectF r2, RectF r3, RectF r4, Matrix m1, Matrix m2, Matrix m3, int tM, RectF rta) {
        this.count = c;
        this.srcRect = sR;
        this.srcRect.round(this.drawBitmapSrc);
        this.rect1 = r1;
        this.rect2 = r2;
        this.rect3 = r3;
        this.rect4 = r4;
        this.matrix1 = m1;
        this.matrix2 = m2;
        this.matrix3 = m3;
        this.touchMode = tM;
        this.rectTotalArea = rta;
    }
}
