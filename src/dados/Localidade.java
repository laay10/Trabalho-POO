public class Localidade { 
    private String cep;
    private String nome;
    private long qtdEleitores;


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

}