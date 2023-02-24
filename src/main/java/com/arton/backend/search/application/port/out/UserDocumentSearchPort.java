package com.arton.backend.search.application.port.out;

import com.arton.backend.administer.mail.application.data.AdminMailSearchDto;
import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;

public interface UserDocumentSearchPort {
    SearchPage<UserDocument> searchUserForMailing(AdminMailSearchDto searchDto, Pageable pageable);
}
