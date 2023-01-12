package com.arton.backend.board.application.service;

import com.arton.backend.board.adapter.out.persistence.faq.FAQ;

import java.util.List;

public interface BoardService {
    public List<FAQ> faqList();
}
