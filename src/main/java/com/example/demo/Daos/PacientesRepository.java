package com.example.demo.Daos;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Paciente;

@Repository
public interface PacientesRepository<T, ID extends Serializable> extends JpaRepository<Paciente, Long> {

}
