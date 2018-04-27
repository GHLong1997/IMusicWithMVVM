package com.example.along.scmusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("kind")
    @Expose
    private String mKind;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("duration")
    @Expose
    private Integer mDuration;
    @SerializedName("stream_url")
    @Expose
    private String mStreamUrl;
    @SerializedName("uri")
    @Expose
    private String mUri;
    @SerializedName("user_id")
    @Expose
    private Integer mUserId;
    @SerializedName("artwork_url")
    @Expose
    private String mArtworkUrl;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("downloadable")
    @Expose
    private Boolean mDownloadable;
    @SerializedName("genre")
    @Expose
    private String mGenre;
    @SerializedName("label_id")
    @Expose
    private Object mLabelId;
    @SerializedName("streamable")
    @Expose
    private Boolean mStreamable;
    @SerializedName("user")
    @Expose
    private Artist mUser;

    public Track() {
    }

    protected Track(Parcel in) {
        if (in.readByte() != 0) {
            mId = in.readInt();
        }
        mKind = in.readString();
        mTitle = in.readString();
        if (in.readByte() != 0) {
            mDuration = in.readInt();
        }
        mStreamUrl = in.readString();
        mUri = in.readString();
        if (in.readByte() != 0) {
            mUserId = in.readInt();
        }
        mArtworkUrl = in.readString();
        mDescription = in.readString();
        byte tmpMDownloadable = in.readByte();
        mDownloadable = tmpMDownloadable == 0 ? null : tmpMDownloadable == 1;
        mGenre = in.readString();
        byte tmpMStreamable = in.readByte();
        mStreamable = tmpMStreamable == 0 ? null : tmpMStreamable == 1;
        mUser = in.readParcelable(Artist.class.getClassLoader());
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId != null) {
            dest.writeByte((byte) 1);
            dest.writeInt(mId);
        }
        dest.writeString(mKind);
        dest.writeString(mTitle);
        if (mDuration != null) {
            dest.writeByte((byte) 1);
            dest.writeInt(mDuration);
        }
        dest.writeString(mStreamUrl);
        dest.writeString(mUri);
        if (mUserId != null) {
            dest.writeByte((byte) 1);
            dest.writeInt(mUserId);
        }
        dest.writeString(mArtworkUrl);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mDownloadable == null ? 0 : mDownloadable ? 1 : 2));
        dest.writeString(mGenre);
        dest.writeByte((byte) (mStreamable == null ? 0 : mStreamable ? 1 : 2));
        dest.writeParcelable(mUser, flags);
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Integer getDuration() {
        return mDuration;
    }

    public void setDuration(Integer duration) {
        mDuration = duration;
    }

    public String getStreamUrl() {
        return mStreamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        mStreamUrl = streamUrl;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public Integer getUserId() {
        return mUserId;
    }

    public void setUserId(Integer userId) {
        mUserId = userId;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Boolean getDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public Object getLabelId() {
        return mLabelId;
    }

    public void setLabelId(Object labelId) {
        mLabelId = labelId;
    }

    public Boolean getStreamable() {
        return mStreamable;
    }

    public void setStreamable(Boolean streamable) {
        mStreamable = streamable;
    }

    public Artist getUser() {
        return mUser;
    }

    public void setUser(Artist user) {
        mUser = user;
    }
}
