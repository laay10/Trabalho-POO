package dados;

public class Governador extends Candidato {
    private String escolaridade;

    public Governador(int numero, String nome, Partido partido, Localidade localidade, String escolaridade) {
        super(numero, nome, partido, localidade);
        this.escolaridade = escolaridade;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    @Override
    public String getDescricaoCandidato() {
        return "Governador: " + this.getNome() + " - Escolaridade: " + this.escolaridade;
    }
}