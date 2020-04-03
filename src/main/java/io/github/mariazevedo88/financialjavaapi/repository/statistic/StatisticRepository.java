package io.github.mariazevedo88.financialjavaapi.repository.statistic;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mariazevedo88.financialjavaapi.model.v1.statistic.Statistic;

/**
 * Interface that implements the Statistic Repository, with JPA CRUD methods.
 *  
 * @author Mariana Azevedo
 * @since 28/03/2020
 */
public interface StatisticRepository extends JpaRepository<Statistic, Long>{

}
