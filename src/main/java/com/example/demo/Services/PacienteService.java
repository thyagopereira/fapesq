package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Daos.PacientesRepository;
import com.example.demo.Entity.Paciente;

@Service
public class PacienteService {

	private PacientesRepository<Paciente, Long> pacientesDAO;

	public PacienteService(PacientesRepository<Paciente, Long> pacientesDAO) {
		super();
		this.pacientesDAO = pacientesDAO;
	}

	public Paciente addPaciente(Paciente paciente) {
		return pacientesDAO.save(paciente);

	}

	public List<Paciente> getPacientes() {
		return pacientesDAO.findAll();
	}

	public Optional<Paciente> getPaciente(Long id) {
		return pacientesDAO.findById(id);
	}

}
