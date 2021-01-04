package com.del.moviecatalogue2.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity (tableName = "movie_database")
public class Movie implements Parcelable {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "release")
    private String release;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "photo")
    private String photo;
    @ColumnInfo(name = "language")
    private String language;
    @ColumnInfo(name = "popularity")
    private double popularity;
    @ColumnInfo(name = "rating")
    private double rating;
    @ColumnInfo(name = "adult")
    private boolean adult;
    @ColumnInfo(name = "type")
    private boolean isMovie;

    public Movie(){
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setIsMovie(boolean isMovie){
        this.isMovie = isMovie;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease() {
        return release;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getRating() {
        return rating;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isMovie(){
        return isMovie;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        release = in.readString();
        description = in.readString();
        photo = in.readString();
        popularity = in.readDouble();
        rating = in.readDouble();
        language = in.readString();
        adult = in.readByte() != 0;
        isMovie = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(release);
        parcel.writeString(description);
        parcel.writeString(photo);
        parcel.writeDouble(popularity);
        parcel.writeDouble(rating);
        parcel.writeString(language);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeByte((byte) (isMovie ? 1 : 0));
    }
}
