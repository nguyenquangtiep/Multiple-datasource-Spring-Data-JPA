package com.mark.springbootmultipledatasource.clazz.repository;

import com.mark.springbootmultipledatasource.clazz.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {
}
