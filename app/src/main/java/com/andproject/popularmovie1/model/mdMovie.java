package com.andproject.popularmovie1.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 20180513.
 */

public class mdMovie implements Parcelable {
    private int inId;
    private String stTitle;
    private String stOrgTitle;
    private String stOverview;
    private String stOrgLan;
    private String stReleaseDate;
    private int intVoteCount;
    private float flVoteAvg;
    private float flPopular;
    private String stPosterPath;
    private String stbackdropPath;
    private boolean bolVideo;
    private boolean bolAdult;

    public mdMovie() {}

    public int getId() {
        return inId;
    }
    public void setId(int inId) {
        this.inId = inId;
    }
    public String getTitle() {
        return stTitle;
    }
    public void setTitle(String stTitle) {
        this.stTitle = stTitle;
    }
    public String getOriginalTitle() {
        return stOrgTitle;
    }
    public void setOriginalTitle(String stOrgTitle) {
        this.stOrgTitle = stOrgTitle;
    }
    public String getOverview() {
        return stOverview;
    }
    public void setOverview(String stOverview) {
        this.stOverview = stOverview;
    }
    public String getOriginalLanguage() {
        return stOrgLan;
    }

    public void setOriginalLanguage(String stOrgLan) {
        this.stOrgLan = stOrgLan;
    }

    public String getReleaseDate() {
        return stReleaseDate;
    }
    public void setReleaseDate(String stReleaseDate) {
        this.stReleaseDate = stReleaseDate;
    }
    public int getVoteCount() {
        return intVoteCount;
    }
    public void setVoteCount(int intVoteCount) {
        this.intVoteCount = intVoteCount;
    }
    public float getVoteAverage() {
        return flVoteAvg;
    }
    public void setVoteAverage(float flVoteAvg) {
        this.flVoteAvg = flVoteAvg;
    }
    public float getPopularity() {
        return flPopular;
    }
    public void setPopularity(float flPopular) {
        this.flPopular = flPopular;
    }
    public String getPosterPath() {
        return stPosterPath;
    }

    public void setPosterPath(String stPosterPath) {
        this.stPosterPath = "http://image.tmdb.org/t/p/w185" + stPosterPath;
    }

    public String getBackdropPath() {
        return stbackdropPath;
    }

    public void setBackdropPath(String stbackdropPath) {
        this.stbackdropPath = "http://image.tmdb.org/t/p/w342" + stbackdropPath;
    }

    public boolean isVideo() {
        return bolVideo;
    }
    public void setVideo(boolean bolVideo) {
        this.bolVideo = bolVideo;
    }
    public boolean isAdult() {
        return bolAdult;
    }
    public void setAdult(boolean bolAdult) {
        this.bolAdult = bolAdult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.inId);
        dest.writeString(this.stTitle);
        dest.writeString(this.stOrgTitle);
        dest.writeString(this.stOverview);
        dest.writeString(this.stOrgLan);
        dest.writeString(this.stReleaseDate);
        dest.writeInt(this.intVoteCount);
        dest.writeFloat(this.flVoteAvg);
        dest.writeFloat(this.flPopular);
        dest.writeString(this.stPosterPath);
        dest.writeString(this.stbackdropPath);
        dest.writeByte(this.bolVideo ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bolAdult ? (byte) 1 : (byte) 0);
    }

    protected mdMovie(Parcel in) {
        this.inId = in.readInt();
        this.stTitle = in.readString();
        this.stOrgTitle = in.readString();
        this.stOverview = in.readString();
        this.stOrgLan = in.readString();
        this.stReleaseDate = in.readString();
        this.intVoteCount = in.readInt();
        this.flVoteAvg = in.readFloat();
        this.flPopular = in.readFloat();
        this.stPosterPath = in.readString();
        this.stbackdropPath = in.readString();
        this.bolVideo = in.readByte() != 0;
        this.bolAdult = in.readByte() != 0;
    }

    public static final Parcelable.Creator<mdMovie> CREATOR = new Parcelable.Creator<mdMovie>() {
        @Override
        public mdMovie createFromParcel(Parcel source) {
            return new mdMovie(source);
        }

        @Override
        public mdMovie[] newArray(int size) {
            return new mdMovie[size];
        }
    };
}
