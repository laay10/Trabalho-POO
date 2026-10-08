package dados;

public class Presidente extends Candidato {
    private double patrimonio;

    public Presidente(int numero, String nome, Partido partido, Localidade localidade, double patrimonio) {
        super(numero, nome, partido, localidade);
        this.patrimonio = patrimonio;
    }

    public double getPatrimonio() {
        return patrimonio;
    }

    @Override
    public String getDescricaoCandidato() {
        return "Presidente: " + this.getNome() + " - Patrimônio: " + this.patrimonio;
    }
}