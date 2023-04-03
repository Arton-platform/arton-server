package com.arton.backend.search.application.port.out;

import com.arton.backend.search.adapter.out.persistence.document.UserDocument;
import com.arton.backend.user.domain.User;

import java.util.List;

public interface UserDocumentSavePort {
    UserDocument save(User user);
    List<UserDocument> saveAll(List<User> user);
}
