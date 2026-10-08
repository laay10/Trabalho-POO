package dados;

public class Localidade { 
    private String cep;
    private String nome;
    private long qtdEleitores;
    private TipoLocalidade tipoLocalidade;

    public Localidade(String cep, String nome, long qtdEleitores, TipoLocalidade tipoLocalidade){
        this.cep = cep;
        this.nome = nome;
        this.qtdEleitores = qtdEleitores;
        this.tipoLocalidade = tipoLocalidade;
    }
    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getQtdEleitores() {
        return this.qtdEleitores;
    }

    public void setQtdEleitores(long qtdEleitores) {
        this.qtdEleitores = qtdEleitores;
    }

    public void setTipoLocalidade(TipoLocalidade tipoLocalidade) {
        this.tipoLocalidade = tipoLocalidade;
    }

    public TipoLocalidade getTipoLocalidade(){
        return tipoLocalidade;
    }

}