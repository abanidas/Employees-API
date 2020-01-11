package com.abani.rest.employees.service;

import com.abani.rest.employees.model.MailUtil;

public interface MailUtilService {

    void save(MailUtil mailUtil);

    MailUtil getMailUtil();
}
