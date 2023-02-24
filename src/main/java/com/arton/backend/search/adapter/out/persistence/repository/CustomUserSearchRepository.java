package com.arton.backend.search.adapter.out.persistence.repository;

import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import org.springframework.data.elasticsearch.core.SearchPage;

public interface CustomUserSearchRepository {
    SearchPage<UserDocument> searchUserForMailing();
}
