package org.web_4th_lab.web_4th_lab.DTO;

import org.web_4th_lab.web_4th_lab.entities.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultListResponse {
    private List<ResultResponse> results;

    public ResultListResponse(List<ResultResponse> results) {
        this.results = results;
    }

    public List<ResultResponse> getResults() {
        return results;
    }

    public void setResults(List<ResultResponse> results) {
        this.results = results;
    }
}
