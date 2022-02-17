package com.example.haritagedemo;

import android.content.Context;

import com.example.haritagedemo.FieldNearbySitesLocation;

import java.util.ArrayList;

/**
 * Created by DAVID BELOOSESKY on 28/04/2014
 */
public class ContentManager {
    public static final String LOG_TAG = ContentManager.class.getSimpleName();
    private static ContentManager msInstance;
    private final Context mContext;
    private ArrayList<FieldNearbySitesLocation> mPredictionList = new ArrayList<>();

    private ContentManager(Context context) {
        mContext = context;
    }

    public static ContentManager init(Context context) {
        if (msInstance == null) {
            msInstance = new ContentManager(context);
        }

        return msInstance;
    }

    public static ContentManager getInstance() {
        return msInstance;
    }

    public ArrayList<FieldNearbySitesLocation> getPredictionList() {
        return mPredictionList;
    }

    public void setPredictionList(ArrayList<FieldNearbySitesLocation> predictionList) {
        mPredictionList = predictionList;
    }

    public ArrayList<FieldNearbySitesLocation> getPredictionDescriptionList() {
        return new ArrayList<>(mPredictionList);
    }

}
