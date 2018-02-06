package at.fhooe.mc.vis_ue4_krgd_hadodob_vers_1;

import android.os.Parcel;
import android.os.Parcelable;

public class BTDevice implements Parcelable {

    private final String mId;
    private final String mDisplayName;

    public BTDevice(String _id, String _displayName) {
        mId = _id;
        mDisplayName = _displayName;
    }

    private BTDevice(Parcel _in) {
        mId = _in.readString();
        mDisplayName = _in.readString();
    }

    public String getId() {
        return mId;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    @Override
    public String toString() {
        return mDisplayName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel _parcel, int _i) {
        _parcel.writeString(mId);
        _parcel.writeString(mDisplayName);
    }

    public static final Parcelable.Creator<BTDevice> CREATOR = new Parcelable.Creator<BTDevice>() {
        public BTDevice createFromParcel(Parcel in) {
            return new BTDevice(in);
        }

        public BTDevice[] newArray(int size) {
            return new BTDevice[size];
        }
    };
}