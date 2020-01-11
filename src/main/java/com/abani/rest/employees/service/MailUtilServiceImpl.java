package com.abani.rest.employees.service;

import com.abani.rest.employees.model.MailUtil;
import com.abani.rest.employees.repository.MailUtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailUtilServiceImpl implements MailUtilService {

    @Autowired
    MailUtilRepository mailRepository;

    @Override
    public void save(MailUtil mailUtil) {
        mailRepository.save(mailUtil);
    }

    @Override
    public MailUtil getMailUtil() {
        List<MailUtil> mailUtils = mailRepository.findAll();
        return mailUtils.isEmpty() ? null : mailUtils.get(0);
    }
}
