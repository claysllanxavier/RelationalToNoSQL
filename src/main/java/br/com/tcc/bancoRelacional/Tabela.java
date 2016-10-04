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
}
