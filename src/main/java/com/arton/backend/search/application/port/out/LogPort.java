package com.arton.backend.search.application.port.out;

import com.arton.backend.search.application.data.SearchPageDto;
import com.arton.backend.search.application.data.SearchPageDtoV2;

public interface LogPort {
    SearchPageDto getRecentTop10Keywords();
    SearchPageDtoV2 getAdvancedTop10Keywords();
}
