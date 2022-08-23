package neo.photogridart.mycreation;

import java.util.Comparator;

class CustomComparator implements Comparator<ImageModel> {
    @Override
    public int compare(ImageModel o1, ImageModel o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
