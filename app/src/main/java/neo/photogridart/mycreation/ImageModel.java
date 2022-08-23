package neo.photogridart.mycreation;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageModel implements Parcelable {
    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        public ImageModel createFromParcel(Parcel parcel) {
            return new ImageModel(parcel);
        }

        public ImageModel[] newArray(int i) {
            return new ImageModel[i];
        }
    };
    private String path;
    private String size;
    private String title;

    public int describeContents() {
        return 0;
    }

    public ImageModel(String str, String str2, String str3) {
        this.title = str;
        this.path = str2;
        this.size = str3;
    }

    protected ImageModel(Parcel parcel) {
        this.title = parcel.readString();
        this.path = parcel.readString();
        this.size = parcel.readString();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.path);
        parcel.writeString(this.size);
    }
}
