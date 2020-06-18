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
	public static final Pattern PATTERN_DATA = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{2}");
	public static final String TOKEN_NUMERO_NOTIFICACAO = "Número da Notificação";
	public static final String TOKEN_EH_PROFISSIONAL_SAUDE = "É profissional de saúde?";
	public static final String TOKEN_TIPO_TESTE = "Tipo de Teste";
	public static final String TOKEN_ESTADO_RESIDENCIA = "Estado de Residência";
	public static final String TOKEN_DATA_NOTIFICACAO = "Data da Notificação";
	public static final String TOKEN_FEBRE = "Febre";
	public static final String TOKEN_TOSSE = "Tosse";
	public static final String TOKEN_OUTROS = "Outros";
	public static final String TOKEN_DOR_GARGANTA = "Dor de Garganta";
	public static final String TOKEN_DISPNEIA = "Dispneia";
	public static final String TOKEN_CEP = "CEP";
	public static final String TOKEN_RESULTADO_TESTE = "Resultado do Teste";
	public static final String TOKEN_SEXO = "Sexo";
	public static final String TOKEN_ESTADO_TESTE = "Estado do Teste";
	public static final String TOKEN_DOENCA_RESPIRATORIA_DESCOMPENSADA = "Doenças respiratórias crônicas descompensadas";
	public static final String TOKEN_DOENCA_RENAL_AVANCADA = "Doenças renais crônicas em estágio avançado (graus 3, 4 ou 5)";
	public static final String TOKEN_DOENCA_CROMOSSOMICA = "Portador de doenças cromossômicas ou estado de fragilidade imunológica";
	public static final String TOKEN_DIABETES = "Diabetes";
	public static final String TOKEN_IMUNOSSUPRESSAO = "Imunossupressão";
	public static final String TOKEN_DOENCA_CARDIACA_CRONICA = "Doenças cardí­acas crônicas";
	public static final String TOKEN_GESTANTE_ALTO_RISCO = "Gestante de alto risco";
	public static final String TOKEN_BAIRRO = "Bairro";
	public static final String TOKEN_DATA_COLETA_TESTE = "Data de coleta do teste";
	public static final String TOKEN_DESCRICAO_SINTOMA = "Descrição do Sintoma";
	public static final String TOKEN_DATA_ENCERRAMENTO = "Data de encerramento";
	public static final String TOKEN_DATA_NASCIMENTO = "Data de Nascimento";
	public static final String TOKEN_MUNICIPIO = "Municí­pio de Residência";
	public static final String TOKEN_DATA_INICIO_SINTOMAS = "Data do iní­cio dos sintomas";
	
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
				 int currentColumnIndex = 0;
				 //buscar nas colunas carregadas se tem cada coluna pelo nome (TOKEN) e pega o index dela
				 
				 ColumnIndexName column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_NUMERO_NOTIFICACAO)).findFirst().orElse(null);
				 String numeroNotificacao = "";
				 Cell currentCell = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //0
					 numeroNotificacao = getCellContentAsString(currentCell);
				 } 
				 record.setNumeroNotificacao(numeroNotificacao);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_EH_PROFISSIONAL_SAUDE)).findFirst().orElse(null);
				 boolean profissionalAreaSaude = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //1
					 profissionalAreaSaude = getCellContentAsBoolean(currentCell);
				 }
				 record.setProfissionalAreaSaude(profissionalAreaSaude);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_TIPO_TESTE)).findFirst().orElse(null);
				 TipoTeste tipoTeste = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //5
					 tipoTeste = getCellContentAsTipoTeste(currentCell);
				 }
				 record.setTipoTeste(tipoTeste);
				 
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_ESTADO_RESIDENCIA)).findFirst().orElse(null);
				 String estadoResidencia = "";
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //6
					 estadoResidencia = getCellContentAsString(currentCell);
				 }
				 record.setEstadoResidencia(estadoResidencia);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_NOTIFICACAO)).findFirst().orElse(null);
				 GregorianCalendar dataNotificacao = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //8
					 dataNotificacao = getCellContentAsData(currentCell);
				 }
				 record.setDataNotificacao(dataNotificacao);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_FEBRE)).findFirst().orElse(null);
				 boolean febre = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //11
					 febre = getCellContentAsBoolean(currentCell);
				 }
				 record.setFebre(febre);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_TOSSE)).findFirst().orElse(null);
				 boolean tosse = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //12
					 tosse = getCellContentAsBoolean(currentCell);
				 }
				 record.setTosse(tosse);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_OUTROS)).findFirst().orElse(null);
				 boolean outros = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //13
					 outros = getCellContentAsBoolean(currentCell);
				 }
				 record.setOutros(outros);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOR_GARGANTA)).findFirst().orElse(null);
				 boolean dorGarganta = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //14
					 dorGarganta = getCellContentAsBoolean(currentCell);
				 }
				 record.setDorGarganta(dorGarganta);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DISPNEIA)).findFirst().orElse(null);
				 boolean dispneia = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //15
					 dispneia = getCellContentAsBoolean(currentCell);
				 }
				 record.setDispneia(dispneia);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_CEP)).findFirst().orElse(null);
				 String CEP = "";
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //16
					 CEP = getCellContentAsString(currentCell);
				 }
				 record.setCEP(CEP);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_RESULTADO_TESTE)).findFirst().orElse(null);
				 boolean resultadoTeste = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //18
					 resultadoTeste = getCellContentAsBoolean(currentCell);
				 }
				 record.setResultadoTeste(resultadoTeste);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_SEXO)).findFirst().orElse(null);
				 Sexo sexo = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //20
					 sexo = getCellContentAsSexo(currentCell);
				 }
				 record.setSexo(sexo);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_ESTADO_TESTE)).findFirst().orElse(null);
				 EstadoTeste estadoTeste = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //23
					 estadoTeste = getCellContentAsEstadoTeste(currentCell);
				 }
				 record.setEstadoTeste(estadoTeste);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_RESPIRATORIA_DESCOMPENSADA)).findFirst().orElse(null);
				 boolean doencaRespiratoriaDescompensada = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //24
					 doencaRespiratoriaDescompensada = getCellContentAsBoolean(currentCell);
				 }
				 record.setDoencaRespiratoriaDescompensada(doencaRespiratoriaDescompensada);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_RENAL_AVANCADA)).findFirst().orElse(null);
				 boolean doencaRenalAvancada = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //25
					 doencaRenalAvancada = getCellContentAsBoolean(currentCell);
				 }
				 record.setDoencaRenalAvancada(doencaRenalAvancada);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_CROMOSSOMICA)).findFirst().orElse(null);
				 boolean doencaCromossomicaImunologica = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //26
					 doencaCromossomicaImunologica = getCellContentAsBoolean(currentCell);
				 }
				 record.setDoencaCromossomicaImunologica(doencaCromossomicaImunologica);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DIABETES)).findFirst().orElse(null);
				 boolean diabetes = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //27
					 diabetes = getCellContentAsBoolean(currentCell);
				 }
				 record.setDiabetes(diabetes);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_IMUNOSSUPRESSAO)).findFirst().orElse(null);
				 boolean imunossupressao = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //28
					 imunossupressao = getCellContentAsBoolean(currentCell);
				 }
				 record.setImunossupressao(imunossupressao);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_CARDIACA_CRONICA)).findFirst().orElse(null);
				 boolean doencaCardiacaCronica = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //29
					 doencaCardiacaCronica = getCellContentAsBoolean(currentCell);
				 }
				 record.setDoencaCardiacaCronica(doencaCardiacaCronica);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_GESTANTE_ALTO_RISCO)).findFirst().orElse(null);
				 boolean gestanteAltoRisco = false;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //30
					 gestanteAltoRisco = getCellContentAsBoolean(currentCell);
				 }
				 record.setGestanteAltoRisco(gestanteAltoRisco);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_BAIRRO)).findFirst().orElse(null);
				 String bairro = "";
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //31
					 bairro = getCellContentAsString(currentCell);
				 }
				 record.setBairro(bairro);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_COLETA_TESTE)).findFirst().orElse(null);
				 GregorianCalendar dataColetaTeste = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //32
					 dataColetaTeste = getCellContentAsData(currentCell);
				 }
				 record.setDataColetaTeste(dataColetaTeste);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DESCRICAO_SINTOMA)).findFirst().orElse(null);
				 String descricaoSintoma = "";
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //34
					 descricaoSintoma = getCellContentAsString(currentCell);
				 }
				 record.setDescricaoSintoma(descricaoSintoma);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_ENCERRAMENTO)).findFirst().orElse(null);
				 GregorianCalendar dataEncerramento = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //35
					 dataEncerramento = getCellContentAsData(currentCell);
				 }
				 record.setDataEncerramento(dataEncerramento);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_NASCIMENTO)).findFirst().orElse(null);
				 GregorianCalendar dataNascimento = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //36
					 dataNascimento = getCellContentAsData(currentCell);
				 }
				 record.setDataNascimento(dataNascimento);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_MUNICIPIO)).findFirst().orElse(null);
				 String municipio = "";
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //38
					 municipio = getCellContentAsString(currentCell);
				 }
				 record.setMunicipio(municipio);
				 
				 column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_INICIO_SINTOMAS)).findFirst().orElse(null);
				 GregorianCalendar dataInicioSintomas = null;
				 if(column != null) {
					 currentColumnIndex = column.getIndex();
					 currentCell = currentRow.getCell(currentColumnIndex); //44
					 dataInicioSintomas = getCellContentAsData(currentCell);
				 }
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
        System.out.println("Number of records: " + dataInfo.getNumberOfRecords());
		
		
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
		return new File("/Users/adalbertocajueiro/Downloads/Base 16_06 UFCG.xlsx");
	}
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		File file = new File("/Users/adalbertocajueiro/Downloads/Base 16_06 UFCG.xlsx");
		//System.out.println("Sim".equalsIgnoreCase("sim"));
		//System.out.println(cleanString("NÃ£o").equalsIgnoreCase("não"));
		DataInfo dados = loadDataFromExcel(file);
		//dados.getLoadedColumns().forEach(d -> System.out.println(d.getName()));
		long filtrados = dados.getRecords().stream().filter(r -> r.getEstadoResidencia().equalsIgnoreCase("Paraí­ba")).count();
		System.out.println("Registros filtrados: " + filtrados);
	}
}
