package com.example.coindesk.domain.repository;

import com.example.coindesk.domain.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    Optional<CurrencyEntity> findByCode(String code);

    Optional<CurrencyEntity> findById(long id);
}
