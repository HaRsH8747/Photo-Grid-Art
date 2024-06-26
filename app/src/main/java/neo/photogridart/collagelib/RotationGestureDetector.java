package neo.photogridart.collagelib;

import android.view.MotionEvent;

public class RotationGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private float fX;
    private float fY;
    private float mAngle;
    private OnRotationGestureListener mListener;
    private int ptrID1 = -1;
    private int ptrID2 = -1;
    private float sX;
    private float sY;

    public interface OnRotationGestureListener {
        void OnRotation(RotationGestureDetector rotationGestureDetector);
    }

    public float getAngle() {
        return this.mAngle;
    }

    public RotationGestureDetector(OnRotationGestureListener listener) {
        this.mListener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                this.ptrID1 = event.getPointerId(event.getActionIndex());
                break;
            case 1:
                this.ptrID1 = -1;
                break;
            case 2:
                if (!(this.ptrID1 == -1 || this.ptrID2 == -1)) {
                    int index1Move = event.findPointerIndex(this.ptrID1);
                    int index2Move = event.findPointerIndex(this.ptrID2);
                    int pointerCountMove = event.getPointerCount();
                    if (index1Move >= 0 && index1Move < pointerCountMove && index2Move >= 0 && index2Move < pointerCountMove) {
                        float nsX = event.getX(event.findPointerIndex(this.ptrID1));
                        float nsY = event.getY(event.findPointerIndex(this.ptrID1));
                        this.mAngle = angleBetweenLines(this.fX, this.fY, this.sX, this.sY, event.getX(event.findPointerIndex(this.ptrID2)), event.getY(event.findPointerIndex(this.ptrID2)), nsX, nsY);
                        if (this.mListener != null) {
                            this.mListener.OnRotation(this);
                            break;
                        }
                    }
                }
                break;
            case 3:
                this.ptrID1 = -1;
                this.ptrID2 = -1;
                break;
            case 5:
                this.ptrID2 = event.getPointerId(event.getActionIndex());
                int index1 = event.findPointerIndex(this.ptrID1);
                int index2 = event.findPointerIndex(this.ptrID2);
                int pointerCount = event.getPointerCount();
                if (index1 >= 0 && index1 < pointerCount && index2 >= 0 && index2 < pointerCount) {
                    this.sX = event.getX(index1);
                    this.sY = event.getY(index1);
                    this.fX = event.getX(index2);
                    this.fY = event.getY(index2);
                    break;
                }
            case 6:
                this.ptrID2 = -1;
                break;
        }
        return true;
    }

    private float angleBetweenLines(float fX2, float fY2, float sX2, float sY2, float nfX, float nfY, float nsX, float nsY) {
        float angle = ((float) Math.toDegrees((double) (((float) Math.atan2((double) (fY2 - sY2), (double) (fX2 - sX2))) - ((float) Math.atan2((double) (nfY - nsY), (double) (nfX - nsX)))))) % 360.0f;
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        if (angle > 180.0f) {
            return angle - 360.0f;
        }
        return angle;
    }
}
