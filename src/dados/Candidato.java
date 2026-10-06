package dados;

public abstract class Candidato {
    private int numero;
    private String nome;
    private Partido partido;
    private Localidade localidade;

    public Candidato(int numero, String nome, Partido partido, Localidade localidade){
        this.numero = numero;
        this.nome = nome;
        this.partido = partido;
        this.localidade = localidade;
    }

    public void setPartido (Partido partido){
        this.partido = partido;
    }

    public Partido getPartido () {
        return partido;
    }

    public void setLocalidade(Localidade localidade){
        this.localidade = localidade;
    }

    public Localidade getLocalidade(){
        return localidade;
    }
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract String getDescricaoCandidato();
}