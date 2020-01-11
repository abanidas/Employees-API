package com.abani.rest.employees.repository;

import com.abani.rest.employees.model.MailUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailUtilRepository extends JpaRepository<MailUtil, String> {
}
