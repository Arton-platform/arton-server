package com.arton.backend.mail.adapter.out.persistence.repository;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.mail.adapter.out.persistence.entity.MailEntity;
import com.arton.backend.mail.adapter.out.persistence.mapper.MailMapper;
import com.arton.backend.mail.application.port.out.MailDeletePort;
import com.arton.backend.mail.application.port.out.MailPort;
import com.arton.backend.mail.application.port.out.MailSavePort;
import com.arton.backend.mail.domain.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.arton.backend.mail.adapter.out.persistence.mapper.MailMapper.toDomain;
import static com.arton.backend.mail.adapter.out.persistence.mapper.MailMapper.toEntity;

@Repository
@RequiredArgsConstructor
public class MailRepositoryAdapter implements MailSavePort, MailDeletePort, MailPort {
    private final MailRepository mailRepository;
    @Override
    public void deleteMailById(Long id) {
        mailRepository.deleteById(id);
    }

    @Override
    public Mail save(Mail mail) {
        return MailMapper.toDomain(mailRepository.save(toEntity(mail)));
    }

    @Override
    public List<Mail> saveAll(List<Mail> mailList) {
        List<MailEntity> mailEntities = Optional.ofNullable(mailList).orElseGet(Collections::emptyList)
                .stream().map(MailMapper::toEntity).collect(Collectors.toList());
        return mailRepository.saveAll(mailEntities).stream().map(MailMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Mail findMailById(Long id) {
        MailEntity mailEntity = mailRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MAIL_NOT_FOUND.getMessage(), ErrorCode.MAIL_NOT_FOUND));
        return toDomain(mailEntity);
    }

    @Override
    public List<Mail> findAll() {
        return Optional.ofNullable(mailRepository.findAll()).orElseGet(Collections::emptyList)
                .stream()
                .map(MailMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Mail> findAllWithPaging(Pageable pageable) {
        return Optional.ofNullable(mailRepository.findAll(pageable)).orElseGet(Page::empty).map(MailMapper::toDomain);
    }
}
