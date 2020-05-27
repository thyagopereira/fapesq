package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Paciente;
import com.example.demo.Services.PacienteService;

@RestController
public class PacienteController {

	private PacienteService pacienteService;

	public PacienteController(PacienteService pacienteService) {
		super();
		this.pacienteService = pacienteService;
	}

	@PostMapping("/paciente")
	public ResponseEntity<Paciente> addPaciente(@RequestBody Paciente paciente) {
		return new ResponseEntity<Paciente>(pacienteService.addPaciente(paciente), HttpStatus.CREATED);
	}

	@GetMapping("/pacientes")
	public ResponseEntity<List<Paciente>> getPacientes() {
		return new ResponseEntity<List<Paciente>>(pacienteService.getPacientes(), HttpStatus.OK);
	}

	@GetMapping("/paciente/{id}")
	public ResponseEntity<Paciente> getPaciente(@PathVariable Long id) {
		Optional<Paciente> paciente = pacienteService.getPaciente(id);
		if (paciente.isPresent()) {
			return new ResponseEntity<Paciente>(paciente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Paciente>(HttpStatus.NOT_FOUND);
		}

	}

}
