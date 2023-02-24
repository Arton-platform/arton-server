package com.arton.backend.search.application.port.out;

import com.arton.backend.search.adapter.out.persistence.document.UserDocument;

import java.util.List;

public interface UserDocumentSavePort {
    UserDocument save(UserDocument userDocument);
    List<UserDocument> saveAll(List<UserDocument> userDocumentList);
}
