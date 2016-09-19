package br.com.tcc.auxiliares;

import br.com.tcc.bancoRelacional.Banco;
import br.com.tcc.bancoRelacional.Tabela;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ModeloTabela extends AbstractTableModel {

    private final ArrayList<ObjectTabela> dados;
    String[] columns = {"Nome Tabela", "Migrar"};

    public ModeloTabela() {
        super();
        dados = new ArrayList<>();
    }

    public void addRow(Banco bd) {
        ObjectTabela t;
        for (Tabela tabela : bd.getTabelas()) {
            t = new ObjectTabela();
            t.setNomeTabela(tabela.getNome());
            t.setMigrar(Boolean.FALSE);
            this.dados.add(t);
        }
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return dados.get(linha).getNomeTabela();
            case 1:
                return dados.get(linha).getMigrar();
        }
        return null;
    }

    public ArrayList<String> getTabelasNaoSelecionadas() {
        ArrayList<String> nomeTabelas = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            Boolean isChecked = Boolean.valueOf(getValueAt(i, 1).toString());
            if (!isChecked) {
                nomeTabelas.add(dados.get(i).getNomeTabela());
            }
        }
        return nomeTabelas;
    }
    
    public ArrayList<String> getTabelasSelecionadas() {
        ArrayList<String> nomeTabelas = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            Boolean isChecked = Boolean.valueOf(getValueAt(i, 1).toString());
            if (isChecked) {
                nomeTabelas.add(dados.get(i).getNomeTabela());
            }
        }
        return nomeTabelas;
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return coluna == 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
   public void setValueAt(Object valor, int linha, int coluna){
        if( valor == null) return;         
        if(coluna == 1){
            dados.get(linha).setMigrar((Boolean) valor);
        } 
        this.fireTableRowsUpdated(linha, linha);
    }
   
   public void selecionaTodas(){
       for(int i = 0; i < getRowCount(); i++){
        setValueAt(Boolean.TRUE, i, 1);
       }
       
   }
}
