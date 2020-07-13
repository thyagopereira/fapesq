package com.example.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Util {
	public static final String FOLDER_DADOS_NAME = "dados";
	public static final String FILE_BAIRROS_NAME = "bairros-cg.txt";
	public static final String FILE_CSV_FILTRADO = "cases_per_date_filtered.txt";
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
	
	public static TreeSet<String> BAIRROS_CADASTRADOS = new TreeSet<String>();
	public static final double SIMILARITY = 0.7;
	
	static {
		try {
			BAIRROS_CADASTRADOS = loadBairros();
		} catch (IOException e) {
			System.out.println("Lista de bairros nao carregados do arquivo");
			e.printStackTrace();
		}
	}
	
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
		string = string.replaceAll("í©", "é");
		string = string.replaceAll("íƒ", "ã");
		string = string.replaceAll("í‡", "Ç");
		string = string.replaceAll("í“", "Ó");
		string = string.replaceAll("íˆ", "É");
		string = string.replaceAll("í•", "Õ");
		string = string.replaceAll("íµ", "õ");
		string = string.replaceAll("íŠ", "Ê");
		string = string.replaceAll("í", "Á");
		string = string.replaceAll("í¢", "â");

	
		string = string.trim();
		
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
					} else if (similarity(valor, "TESTE RíPIDO - ANTICORPO") > 0.9 && valor.contains("CORPO")) {
						result = TipoTeste.TESTE_RAPIDO_ANTICORPO;
					} else if (similarity(valor,"TESTE RíPIDO - ANTíGENO") > 0.9 && valor.contains("GENO")) {
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
			System.out.println("Loading file: " + file.getAbsolutePath());
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
				columns.add(new ColumnIndexName(cell.getColumnIndex(), nomeColuna));
			}
			dataInfo.setLoadedColumns(columns);

			LinkedList<Record> records = new LinkedList<Record>();
			int id = 1;
			for (int i = 1; i <= lastRowNum; i++) {
				Row currentRow = sheet.getRow(i);
				Record record = new Record();

				record.setId(id++);
				int currentColumnIndex = 0;
				// buscar nas colunas carregadas se tem cada coluna pelo nome (TOKEN) e pega o
				// index dela

				ColumnIndexName column = columns.stream()
						.filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_NUMERO_NOTIFICACAO)).findFirst()
						.orElse(null);
				String numeroNotificacao = "";
				Cell currentCell = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 0
					numeroNotificacao = getCellContentAsString(currentCell);
				}
				record.setNumeroNotificacao(numeroNotificacao);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_EH_PROFISSIONAL_SAUDE))
						.findFirst().orElse(null);
				boolean profissionalAreaSaude = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 1
					profissionalAreaSaude = getCellContentAsBoolean(currentCell);
				}
				record.setProfissionalAreaSaude(profissionalAreaSaude);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_TIPO_TESTE)).findFirst()
						.orElse(null);
				TipoTeste tipoTeste = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 5
					tipoTeste = getCellContentAsTipoTeste(currentCell);
				}
				record.setTipoTeste(tipoTeste);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_ESTADO_RESIDENCIA))
						.findFirst().orElse(null);
				String estadoResidencia = "";
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 6
					estadoResidencia = getCellContentAsString(currentCell);
				}
				record.setEstadoResidencia(estadoResidencia);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_NOTIFICACAO))
						.findFirst().orElse(null);
				GregorianCalendar dataNotificacao = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 8
					dataNotificacao = getCellContentAsData(currentCell);
				}
				record.setDataNotificacao(dataNotificacao);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_FEBRE)).findFirst()
						.orElse(null);
				boolean febre = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 11
					febre = getCellContentAsBoolean(currentCell);
				}
				record.setFebre(febre);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_TOSSE)).findFirst()
						.orElse(null);
				boolean tosse = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 12
					tosse = getCellContentAsBoolean(currentCell);
				}
				record.setTosse(tosse);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_OUTROS)).findFirst()
						.orElse(null);
				boolean outros = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 13
					outros = getCellContentAsBoolean(currentCell);
				}
				record.setOutros(outros);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOR_GARGANTA))
						.findFirst().orElse(null);
				boolean dorGarganta = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 14
					dorGarganta = getCellContentAsBoolean(currentCell);
				}
				record.setDorGarganta(dorGarganta);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DISPNEIA)).findFirst()
						.orElse(null);
				boolean dispneia = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 15
					dispneia = getCellContentAsBoolean(currentCell);
				}
				record.setDispneia(dispneia);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_CEP)).findFirst()
						.orElse(null);
				String CEP = "";
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 16
					CEP = getCellContentAsString(currentCell);
					// System.out.println("CEP: " + CEP);
				}
				record.setCEP(CEP);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_RESULTADO_TESTE))
						.findFirst().orElse(null);
				boolean resultadoTeste = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();

					currentCell = currentRow.getCell(currentColumnIndex); // 18
					resultadoTeste = getCellContentAsBoolean(currentCell);
				}
				record.setResultadoTeste(resultadoTeste);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_SEXO)).findFirst()
						.orElse(null);
				Sexo sexo = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 20
					sexo = getCellContentAsSexo(currentCell);
				}
				record.setSexo(sexo);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_ESTADO_TESTE))
						.findFirst().orElse(null);
				EstadoTeste estadoTeste = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 23
					estadoTeste = getCellContentAsEstadoTeste(currentCell);
				}
				record.setEstadoTeste(estadoTeste);

				column = columns.stream()
						.filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_RESPIRATORIA_DESCOMPENSADA))
						.findFirst().orElse(null);
				boolean doencaRespiratoriaDescompensada = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 24
					doencaRespiratoriaDescompensada = getCellContentAsBoolean(currentCell);
				}
				record.setDoencaRespiratoriaDescompensada(doencaRespiratoriaDescompensada);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_RENAL_AVANCADA))
						.findFirst().orElse(null);
				boolean doencaRenalAvancada = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 25
					doencaRenalAvancada = getCellContentAsBoolean(currentCell);
				}
				record.setDoencaRenalAvancada(doencaRenalAvancada);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_CROMOSSOMICA))
						.findFirst().orElse(null);
				boolean doencaCromossomicaImunologica = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 26
					doencaCromossomicaImunologica = getCellContentAsBoolean(currentCell);
				}
				record.setDoencaCromossomicaImunologica(doencaCromossomicaImunologica);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DIABETES)).findFirst()
						.orElse(null);
				boolean diabetes = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 27
					diabetes = getCellContentAsBoolean(currentCell);
				}
				record.setDiabetes(diabetes);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_IMUNOSSUPRESSAO))
						.findFirst().orElse(null);
				boolean imunossupressao = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 28
					imunossupressao = getCellContentAsBoolean(currentCell);
				}
				record.setImunossupressao(imunossupressao);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DOENCA_CARDIACA_CRONICA))
						.findFirst().orElse(null);
				boolean doencaCardiacaCronica = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 29
					doencaCardiacaCronica = getCellContentAsBoolean(currentCell);
				}
				record.setDoencaCardiacaCronica(doencaCardiacaCronica);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_GESTANTE_ALTO_RISCO))
						.findFirst().orElse(null);
				boolean gestanteAltoRisco = false;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 30
					gestanteAltoRisco = getCellContentAsBoolean(currentCell);
				}
				record.setGestanteAltoRisco(gestanteAltoRisco);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_BAIRRO)).findFirst()
						.orElse(null);
				String bairro = "";
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 31
					bairro = getCellContentAsString(currentCell);
					// mapear bairro para algum cadastrado
					if (BAIRROS_CADASTRADOS.size() > 1) {
						for (String b : BAIRROS_CADASTRADOS) {
							if (similarity(bairro, b) > SIMILARITY) {
								bairro = b;
								break;
							}
						}
						if (!BAIRROS_CADASTRADOS.contains(bairro)) {
							// se nao foi mapeado tem que tratar aqui
							bairro = findNameBairro(bairro);
							for (String b : BAIRROS_CADASTRADOS) {
								if (similarity(bairro, b) > SIMILARITY) {
									bairro = b;
									break;
								}
							}
						}

					}
				}
				record.setBairro(bairro);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_COLETA_TESTE))
						.findFirst().orElse(null);
				GregorianCalendar dataColetaTeste = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 32
					dataColetaTeste = getCellContentAsData(currentCell);
				}
				record.setDataColetaTeste(dataColetaTeste);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DESCRICAO_SINTOMA))
						.findFirst().orElse(null);
				String descricaoSintoma = "";
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 34
					descricaoSintoma = getCellContentAsString(currentCell);
				}
				record.setDescricaoSintoma(descricaoSintoma);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_ENCERRAMENTO))
						.findFirst().orElse(null);
				GregorianCalendar dataEncerramento = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 35
					dataEncerramento = getCellContentAsData(currentCell);
				}
				record.setDataEncerramento(dataEncerramento);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_NASCIMENTO))
						.findFirst().orElse(null);
				GregorianCalendar dataNascimento = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 36
					dataNascimento = getCellContentAsData(currentCell);
				}
				record.setDataNascimento(dataNascimento);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_MUNICIPIO)).findFirst()
						.orElse(null);
				String municipio = "";
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 38
					municipio = getCellContentAsString(currentCell);
				}
				record.setMunicipio(municipio);

				column = columns.stream().filter(elem -> elem.getName().equalsIgnoreCase(TOKEN_DATA_INICIO_SINTOMAS))
						.findFirst().orElse(null);
				GregorianCalendar dataInicioSintomas = null;
				if (column != null) {
					currentColumnIndex = column.getIndex();
					currentCell = currentRow.getCell(currentColumnIndex); // 44
					dataInicioSintomas = getCellContentAsData(currentCell);
				}
				record.setDataInicioSintomas(dataInicioSintomas);
				if(record.getDataNotificacao() != null) {
					records.add(record);
				}
			}
			dataInfo.setRecords(records);
			dataInfo.setNumberOfRecords(records.size());
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		System.out.println("Number of records: " + dataInfo.getNumberOfRecords());

		return dataInfo;
	}
	private static String findNameBairro(String bairro) {
		String result = bairro;
		if (bairro.contains("BODOCON") || bairro.contains("BONDOC") || bairro.contains("BODONG")) {
			result = "Bodocongó";
		}else if(bairro.contains("UNIVERSITA")) {
			result = "Universitário";
		} else if (bairro.contains("GALANTE")) {
			result = "Galante";
		} else if (bairro.contains("ALUISIO") || bairro.contains("Aloí­sio")) {
			result = "Aluísio Campos";
		} else if (bairro.contains("nacoes") || bairro.contains("NACOES") || bairro.contains("NAÇOES") || bairro.contains("NAÇÕES")) {
			result = "Bairro das Nações";
		} else if (bairro.contains("DINAMERICA")) {
			result = "Dinamérica";
		} else if (bairro.contains("ARAX")) {
			result = "Araxá";
		} else if (bairro.contains("DA MATA")) {
			result = "São José da Mata";
		} else if (bairro.contains("CINZ")) {
			result = "Conjunto Cinza";
		} else if(bairro.contains("GLORIA") || bairro.contains("GLÓRIA") ||bairro.contains("Gloria") ) {
			result = "Glória";
		} else if(bairro.contains("QUARENTA") || bairro.contains("Quarenta") || bairro.contains("QUARENT")) {
			result = "Quarenta";
		} else if (bairro.contains("LIGEIRO")) {
			result = "Ligeiro";
		} else if (bairro.contains("M CASTELO")) {
			result = "Monte Castelo";
		} else if (bairro.contains("MULTIRAO") || bairro.contains("MULTIRãO") || bairro.contains("MUTIRAO") || bairro.contains("MUTIRãO")) {
			result = "Mutirão do Serrotão";
		} else if(bairro.contains("medice") || bairro.contains("médici") || bairro.contains("MÉDICE") || bairro.contains("MEDICE") || bairro.contains("MEDICI") || bairro.contains("MEDICí") || bairro.contains("MÉDICE") || bairro.contains("MÉDICI") || bairro.contains("Médici")) {
			result = "Pres. Médici";
		} else if (bairro.contains("RAMADINHA") || bairro.contains("RAMADINHO") || bairro.contains("RAMANDINHA") || bairro.contains("RANADINHA")) {
			result = "Ramadinha";
		} else if (bairro.contains("IRMAS") || bairro.contains("Tres") || bairro.contains("3")) {
			result = "Três Irmãs";
		} else if (bairro.contains("LAURITZEN")) {
			result = "Lauritzen";
		} else if(bairro.contains("rural") || bairro.contains("ZONA") || bairro.contains("zona") || bairro.contains("Zona")) {
			result = "Área Rural";
		} else if (bairro.contains("SUDOESTE") || bairro.contains("SULDOESTE") || bairro.contains("sudoeste") || bairro.contains("Sudoeste")) {
			result = "Portal Sudoeste";
		} else if (bairro.contains("BRASILIA")) {
			result = "Nova Brasília";
		} else if (bairro.contains("C E N T R O")) {
			result = "Centro";
		} else if (bairro.contains("CIDADES")) {
			result = "Bairro das Cidades";
		}
		
		return result;
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
		File folder = new File(FOLDER_DADOS_NAME);
		return new File(folder,"Base 03_07 7h UFCG.xlsx");
	}
	public static TreeSet<String> loadBairros() throws IOException{
		TreeSet<String> bairros = new TreeSet<String>();
		File folderDados = new File(FOLDER_DADOS_NAME);
		File fileBairros = new File(folderDados,FILE_BAIRROS_NAME);
		FileReader fr = new FileReader(fileBairros);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while((line = br.readLine()) != null) {
			if(line.length() > 3) {
				bairros.add(line);
			}
		}
		
		return bairros;
	}

	/**
	 * Calculates the similarity (a number within 0 and 1) between two strings.
	 * Pega de: https://stackoverflow.com/questions/955110/similarity-string-comparison-in-java
	 */
	public static double similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
			/* both strings are zero length */ }
		/*
		 * // If you have Apache Commons Text, you can use it to calculate the edit
		 * distance: LevenshteinDistance levenshteinDistance = new
		 * LevenshteinDistance(); return (longerLength -
		 * levenshteinDistance.apply(longer, shorter)) / (double) longerLength;
		 */
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	// Example implementation of the Levenshtein Edit Distance
	// See http://rosettacode.org/wiki/Levenshtein_distance#Java
	public static int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}
	public static List<DataQuantidade> listRecordToListSingleRecord(List<Record> list) {
		List<DataQuantidade> retorno = new LinkedList<DataQuantidade>();
		
		Comparator<GregorianCalendar> comparador = 
				(d1,d2) -> {
					
					if (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
						if(d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)) {
							return d1.get(Calendar.DAY_OF_MONTH) - d2.get(Calendar.DAY_OF_MONTH);
						}else {
							return d1.get(Calendar.MONTH) - d2.get(Calendar.MONTH);
						}
					} else {
						return d1.get(Calendar.YEAR) - d2.get(Calendar.YEAR); 
					}
				};
		TreeMap<String,Long> ret = new TreeMap<String,Long>( 
				list.stream()
				.sorted( (r1,r2) -> comparador.compare(r1.getDataNotificacao(), r2.getDataNotificacao()))
				.collect(Collectors.groupingBy(Record::getDataNotificacaoAsString,Collectors.counting())));
		
		ret.forEach( (d,num) -> retorno.add(new DataQuantidade(d,num.intValue())));
		Comparator<DataQuantidade> comparadorDtQtde = 
				(d1,d2) -> {
					
					if (d1.getData().get(Calendar.YEAR) == d2.getData().get(Calendar.YEAR)) {
						if(d1.getData().get(Calendar.MONTH) == d2.getData().get(Calendar.MONTH)) {
							return d1.getData().get(Calendar.DAY_OF_MONTH) - d2.getData().get(Calendar.DAY_OF_MONTH);
						}else {
							return d1.getData().get(Calendar.MONTH) - d2.getData().get(Calendar.MONTH);
						}
					} else {
						return d1.getData().get(Calendar.YEAR) - d2.getData().get(Calendar.YEAR); 
					}
				};
				
		retorno.sort(comparadorDtQtde);
		return retorno;
	}
	
	public static LinkedList<DataQuantidade> loadStateDataFiltered() {
		LinkedList<DataQuantidade> historico = new LinkedList<DataQuantidade>();
		File folder = new File(FOLDER_DADOS_NAME);
		File casesPerDateFilteded = new File(folder,FILE_CSV_FILTRADO);
		Comparator<DataQuantidade> comparadorDtQtde = 
				(d1,d2) -> {
					
					if (d1.getData().get(Calendar.YEAR) == d2.getData().get(Calendar.YEAR)) {
						if(d1.getData().get(Calendar.MONTH) == d2.getData().get(Calendar.MONTH)) {
							return d1.getData().get(Calendar.DAY_OF_MONTH) - d2.getData().get(Calendar.DAY_OF_MONTH);
						}else {
							return d1.getData().get(Calendar.MONTH) - d2.getData().get(Calendar.MONTH);
						}
					} else {
						return d1.getData().get(Calendar.YEAR) - d2.getData().get(Calendar.YEAR); 
					}
				};
				
		try {
			BufferedReader reader = Files.newBufferedReader(casesPerDateFilteded.toPath());
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] campos = line.split(",");
				String data = campos[0];
				String[] dataCampos = data.split("-");
				int ano = Integer.parseInt(dataCampos[0]);
				int mes = Integer.parseInt(dataCampos[1]) - 1;
				int dia = Integer.parseInt(dataCampos[2]);
				GregorianCalendar dt = new GregorianCalendar();
				dt.set(Calendar.YEAR, ano);
				dt.set(Calendar.MONTH, mes);
				dt.set(Calendar.DAY_OF_MONTH, dia);
				int qtde = Integer.parseInt(campos[1]);
				
				DataQuantidade dtqtde = new DataQuantidade();
				dtqtde.setData(dt);
				dtqtde.setQuantidade(qtde);
				historico.add(dtqtde);
			}
			historico.sort(comparadorDtQtde);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return historico;
	
	}
	public static TreeMap<String,List<DataQuantidade>> filterRecordsCG(LinkedList<Record> records) throws IOException{
		TreeMap<String,List<DataQuantidade>> result = new TreeMap<String,List<DataQuantidade>>();
		TreeSet<String> bairros = loadBairros();
		
		Comparator<GregorianCalendar> comparador = 
				(d1,d2) -> {
					if (d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)) {
						if(d1.get(Calendar.MONTH) == d2.get(Calendar.MONTH)) {
							return d1.get(Calendar.DAY_OF_MONTH) - d2.get(Calendar.DAY_OF_MONTH);
						}else {
							return d1.get(Calendar.MONTH) - d2.get(Calendar.MONTH);
						}
					} else {
						return d1.get(Calendar.YEAR) - d2.get(Calendar.YEAR); 
					}
				};
		//campos a serem retornados na lista
		////Data, Bairro, Qtde casos no bairro, Qtde óbitos (nao existe esse ultimo campo)
		TreeMap<String,List<Record>> registrosFiltrados = new TreeMap<String,List<Record>> (
				records.stream()
					.filter(r -> r.isResultadoTeste())
					.filter(r -> r.getEstadoResidencia().equals("Paraí­ba"))
					.filter(r -> r.getMunicipio().equals("Campina Grande"))
					.filter(r -> bairros.contains(r.getBairro()))
					.collect(Collectors.groupingBy(Record::getBairro)));
		
		
		registrosFiltrados.forEach((k,v) -> {
			List<DataQuantidade> dataQuantidade = listRecordToListSingleRecord(v);
			result.put(k, dataQuantidade);
		});
		/*
		 * System.out.println("TOTAL DE CASOS: " +
		 * registrosFiltrados.values().stream().map(List::size).reduce(0,Integer::sum));
		 * for (Map.Entry<String, List<DataQuantidade>> entry : result.entrySet()) {
		 * System.out.println("Bairro : " + entry.getKey() + " Casos : " +
		 * entry.getValue().stream().map(DataQuantidade::getQuantidade).reduce(0,Integer
		 * ::sum)); }
		 */
		//registrosFiltrados.forEach((n,c) -> System.out.println("Bairro: " + n + "( " + c + " ocorrencias)"));
		
		return result;
	}
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		File folder = new File(FOLDER_DADOS_NAME);
		File file = getLastDataInfo(); //new File(folder,"Base 16_06 UFCG.xlsx");
		//System.out.println("Sim".equalsIgnoreCase("sim"));
		//System.out.println(cleanString("NÃ£o").equalsIgnoreCase("não"));
		LinkedList<DataQuantidade>	historicoDiario = loadStateDataFiltered();
		DataInfo dados = loadDataFromExcel(file);
		dados.getLoadedColumns().forEach(d -> System.out.println(d.getName()));
		//long filtrados = dados.getRecords().stream().filter(r -> r.getEstadoResidencia().equalsIgnoreCase("Paraí­ba")).count();
		TreeSet<String> bairros = new TreeSet<String>();
		TreeSet<String> loadedBairros = loadBairros();
		dados.getRecords().stream()
			.filter(r -> r.getEstadoResidencia().equals("Paraí­ba"))
			.filter(r -> r.getMunicipio().equals("Campina Grande"))
			.forEach(r -> bairros.add(r.getBairro()));
		System.out.println("Registros de CG: " + dados.getRecords().stream()
			.filter(r -> r.getEstadoResidencia().equals("Paraí­ba"))
			.filter(r -> r.getMunicipio().equals("Campina Grande"))
			.filter(r -> bairros.contains(r.getBairro())).count());
		System.out.println("Registros de CG confirmados: " + dados.getRecords().stream()
				.filter(r -> r.getEstadoResidencia().equals("Paraí­ba"))
				.filter(r -> r.getMunicipio().equals("Campina Grande"))
				.filter(r -> r.isResultadoTeste())
				.filter(r -> bairros.contains(r.getBairro())).count());
		System.out.println("Registros com data de notificacao nula: " + 
				dados.getRecords().stream()
				.filter(r -> r.getEstadoResidencia().equals("Paraí­ba"))
				.filter(r -> r.getMunicipio().equals("Campina Grande"))
				.filter(r -> r.getDataNotificacao() == null).count());
		TreeSet<String> bairrosDaBase = new TreeSet<String>();
		TreeSet<String> bairrosEncontrados = new TreeSet<String>();
		bairros.forEach(c -> {
			loadedBairros.forEach(b -> {
				double similarity = similarity(c, b);
				if(similarity > 0.7) {
					bairrosDaBase.add(c);
					bairrosEncontrados.add(b);
					//System.out.println( "(" + c + "," + b + ") : " + similarity);
				}
			});
		});
		
		
		System.out.println(loadedBairros.size() + " bairros carregados do arquivo");
		System.out.println(bairrosEncontrados.size() + " bairros encontrados na similaridade");
		loadedBairros.removeAll(bairrosEncontrados);
		System.out.println(loadedBairros.size() + " bairros da lista que sobraram na similaridade");
		//System.out.println("Registros filtrados: " + filtrados);
		//loadedBairros.forEach(b -> System.out.println(b));
		
		System.out.println("Bairros lidos da base: " + bairros.size());
		System.out.println("Bairros lidos da base e mapeados: " + bairrosDaBase.size());
		bairros.removeAll(bairrosDaBase);
		//bairros.stream().forEach(b -> System.out.println(b + " (" + dados.getRecords().stream().filter(r -> r.getBairro().equals(b)).count() + " ocorrencias)"));
		int soma = 0;
		for (String b : bairros) {
			soma += dados.getRecords().stream().filter(r -> r.getBairro().equals(b)).count();
		}
		//System.out.println("Soma das ocorrencias: " + soma);
		//System.out.println("Registros nao classificados de bairro: " + dados.getRecords().stream().filter(r -> bairros.contains(r.getBairro())).count());
		filterRecordsCG(dados.getRecords());
	}
}
