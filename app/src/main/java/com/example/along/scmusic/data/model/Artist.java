package com.example.along.scmusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist implements Parcelable {

    @SerializedName("avatar_url")
    @Expose
    private String mAvatarUrl;
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("kind")
    @Expose
    private String mKind;
    @SerializedName("permalink_url")
    @Expose
    private String mPermalinkUrl;
    @SerializedName("uri")
    @Expose
    private String mUri;
    @SerializedName("username")
    @Expose
    private String mUsername;
    @SerializedName("permalink")
    @Expose
    private String mPermalink;
    @SerializedName("last_modified")
    @Expose
    private String mLastModified;

    public Artist() {
    }

    protected Artist(Parcel in) {
        mAvatarUrl = in.readString();
        if (in.readByte() != 0) {
            mId = in.readInt();
        }
        mKind = in.readString();
        mPermalinkUrl = in.readString();
        mUri = in.readString();
        mUsername = in.readString();
        mPermalink = in.readString();
        mLastModified = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAvatarUrl);
        if (mId != null) {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mId);
        }
        parcel.writeString(mKind);
        parcel.writeString(mPermalinkUrl);
        parcel.writeString(mUri);
        parcel.writeString(mUsername);
        parcel.writeString(mPermalink);
        parcel.writeString(mLastModified);
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.mAvatarUrl = avatarUrl;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        this.mKind = kind;
    }

    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        this.mPermalinkUrl = permalinkUrl;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        this.mUri = uri;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(String permalink) {
        this.mPermalink = permalink;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModified(String lastModified) {
        this.mLastModified = lastModified;
    }
}
