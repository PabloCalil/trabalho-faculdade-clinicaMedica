package Services;

public class Consulta {

    private int idConsulta;
    private String dataHora;
    private String status;
    private String nomePaciente;
    private String nomeConvenio;
    private int idPaciente;
    

    public int getIdConsulta() { return idConsulta; }
    public void setIdConsulta(int idConsulta) { this.idConsulta = idConsulta; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNomePaciente() { return nomePaciente; }
    public void setNomePaciente(String nomePaciente) { this.nomePaciente = nomePaciente; }

    public String getNomeConvenio() { return nomeConvenio; }
    public void setNomeConvenio(String nomeConvenio) { this.nomeConvenio = nomeConvenio; }

public int getIdPaciente() {
    return idPaciente;
}

public void setIdPaciente(int idPaciente) {
    this.idPaciente = idPaciente;
}
}