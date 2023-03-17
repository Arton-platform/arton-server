package com.arton.backend.search.application.data;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecentKeywordResponse {
    List<String> searchHistories = new ArrayList<>();

    @Builder
    public RecentKeywordResponse(List<String> searchHistories) {
        this.searchHistories = searchHistories;
    }
}
