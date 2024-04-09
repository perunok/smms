package com.act.smms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.act.smms.model.Status;

public interface StatusRepo extends JpaRepository<Status, Long> {

}
