package dados;

public class Voto {
    private int id;
    private int hora;
    private Candidato candidato;
    private Localidade localidade;

    public Voto(int id, int hora, Candidato candidato, Localidade localidade) {
        this.id = id;
        this.hora = hora;
        this.candidato = candidato;
        this.localidade = localidade;
    }

    public Candidato getCandidato() {
        return this.candidato;
    }
     
    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Localidade getLocalidade() {
        return this.localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHora() {
        return this.hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

}