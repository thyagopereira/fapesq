package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

enum Sexo{ 
	MASCULINO, FEMININO;
}
enum EstadoTeste{
	CONCLUIDO, SOLICITADO, COLETADO;
}
enum TipoTeste{
	RT_PCR,TESTE_RAPIDO_ANTICORPO, TESTE_RAPIDO_ANTIGENO, NAO_CLASSIFICADO;
}
public class Record {
	private int id;
	private String numeroNotificacao; //coluna 0
	private boolean profissionalAreaSaude; //coluna 1
	private TipoTeste tipoTeste; //coluna 5
	private String estadoResidencia; //coluna 6
	private GregorianCalendar dataNotificacao; //coluna 8
	private boolean febre; //coluna 11
	private boolean tosse; //coluna 12
	private boolean outros; //coluna 13
	private boolean dorGarganta; //coluna 14
	private boolean dispneia; //coluna 15
	private String CEP; //coluna 16
	private boolean resultadoTeste; //coluna 18. true eh positivo e false eh negativo
	private Sexo sexo; //coluna 20
	private EstadoTeste estadoTeste; //coluna 23
	private boolean doencaRespiratoriaDescompensada; //coluna 24
	private boolean doencaRenalAvancada; //coluna 25
	private boolean doencaCromossomicaImunologica; //coluna 26
	private boolean diabetes; //coluna 27
	private boolean imunossupressao; //coluna 28
	private boolean doencaCardiacaCronica; //coluna 29
	private boolean gestanteAltoRisco; //coluna 30
	private String bairro; //coluna 31
	private GregorianCalendar dataColetaTeste; //coluna 32
	private String descricaoSintoma; //coluna 34
	private GregorianCalendar dataEncerramento; //coluna 35
	private GregorianCalendar dataNascimento; //coluna 36
	private String municipio; //coluna 38
	private GregorianCalendar dataInicioSintomas; //coluna 44
	
	public Record() {
		super();
	}

		
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNumeroNotificacao() {
		return numeroNotificacao;
	}

	public void setNumeroNotificacao(String numeroNotificacao) {
		this.numeroNotificacao = numeroNotificacao;
	}

	public boolean isProfissionalAreaSaude() {
		return profissionalAreaSaude;
	}

	public void setProfissionalAreaSaude(boolean profissionalAreaSaude) {
		this.profissionalAreaSaude = profissionalAreaSaude;
	}

	public TipoTeste getTipoTeste() {
		return tipoTeste;
	}

	public void setTipoTeste(TipoTeste tipoTeste) {
		this.tipoTeste = tipoTeste;
	}

	public String getEstadoResidencia() {
		return estadoResidencia;
	}

	public void setEstadoResidencia(String estadoResidencia) {
		this.estadoResidencia = estadoResidencia;
	}

	public GregorianCalendar getDataNotificacao() {
		return dataNotificacao;
	}

	public void setDataNotificacao(GregorianCalendar dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}

	public boolean isFebre() {
		return febre;
	}

	public void setFebre(boolean febre) {
		this.febre = febre;
	}

	public boolean isTosse() {
		return tosse;
	}

	public void setTosse(boolean tosse) {
		this.tosse = tosse;
	}

	public boolean isOutros() {
		return outros;
	}

	public void setOutros(boolean outros) {
		this.outros = outros;
	}

	public boolean isDorGarganta() {
		return dorGarganta;
	}

	public void setDorGarganta(boolean dorGarganta) {
		this.dorGarganta = dorGarganta;
	}

	public boolean isDispneia() {
		return dispneia;
	}

	public void setDispneia(boolean dispneia) {
		this.dispneia = dispneia;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public boolean isResultadoTeste() {
		return resultadoTeste;
	}

	public void setResultadoTeste(boolean resultadoTeste) {
		this.resultadoTeste = resultadoTeste;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public boolean isDoencaRespiratoriaDescompensada() {
		return doencaRespiratoriaDescompensada;
	}

	public void setDoencaRespiratoriaDescompensada(boolean doencaRespiratoriaDescompensada) {
		this.doencaRespiratoriaDescompensada = doencaRespiratoriaDescompensada;
	}

	public boolean isDoencaRenalAvancada() {
		return doencaRenalAvancada;
	}

	public void setDoencaRenalAvancada(boolean doencaRenalAvancada) {
		this.doencaRenalAvancada = doencaRenalAvancada;
	}

	public boolean isDoencaCromossomicaImunologica() {
		return doencaCromossomicaImunologica;
	}

	public void setDoencaCromossomicaImunologica(boolean doencaCromossomicaImunologica) {
		this.doencaCromossomicaImunologica = doencaCromossomicaImunologica;
	}

	public boolean isDiabetes() {
		return diabetes;
	}

	public void setDiabetes(boolean diabetes) {
		this.diabetes = diabetes;
	}

	public boolean isImunossupressao() {
		return imunossupressao;
	}

	public void setImunossupressao(boolean imunossupressao) {
		this.imunossupressao = imunossupressao;
	}

	public boolean isDoencaCardiacaCronica() {
		return doencaCardiacaCronica;
	}

	public void setDoencaCardiacaCronica(boolean doencaCardiacaCronica) {
		this.doencaCardiacaCronica = doencaCardiacaCronica;
	}

	public boolean isGestanteAltoRisco() {
		return gestanteAltoRisco;
	}

	public void setGestanteAltoRisco(boolean gestanteAltoRisco) {
		this.gestanteAltoRisco = gestanteAltoRisco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public GregorianCalendar getDataColetaTeste() {
		return dataColetaTeste;
	}

	public void setDataColetaTeste(GregorianCalendar dataColetaTeste) {
		this.dataColetaTeste = dataColetaTeste;
	}

	public EstadoTeste getEstadoTeste() {
		return estadoTeste;
	}

	public void setEstadoTeste(EstadoTeste estadoTeste) {
		this.estadoTeste = estadoTeste;
	}

	public String getDescricaoSintoma() {
		return descricaoSintoma;
	}

	public void setDescricaoSintoma(String descricaoSintoma) {
		this.descricaoSintoma = descricaoSintoma;
	}

	public GregorianCalendar getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(GregorianCalendar dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public GregorianCalendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(GregorianCalendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public GregorianCalendar getDataInicioSintomas() {
		return dataInicioSintomas;
	}

	public void setDataInicioSintomas(GregorianCalendar dataInicioSintomas) {
		this.dataInicioSintomas = dataInicioSintomas;
	}
	public String getDataNotificacaoAsString() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
		return df.format(this.dataNotificacao.getTime());
	}
	
}
