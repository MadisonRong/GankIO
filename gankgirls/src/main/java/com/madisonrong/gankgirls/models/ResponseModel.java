package com.madisonrong.gankgirls.models;

import java.util.List;

/**
 * Created by MadisonRong on 6/28/16.
 */
public class ResponseModel {

    private boolean error;
    private List<DataModel> results;

    public ResponseModel(boolean error, List<DataModel> results) {
        this.error = error;
        this.results = results;
    }

    public ResponseModel() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<DataModel> getResults() {
        return results;
    }

    public void setResults(List<DataModel> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
