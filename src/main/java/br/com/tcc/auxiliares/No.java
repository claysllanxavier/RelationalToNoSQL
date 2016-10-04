package br.com.tcc.auxiliares;

import java.util.ArrayList;

/**
 *
 * @author claysllanxavier
 */
public class No {

    private String nomeTabela;
    private final ArrayList<No> filho = new ArrayList<>();
    private final No pai;

    public No(No parent) {
        this.pai = parent;
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    public ArrayList<No> getFilho() {
        return filho;
    }

    public No getPai() {
        return pai;
    }

    public No addFilho(No pai, String nomeTabela) {
        No no = new No(pai);
        no.setNomeTabela(nomeTabela);
        pai.getFilho().add(no);
        return no;
    }
    public void printTree(No no, String appender) {
        System.out.println(appender + no.getNomeTabela());
        for (No each : no.getFilho()) {
            printTree(each, appender + appender);
        }
    }

    public No buscaPai(String nomeTabela, No no) {
        if (no.getNomeTabela().equalsIgnoreCase(nomeTabela)) {
            return no;
        }
        No res = null;
        for (int i = 0; res == null && i < no.getFilho().size(); i++) {
            res = buscaPai(nomeTabela, no.getFilho().get(i));
        }
        return res;
    }
}