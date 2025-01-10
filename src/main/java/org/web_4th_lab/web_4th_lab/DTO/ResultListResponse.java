package org.web_4th_lab.web_4th_lab.DTO;

import org.web_4th_lab.web_4th_lab.entities.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultListResponse {
    private List<Result> results;

    public ResultListResponse(List<Result> results) {
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
