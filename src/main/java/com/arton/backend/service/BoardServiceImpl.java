package com.arton.backend.service;

import com.arton.backend.entity.board.FAQ;
import com.arton.backend.repository.board.FAQRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{

    FAQRepository faqRepository;

    @Override
    public List<FAQ> faqList(){
        return faqRepository.findAllByOrderByCreatedDateDesc().orElse(new ArrayList<FAQ>());
    }
}
