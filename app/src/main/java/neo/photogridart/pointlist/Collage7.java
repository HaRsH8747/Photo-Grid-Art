package neo.photogridart.pointlist;

import android.graphics.PointF;
import java.util.ArrayList;

public class Collage7 extends Collage {
    public static int shapeCount = 7;

    public Collage7(int i, int j) {
        this.collageLayoutList = new ArrayList();
        PointF[] pointFArr = new PointF[4];
        ArrayList obj = new ArrayList();
        obj.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f)});
        obj.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f)});
        obj.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f)});
        obj.add(new PointF[]{new PointF(0.6f * ((float) i), 0.4166667f * ((float) j)), new PointF(0.6f * ((float) i), ((float) j) * 0.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.3333333f, 0.4166667f * ((float) j))});
        obj.add(new PointF[]{new PointF(0.6f * ((float) i), 0.4166667f * ((float) j)), new PointF(((float) i) * 1.0f, 0.4166667f * ((float) j)), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(0.6f * ((float) i), ((float) j) * 0.0f)});
        obj.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f), new PointF(0.7333333f * ((float) i), ((float) j) * 1.0f), new PointF(0.7333333f * ((float) i), 0.4166667f * ((float) j)), new PointF(((float) i) * 0.3333333f, 0.4166667f * ((float) j))});
        obj.add(new PointF[]{new PointF(0.7333333f * ((float) i), ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, 0.4166667f * ((float) j)), new PointF(0.7333333f * ((float) i), 0.4166667f * ((float) j))});
        this.collageLayoutList.add(new CollageLayout(obj));
        ArrayList obj2 = new ArrayList();
        obj2.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(0.25f * ((float) i), ((float) j) * 1.0f), new PointF(0.25f * ((float) i), ((float) j) * 0.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f)});
        obj2.add(new PointF[]{new PointF(0.5f * ((float) i), ((float) j) * 0.0f), new PointF(0.25f * ((float) i), ((float) j) * 0.0f), new PointF(0.25f * ((float) i), 0.5f * ((float) j)), new PointF(0.5f * ((float) i), 0.5f * ((float) j))});
        obj2.add(new PointF[]{new PointF(0.75f * ((float) i), ((float) j) * 0.0f), new PointF(0.5f * ((float) i), ((float) j) * 0.0f), new PointF(0.5f * ((float) i), 0.5f * ((float) j)), new PointF(0.75f * ((float) i), 0.5f * ((float) j))});
        obj2.add(new PointF[]{new PointF(0.75f * ((float) i), ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, 0.5f * ((float) j)), new PointF(0.75f * ((float) i), 0.5f * ((float) j))});
        obj2.add(new PointF[]{new PointF(0.5f * ((float) i), 0.5f * ((float) j)), new PointF(0.25f * ((float) i), 0.5f * ((float) j)), new PointF(0.25f * ((float) i), ((float) j) * 1.0f), new PointF(0.5f * ((float) i), ((float) j) * 1.0f)});
        obj2.add(new PointF[]{new PointF(0.5f * ((float) i), 0.5f * ((float) j)), new PointF(0.75f * ((float) i), 0.5f * ((float) j)), new PointF(0.75f * ((float) i), ((float) j) * 1.0f), new PointF(0.5f * ((float) i), ((float) j) * 1.0f)});
        obj2.add(new PointF[]{new PointF(0.75f * ((float) i), 0.5f * ((float) j)), new PointF(((float) i) * 1.0f, 0.5f * ((float) j)), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f), new PointF(0.75f * ((float) i), ((float) j) * 1.0f)});
        this.collageLayoutList.add(new CollageLayout(obj2));
        ArrayList obj3 = new ArrayList();
        obj3.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f)});
        obj3.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f)});
        obj3.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f)});
        obj3.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f)});
        obj3.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f)});
        obj3.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f)});
        obj3.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f)});
        this.collageLayoutList.add(new CollageLayout(obj3));
        ArrayList obj4 = new ArrayList();
        obj4.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f)});
        obj4.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f)});
        obj4.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f)});
        obj4.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f)});
        obj4.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f)});
        obj4.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f)});
        obj4.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f)});
        this.collageLayoutList.add(new CollageLayout(obj4));
        ArrayList obj5 = new ArrayList();
        obj5.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f)});
        obj5.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f)});
        obj5.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f)});
        obj5.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f)});
        obj5.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f)});
        obj5.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f)});
        obj5.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f)});
        this.collageLayoutList.add(new CollageLayout(obj5));
        ArrayList obj6 = new ArrayList();
        obj6.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(0.5f * ((float) i), ((float) j) * 0.3333333f), new PointF(0.5f * ((float) i), ((float) j) * 0.6666667f)});
        obj6.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f)});
        obj6.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f)});
        obj6.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f)});
        obj6.add(new PointF[]{new PointF(0.5f * ((float) i), ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(0.5f * ((float) i), ((float) j) * 0.3333333f)});
        obj6.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(0.5f * ((float) i), ((float) j) * 0.6666667f), new PointF(0.5f * ((float) i), ((float) j) * 1.0f)});
        obj6.add(new PointF[]{new PointF(0.5f * ((float) i), ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(0.5f * ((float) i), ((float) j) * 0.6666667f)});
        this.collageLayoutList.add(new CollageLayout(obj6));
        ArrayList obj7 = new ArrayList();
        obj7.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f)});
        obj7.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f)});
        obj7.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f)});
        obj7.add(new PointF[]{new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, 0.25f * ((float) j)), new PointF(((float) i) * 1.0f, 0.25f * ((float) j))});
        obj7.add(new PointF[]{new PointF(((float) i) * 1.0f, 0.25f * ((float) j)), new PointF(((float) i) * 1.0f, 0.5f * ((float) j)), new PointF(((float) i) * 0.6666667f, 0.5f * ((float) j)), new PointF(((float) i) * 0.6666667f, 0.25f * ((float) j))});
        obj7.add(new PointF[]{new PointF(((float) i) * 1.0f, 0.5f * ((float) j)), new PointF(((float) i) * 0.6666667f, 0.5f * ((float) j)), new PointF(((float) i) * 0.6666667f, 0.75f * ((float) j)), new PointF(((float) i) * 1.0f, 0.75f * ((float) j))});
        obj7.add(new PointF[]{new PointF(((float) i) * 1.0f, 0.75f * ((float) j)), new PointF(((float) i) * 0.6666667f, 0.75f * ((float) j)), new PointF(((float) i) * 0.6666667f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f)});
        this.collageLayoutList.add(new CollageLayout(obj7));
        ArrayList obj8 = new ArrayList();
        obj8.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f)});
        obj8.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f)});
        obj8.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f)});
        obj8.add(new PointF[]{new PointF(((float) i) * 0.3333333f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f)});
        obj8.add(new PointF[]{new PointF(((float) i) * 0.6666667f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.6666667f, ((float) j) * 0.3333333f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f)});
        obj8.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.0f, ((float) j) * 0.6666667f)});
        obj8.add(new PointF[]{new PointF(((float) i) * 1.0f, ((float) j) * 1.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 0.6666667f), new PointF(((float) i) * 0.3333333f, ((float) j) * 1.0f)});
        this.collageLayoutList.add(new CollageLayout(obj8));
        ArrayList obj9 = new ArrayList();
        obj9.add(new PointF[]{new PointF(((float) i) * 0.0f, 0.75f * ((float) j)), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(0.2916667f * ((float) i), ((float) j) * 0.0f), new PointF(0.2916667f * ((float) i), 0.75f * ((float) j))});
        obj9.add(new PointF[]{new PointF(0.2916667f * ((float) i), 0.75f * ((float) j)), new PointF(0.2916667f * ((float) i), ((float) j) * 0.0f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.0f), new PointF(0.5833333f * ((float) i), 0.75f * ((float) j))});
        obj9.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, 0.75f * ((float) j)), new PointF(0.2916667f * ((float) i), 0.75f * ((float) j)), new PointF(0.2916667f * ((float) i), ((float) j) * 1.0f)});
        obj9.add(new PointF[]{new PointF(0.2916667f * ((float) i), ((float) j) * 1.0f), new PointF(0.2916667f * ((float) i), 0.75f * ((float) j)), new PointF(0.5833333f * ((float) i), 0.75f * ((float) j)), new PointF(0.5833333f * ((float) i), ((float) j) * 1.0f)});
        obj9.add(new PointF[]{new PointF(0.5833333f * ((float) i), ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.3333333f)});
        obj9.add(new PointF[]{new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.3333333f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f)});
        obj9.add(new PointF[]{new PointF(0.5833333f * ((float) i), ((float) j) * 1.0f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f)});
        this.collageLayoutList.add(new CollageLayout(obj9));
        ArrayList obj10 = new ArrayList();
        obj10.add(new PointF[]{new PointF(((float) i) * 0.0f, 0.25f * ((float) j)), new PointF(((float) i) * 0.0f, ((float) j) * 0.0f), new PointF(0.2916667f * ((float) i), ((float) j) * 0.0f), new PointF(0.2916667f * ((float) i), 0.25f * ((float) j))});
        obj10.add(new PointF[]{new PointF(0.2916667f * ((float) i), 0.25f * ((float) j)), new PointF(0.2916667f * ((float) i), ((float) j) * 0.0f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.0f), new PointF(0.5833333f * ((float) i), 0.25f * ((float) j))});
        obj10.add(new PointF[]{new PointF(((float) i) * 0.0f, ((float) j) * 1.0f), new PointF(((float) i) * 0.0f, 0.25f * ((float) j)), new PointF(0.2916667f * ((float) i), 0.25f * ((float) j)), new PointF(0.2916667f * ((float) i), ((float) j) * 1.0f)});
        obj10.add(new PointF[]{new PointF(0.2916667f * ((float) i), ((float) j) * 1.0f), new PointF(0.2916667f * ((float) i), 0.25f * ((float) j)), new PointF(0.5833333f * ((float) i), 0.25f * ((float) j)), new PointF(0.5833333f * ((float) i), ((float) j) * 1.0f)});
        obj10.add(new PointF[]{new PointF(0.5833333f * ((float) i), ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.0f), new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.3333333f)});
        obj10.add(new PointF[]{new PointF(((float) i) * 1.0f, ((float) j) * 0.3333333f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.3333333f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f)});
        obj10.add(new PointF[]{new PointF(0.5833333f * ((float) i), ((float) j) * 1.0f), new PointF(0.5833333f * ((float) i), ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 0.6666667f), new PointF(((float) i) * 1.0f, ((float) j) * 1.0f)});
        this.collageLayoutList.add(new CollageLayout(obj10));
    }
}
