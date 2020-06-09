package com.example.demo.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Util {
	public static Pattern PATTERN_DATA = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{2}");
	
	public static String cleanString(String string) {
		string = string.replaceAll("Ãº", "ú");
		string = string.replaceAll("Ã§", "ç");
		string = string.replaceAll("Ã£", "ã");
		string = string.replaceAll("Ã‰", "É");
		string = string.replaceAll("Ãª", "ê");
		string = string.replaceAll("Ã³", "ó");
		string = string.replaceAll("Ã´", "ô");
		string = string.replaceAll("Ã¡", "á");
		string = string.replaceAll("Ã", "í");
		
		return string;
	}
	public static GregorianCalendar stringToData(String stringData) {
		GregorianCalendar result = null;
		
		if(stringData != null) {
			if (PATTERN_DATA.matcher(stringData).matches()) {
				String[] camposData = stringData.split("[/]");
				int mes = Integer.parseInt(camposData[0]) - 1;
				int dia = Integer.parseInt(camposData[1]);
				int ano = Integer.parseInt(camposData[2]) + 2000;
				
				result = new GregorianCalendar();
				result.set(Calendar.DAY_OF_MONTH, dia);
				result.set(Calendar.MONTH, mes);
				result.set(Calendar.YEAR, ano);
			}
		}
		
		return result;
	}
	
	public static String getCellContentAsString(Cell cell) {
		String result = "";
		if(cell != null) {
			if(cell.getCellType() != null) {
				if(cell.getCellType().equals(CellType.STRING)) {
					result = cleanString(cell.getStringCellValue());	 
				} else if(cell.getCellType().equals(CellType.NUMERIC)) {
					SimpleDateFormat df = new SimpleDateFormat("############");
					result = df.getNumberFormat().format(cell.getNumericCellValue());
				}
			}
		}
		
		return result;
	}
	
	public static GregorianCalendar getCellContentAsData(Cell cell) {
		GregorianCalendar result = null;
		if(cell != null) {
			if(cell.getCellType() != null) {
				if(cell.getCellType().equals(CellType.NUMERIC)) {				
					Date data = DateUtil.getJavaDate(cell.getNumericCellValue());
					result = new GregorianCalendar();
					result.setTime(data);
				}
			}
		}

		return result;
	}
	
	public static boolean getCellContentAsBoolean(Cell cell) {
		boolean result = false;
		if(cell != null) {
			if(cell.getCellType() != null) {
				if(cell.getCellType().equals(CellType.STRING)) {
					String valor = cleanString(cell.getStringCellValue());
					if(valor.equalsIgnoreCase("Sim") || valor.equalsIgnoreCase("Positivo")) {
						result = true;
					}
				}
			}
		}
		
		
		return result;
	}
	public static TipoTeste getCellContentAsTipoTeste(Cell cell) {
		TipoTeste result = TipoTeste.NAO_CLASSIFICADO;
		if(cell != null) {
			if(cell.getCellType() != null) {
				if(cell.getCellType().equals(CellType.STRING)) {
					String valor = cleanString(cell.getStringCellValue());
					if(valor.equalsIgnoreCase("RT-PCR")) {
						result = TipoTeste.RT_PCR;
					} else if (valor.equalsIgnoreCase("TESTE RíPIDO - ANTICORPO")) {
						result = TipoTeste.TESTE_RAPIDO_ANTICORPO;
					} else if (valor.equalsIgnoreCase("TESTE RíPIDO - ANTíGENO")) {
						result = TipoTeste.TESTE_RAPIDO_ANTIGENO;
					}
				}
			}
		}
		
		
		return result;
	}
	
	public static EstadoTeste getCellContentAsEstadoTeste(Cell cell) {
		EstadoTeste result = EstadoTeste.SOLICITADO;
		
		if(cell != null) {
			if(cell.getCellType() != null) {
				if(cell.getCellType().equals(CellType.STRING)) {
					String valor = cleanString(cell.getStringCellValue());
					if(valor.equalsIgnoreCase("Concluí­do")) {
						result = EstadoTeste.CONCLUIDO;
					} else if (valor.equalsIgnoreCase("Coletado")) {
						result = EstadoTeste.COLETADO;
					} 
				}
			}
		}
		
		
		return result;
	}
	public static Sexo getCellContentAsSexo(Cell cell) {
		Sexo result = Sexo.MASCULINO;
		if(cell != null) {
			if(cell.getCellType() != null) {
				if(cell.getCellType().equals(CellType.STRING)) {
					String valor = cleanString(cell.getStringCellValue());
					if(valor.equalsIgnoreCase("Feminino")) {
						result = Sexo.FEMININO;
					} 
				}
			}
		}
		
		
		return result;
	}
	public static DataInfo loadDataFromExcel(File file) throws EncryptedDocumentException, IOException {
		DataInfo dataInfo = new DataInfo();
		dataInfo.setFile(file);
		
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
			int numberOfSheets = wb.getNumberOfSheets();
			System.out.println("Loading file: " + file.getName());
			System.out.println("Number of sheets: " + numberOfSheets);
			System.out.println("Assuming data in sheet 0 (first sheet)");
			System.out.println("Building data to be returned");
			Sheet sheet = wb.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			dataInfo.setNumberOfRecords(lastRowNum);
			
			Row firstRow = sheet.getRow(0);
			int lastColumn = firstRow.getLastCellNum();
			dataInfo.setNumberOfColumns(lastColumn + 1);
			
			LinkedList<ColumnIndexName> columns = new LinkedList<ColumnIndexName>();
			
			for (Cell cell : firstRow) {
				String nomeColuna = cleanString(cell.getStringCellValue());
				columns.add(new ColumnIndexName(cell.getColumnIndex(),nomeColuna));
			}
			dataInfo.setLoadedColumns(columns);
			
			
			LinkedList<Record> records = new LinkedList<Record>();
			for (int i = 1; i <= lastRowNum; i++) {
				 Row currentRow = sheet.getRow(i);
				 Record record = new Record();
				 
				 Cell currentCell = currentRow.getCell(0);
				 String numeroNotificacao = getCellContentAsString(currentCell);
				 record.setNumeroNotificacao(numeroNotificacao);
				 
				 currentCell = currentRow.getCell(1);
				 boolean profissionalAreaSaude = getCellContentAsBoolean(currentCell);
				 record.setProfissionalAreaSaude(profissionalAreaSaude);
				 
				 currentCell = currentRow.getCell(5);
				 TipoTeste tipoTeste = getCellContentAsTipoTeste(currentCell);
				 record.setTipoTeste(tipoTeste);
				 
				 currentCell = currentRow.getCell(6);
				 String estadoResidencia = getCellContentAsString(currentCell);
				 record.setEstadoResidencia(estadoResidencia);
				 
				 currentCell = currentRow.getCell(8);
				 GregorianCalendar dataNotificacao = getCellContentAsData(currentCell);
				 record.setDataNotificacao(dataNotificacao);
				 
				 currentCell = currentRow.getCell(11);
				 boolean febre = getCellContentAsBoolean(currentCell);
				 record.setFebre(febre);
				 
				 currentCell = currentRow.getCell(12);
				 boolean tosse = getCellContentAsBoolean(currentCell);
				 record.setTosse(tosse);
				 
				 currentCell = currentRow.getCell(13);
				 boolean outros = getCellContentAsBoolean(currentCell);
				 record.setOutros(outros);
				 
				 currentCell = currentRow.getCell(14);
				 boolean dorGarganta = getCellContentAsBoolean(currentCell);
				 record.setDorGarganta(dorGarganta);
				 
				 currentCell = currentRow.getCell(15);
				 boolean dispneia = getCellContentAsBoolean(currentCell);
				 record.setDispneia(dispneia);
				 
				 currentCell = currentRow.getCell(16);
				 String CEP = getCellContentAsString(currentCell);
				 record.setCEP(CEP);
				 
				 currentCell = currentRow.getCell(18);
				 boolean resultadoTeste = getCellContentAsBoolean(currentCell);
				 record.setResultadoTeste(resultadoTeste);
				 
				 currentCell = currentRow.getCell(20);
				 Sexo sexo = getCellContentAsSexo(currentCell);
				 record.setSexo(sexo);
				 
				 currentCell = currentRow.getCell(23);
				 EstadoTeste estadoTeste = getCellContentAsEstadoTeste(currentCell);
				 record.setEstadoTeste(estadoTeste);
				 
				 currentCell = currentRow.getCell(24);
				 boolean doencaRespiratoriaDescompensada = getCellContentAsBoolean(currentCell);
				 record.setDoencaRespiratoriaDescompensada(doencaRespiratoriaDescompensada);
				 
				 currentCell = currentRow.getCell(25);
				 boolean doencaRenalAvancada = getCellContentAsBoolean(currentCell);
				 record.setDoencaRenalAvancada(doencaRenalAvancada);
				 
				 currentCell = currentRow.getCell(26);
				 boolean doencaCromossomicaImunologica = getCellContentAsBoolean(currentCell);
				 record.setDoencaCromossomicaImunologica(doencaCromossomicaImunologica);
				 
				 currentCell = currentRow.getCell(27);
				 boolean diabetes = getCellContentAsBoolean(currentCell);
				 record.setDiabetes(diabetes);
				 
				 currentCell = currentRow.getCell(28);
				 boolean imunossupressao = getCellContentAsBoolean(currentCell);
				 record.setImunossupressao(imunossupressao);
				 
				 currentCell = currentRow.getCell(29);
				 boolean doencaCardiacaCronica = getCellContentAsBoolean(currentCell);
				 record.setDoencaCardiacaCronica(doencaCardiacaCronica);
				 
				 currentCell = currentRow.getCell(30);
				 boolean gestanteAltoRisco = getCellContentAsBoolean(currentCell);
				 record.setGestanteAltoRisco(gestanteAltoRisco);
				 
				 currentCell = currentRow.getCell(31);
				 String bairro = getCellContentAsString(currentCell);
				 record.setBairro(bairro);
				 
				 currentCell = currentRow.getCell(32);
				 GregorianCalendar dataColetaTeste = getCellContentAsData(currentCell);
				 record.setDataColetaTeste(dataColetaTeste);
				 
				 currentCell = currentRow.getCell(34);
				 String descricaoSintoma = getCellContentAsString(currentCell);
				 record.setDescricaoSintoma(descricaoSintoma);
				 
				 currentCell = currentRow.getCell(35);
				 GregorianCalendar dataEncerramento = getCellContentAsData(currentCell);
				 record.setDataEncerramento(dataEncerramento);
				 
				 currentCell = currentRow.getCell(36);
				 GregorianCalendar dataNascimento = getCellContentAsData(currentCell);
				 record.setDataNascimento(dataNascimento);
				 
				 currentCell = currentRow.getCell(38);
				 String municipio = getCellContentAsString(currentCell);
				 record.setMunicipio(municipio);
				 
				 currentCell = currentRow.getCell(44);
				 GregorianCalendar dataInicioSintomas = getCellContentAsData(currentCell);
				 record.setDataInicioSintomas(dataInicioSintomas);
				 
				 records.add(record);
			}
			dataInfo.setRecords(records);
			dataInfo.setNumberOfRecords(records.size());
		} catch (EncryptedDocumentException | IOException e) {			
			e.printStackTrace();
			throw e;
		} finally{
			if(wb != null) {
				wb.close();
			}
		}
        
		
		
		return dataInfo;
	}
	
	public static DataInfo loadLastDataInfo() throws EncryptedDocumentException, IOException {
		File file = getLastDataInfo();
		return loadDataFromExcel(file);
		
	}
	private static File getLastDataInfo() {
		//aqui deve conter a logica de pegar o ultimo arquivo de dados subido para o servidor
		//os arquivos devem ser subidos para uma pasta especifica via servico 
		//pela data do arquivo mais recente (ou pelo nome) creio qu eseja possivel saber 
		//qual arquivo seria o mais recente.
		return new File("/Users/adalbertocajueiro/Downloads/Base 03-06 UFCG.xlsx");
	}
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		File file = new File("/Users/adalbertocajueiro/Downloads/Base 03-06 UFCG.xlsx");
		//System.out.println("Sim".equalsIgnoreCase("sim"));
		//System.out.println(cleanString("NÃ£o").equalsIgnoreCase("não"));
		DataInfo dados = loadDataFromExcel(file);
		System.out.println(dados.getNumberOfRecords());
	}
}
