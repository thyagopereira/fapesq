package com.example.demo.Controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.PacienteService;
import com.example.demo.util.DataInfo;
import com.example.demo.util.DataQuantidade;
import com.example.demo.util.SimpleRecord;
import com.example.demo.util.Util;
import com.example.demo.util.Record;

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

	@CrossOrigin
	@GetMapping("/registrosconfirmados")
	public ResponseEntity<LinkedList<Record>> getRegistrosConfirmados() {
		LinkedList<Record> records = dataInfo.getRecords();
		LinkedList<Record> confirmedRecords = new LinkedList<>();

		for (Record record: records) {
			if (record.isResultadoTeste()) {
				confirmedRecords.add(record);
			}		
		}

		return new ResponseEntity<LinkedList<Record>>(confirmedRecords, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping("/registrosrecuperados")
	public ResponseEntity<LinkedList<Record>> getRegistrosRecuperados() {
		LinkedList<Record> records = dataInfo.getRecords();
		LinkedList<Record> recoveredRecords = new LinkedList<>();

		for (Record record: records) {
			if (record.isResultadoTeste() && record.getDataEncerramento() != null) {

				recoveredRecords.add(record);
			}		
		}

		return new ResponseEntity<LinkedList<Record>>(recoveredRecords, HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/registroscg")
	public ResponseEntity<TreeMap<String,List<DataQuantidade>>> getRegistrosCg() {
		TreeMap<String,List<DataQuantidade>> records = new TreeMap<String,List<DataQuantidade>> ();
		
		try {
			records = Util.filterRecordsCG(dataInfo.getRecords());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<TreeMap<String,List<DataQuantidade>>>(records, HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/pbdatafiltered")
	public ResponseEntity<List<DataQuantidade>> getStateDataFiltered() {		
		
		List<DataQuantidade> dataFiltered = Util.loadStateDataFiltered();
		
		return new ResponseEntity<List<DataQuantidade>>(dataFiltered, HttpStatus.OK);
	}
}
