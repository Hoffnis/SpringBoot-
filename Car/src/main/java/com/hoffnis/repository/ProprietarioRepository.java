package com.hoffnis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hoffnis.model.Proprietario;

@Repository
@Transactional
public interface ProprietarioRepository extends CrudRepository<Proprietario, Long> {
	@Query("select p from Proprietario p where p.pessoa.id = ?1")
	public List<Proprietario> getProprietario(Long pessoaid);
}