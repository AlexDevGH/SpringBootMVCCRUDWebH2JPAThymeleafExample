package it.carrerajeans.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.carrerajeans.entities.Fattura;

@Repository
public interface IRepoFattura extends JpaRepository<Fattura, Long> {

}
