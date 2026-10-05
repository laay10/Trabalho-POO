public enum TipoLocalidade {

    NACIONAL("Nacional"),
    ESTADUAL("Estadual"),
    MUNICIPAL("Municipal");

    private String descricao;

    TipoLocalidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}