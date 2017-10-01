package com.android.filter;

/**
 * Created by gabriellim on 30092017--.
 */

public class University implements Comparable<University> {

    private String mName;
    private double mRating;

    public University(String name, double rating) {
        super();
        this.mName = name;
        this.mRating = rating;
    }

    public String getUniversityName() {
        return this.mName;
    }

    public double getUniversityRating() {
        return this.mRating;
    }

    public int compareTo(University compareUniversity) {
        double compareRating = compareUniversity.getUniversityRating();
        return Double.compare(compareRating, this.mRating);
    }

}
