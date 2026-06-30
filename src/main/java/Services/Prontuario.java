package Services;

public class Prontuario {

    private int idProntuario;
    private int idPaciente;
    private String historico;
    private String alergias;
    private String dataCriacao;
    private String pressaoArterial;
    private String frequenciaCardiaca;
    private String temperatura;
    private String exameFisico;
    private String cid10;
    private String conduta;

    public int getIdProntuario() { return idProntuario; }
    public void setIdProntuario(int idProntuario) { this.idProntuario = idProntuario; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public String getHistorico() { return historico; }
    public void setHistorico(String historico) { this.historico = historico; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }

    public String getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(String dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getPressaoArterial() { return pressaoArterial; }
    public void setPressaoArterial(String pressaoArterial) { this.pressaoArterial = pressaoArterial; }

    public String getFrequenciaCardiaca() { return frequenciaCardiaca; }
    public void setFrequenciaCardiaca(String frequenciaCardiaca) { this.frequenciaCardiaca = frequenciaCardiaca; }

    public String getTemperatura() { return temperatura; }
    public void setTemperatura(String temperatura) { this.temperatura = temperatura; }

    public String getExameFisico() { return exameFisico; }
    public void setExameFisico(String exameFisico) { this.exameFisico = exameFisico; }

    public String getCid10() { return cid10; }
    public void setCid10(String cid10) { this.cid10 = cid10; }

    public String getConduta() { return conduta; }
    public void setConduta(String conduta) { this.conduta = conduta; }
}