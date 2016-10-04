package br.com.tcc.bancoRelacional;

public class Coluna {

    private String nome;
    private String tipoColuna;
    private boolean chavePrimaria;
    private boolean isUnica = false;
    private boolean isChaveEstrangeira = false;
    private String tabelaForeignKeyReferencia;
    private String colunaForeignKeyReferencia;

    public String getColunaForeignKeyReferencia() {
        return colunaForeignKeyReferencia;
    }

    public void setColunaForeignKeyReferencia(String colunaForeignKeyReferencia) {
        this.colunaForeignKeyReferencia = colunaForeignKeyReferencia;
    }

    public boolean isChaveEstrangeira() {
        return isChaveEstrangeira;
    }

    public void seteChaveEstrangeira(boolean eChaveEstrangeira) {
        this.isChaveEstrangeira = eChaveEstrangeira;
    }

    public String getTabelaForeignKeyReferencia() {
        return tabelaForeignKeyReferencia;
    }

    public void setTabelaForeignKeyReferencia(String tabelaForeignKeyReferencia) {
        this.tabelaForeignKeyReferencia = tabelaForeignKeyReferencia;
    }

    public boolean isUnica() {
        return isUnica;
    }

    public void seteUnica(boolean eUnica) {
        this.isUnica = eUnica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoColuna() {
        return tipoColuna;
    }

    public void setTipoColuna(String tipoColuna) {
        this.tipoColuna = tipoColuna;
    }

    public boolean isChavePrimaria() {
        return chavePrimaria;
    }

    public void setChavePrimaria(boolean chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
    }
}
