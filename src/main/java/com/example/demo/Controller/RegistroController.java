package com.example.demo.Controller;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.PacienteService;
import com.example.demo.util.DataInfo;
import com.example.demo.util.Util;

@RestController
public class RegistroController {

	private DataInfo dataInfo;
	
	public RegistroController(PacienteService pacienteService) {
		super();
		try {
			dataInfo = Util.loadLastDataInfo();
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
			dataInfo = new DataInfo();
		}
	}


	@CrossOrigin
	@GetMapping("/registros")
	public ResponseEntity<DataInfo> getRegistros() {
		return new ResponseEntity<DataInfo>(dataInfo, HttpStatus.OK);
	}
}
