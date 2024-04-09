package com.act.smms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.act.smms.enums.ISPConfigOption;
import com.act.smms.model.ISPConfig;
import java.util.List;

public interface ISPConfigRepo extends JpaRepository<ISPConfig, Long> {
    List<ISPConfig> findByOption(ISPConfigOption option);
}
