package Services;

/** Uma receita/documento emitido, ligado a um atendimento (consulta). */
public class Receita {

    private String dataEmissao;
    private String medicamentos;

    public String getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(String dataEmissao) { this.dataEmissao = dataEmissao; }

    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
}
