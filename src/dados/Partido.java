package dados;

public class Partido {
    private int codigo;
    private String nome;

    public Partido(int codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void descricaoPartido() {
        System.out.println("Código: " + this.codigo);
        System.out.println("Nome: " + this.nome);
    }
}