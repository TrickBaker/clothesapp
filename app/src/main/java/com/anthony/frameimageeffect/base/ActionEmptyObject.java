package com.anthony.frameimageeffect.base;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ntnhuy on 04/03/2016.
 */
public class ActionEmptyObject extends BaseObject implements Parcelable{
    private int objectType;
    private boolean isFlags;
    private Bundle bundle;
    private String raw;
    private Object object;

    public ActionEmptyObject(int objectType) {
        this.objectType = objectType;
    }

    public ActionEmptyObject() {
    }

    public ActionEmptyObject setObjectType(int objectType) {
        this.objectType = objectType;
        return this;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public ActionEmptyObject setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    @Override
    public int getObjectType() {
        return objectType;
    }

    public boolean isFlags() {
        return isFlags;
    }

    public ActionEmptyObject setFlags(boolean flags) {
        isFlags = flags;
        return this;
    }

    public String getRaw() {
        return raw;
    }

    public ActionEmptyObject setRaw(String raw) {
        this.raw = raw;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.objectType);
        dest.writeByte(this.isFlags ? (byte) 1 : (byte) 0);
        dest.writeBundle(this.bundle);
        dest.writeString(this.raw);
    }

    protected ActionEmptyObject(Parcel in) {
        this.objectType = in.readInt();
        this.isFlags = in.readByte() != 0;
        this.bundle = in.readBundle();
        this.raw = in.readString();
    }

    public static final Creator<ActionEmptyObject> CREATOR = new Creator<ActionEmptyObject>() {
        @Override
        public ActionEmptyObject createFromParcel(Parcel source) {
            return new ActionEmptyObject(source);
        }

        @Override
        public ActionEmptyObject[] newArray(int size) {
            return new ActionEmptyObject[size];
        }
    };

    public ActionEmptyObject setTag(Object object) {
        this.object = object;
        return this;
    }

    public Object getTag() {
        return this.object;
    }
}
