package com.example.demo.Controller;

import java.util.HashMap;
import java.util.LinkedList;

import com.example.demo.Services.AffectedService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AffectedController {
    private AffectedService affectedService;

    public AffectedController (AffectedService affectedService) {
        super();
        this.affectedService = affectedService;
    }
    
	@GetMapping("/area/{cep}/{raio}")
	public ResponseEntity<LinkedList<HashMap<String, Double>>> getPacientes(@PathVariable String cep, @PathVariable double raio) {
		return new ResponseEntity<LinkedList<HashMap<String, Double>>>(affectedService.affected_area(cep, raio), HttpStatus.OK);
	}
    
}
