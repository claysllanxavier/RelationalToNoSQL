package br.com.tcc.bancoRelacional;


import java.util.ArrayList;

public class Banco {
    ArrayList<Tabela> tabelas = new ArrayList<>();
    
    public ArrayList<Tabela> getTabelas() {
        return tabelas;
    }

    public void addTabela(Tabela tabela) {
        this.tabelas.add(tabela);
    }

    public void removeTabela(String nomeTabela){
        for (int i = 0; i < tabelas.size(); i++) {
            if(tabelas.get(i).getNome().equalsIgnoreCase(nomeTabela)){
                tabelas.remove(i);
            }
        }
    }
    public void imprimeTabelas(){
        for(Tabela t : tabelas){
            System.out.println(t.getNome());
        }
    }
}
