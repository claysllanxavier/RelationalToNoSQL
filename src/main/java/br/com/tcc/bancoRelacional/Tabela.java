package br.com.tcc.bancoRelacional;


import java.util.ArrayList;

public class Tabela {
    private String nome;
    private ArrayList<Coluna> colunas = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Coluna> getColunas() {
        return colunas;
    }

    public void addColunas(Coluna coluna) {
        this.colunas.add(coluna);
    }
    
    public boolean possuiReferencia(){
        for(Coluna c: colunas){
            if(c.eChaveEstrangeira()){
                return true;
            }
        }
        return false;
    }
    public String tabelaReferenciada(){
        for(Coluna c: colunas){
            return c.getTabelaForeignKeyReferencia();
        }
        return "";
    }
}
