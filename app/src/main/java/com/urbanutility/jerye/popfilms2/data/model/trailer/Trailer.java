package com.urbanutility.jerye.popfilms2.data.model.trailer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailer implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    public final static Parcelable.Creator<Trailer> CREATOR = new Creator<Trailer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return (new Trailer[size]);
        }

    };

    protected Trailer(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (com.urbanutility.jerye.popfilms2.data.model.trailer.Result.class.getClassLoader()));
    }

    public Trailer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

}


