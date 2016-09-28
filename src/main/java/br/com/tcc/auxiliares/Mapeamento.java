package br.com.tcc.auxiliares;

import br.com.tcc.bancoRelacional.*;
import br.com.tcc.conexao.relacional.Conexao;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Trabalho desenvolvido para otenção de nota na disciplina de TCC Faculdade
 * Católica do Tocantins Sistemas de Informação
 *
 * @author Claysllan Xavier
 * @version 1.0
 */
public class Mapeamento {

    static DatabaseMetaData metadata = null;

    public Mapeamento(Conexao c) throws SQLException {
        Connection connection = c.getC();
        metadata = connection.getMetaData();
    }

    public String imprmirInformacoesMetadata() throws SQLException {
        String informacoes;
        informacoes = "<html>Banco de Dados: " + metadata.getDatabaseProductName() + "<br />"
                + "Versão: " + metadata.getDatabaseProductVersion() + "<br />"
                + "Usuário: " + metadata.getUserName() + "<br />" + "</html>";
        return informacoes;
    }

    public Banco mapearBanco() throws SQLException {
        Banco bd = new Banco();
        ResultSet resultadoTabelas = metadata.getTables(null, null, null, null);
        while (resultadoTabelas.next()) {
            Tabela tabela = new Tabela();
            tabela.setNome(resultadoTabelas.getString("TABLE_NAME"));
            ResultSet resultadoColunas = metadata.getColumns(null, null, tabela.getNome(), null);

            ResultSet chavesEstrangeiras = metadata.getImportedKeys(null, null, resultadoTabelas.getString("TABLE_NAME"));
            ArrayList<String> ColunasContemChaveEstrangeira = new ArrayList<>();
            ArrayList<String> colunasReferenciadas = new ArrayList<>();
            ArrayList<String> tabelasReferenciadas = new ArrayList<>();
            while (chavesEstrangeiras.next()) {
                String tabelaChaveEstrangeira = chavesEstrangeiras.getString("FKTABLE_NAME");
                String colunaChaveEstrangeira = chavesEstrangeiras.getString("FKCOLUMN_NAME");
                String tabelaReferenciadaChaveEstrangeira = chavesEstrangeiras.getString("PKTABLE_NAME");
                String colunaReferenciadaChaveEstrangeira = chavesEstrangeiras.getString("PKCOLUMN_NAME");
                ColunasContemChaveEstrangeira.add(colunaChaveEstrangeira);
                colunasReferenciadas.add(colunaReferenciadaChaveEstrangeira);
                tabelasReferenciadas.add(tabelaReferenciadaChaveEstrangeira);
            }

            while (resultadoColunas.next()) {
                Coluna coluna = new Coluna();
                coluna.setNome(resultadoColunas.getString("COLUMN_NAME"));
                coluna.setTipoColuna(resultadoColunas.getString("TYPE_NAME"));
                if (resultadoColunas.getString("IS_AUTOINCREMENT").equals("YES")) {
                    coluna.setChavePrimaria(true);
                }
                ResultSet index_information = metadata.getIndexInfo(null, null, tabela.getNome(), true, true);
                /*Percorre a lista de índices da tabela*/
                while (index_information.next()) {

                    String index_column = index_information.getString("COLUMN_NAME");
                    if (index_column.equals(resultadoColunas.getString("COLUMN_NAME"))) {
                        coluna.seteUnica(true);
                    }
                }

                for (int i = 0; i < ColunasContemChaveEstrangeira.size(); i++) {
                    if (coluna.getNome().equals(ColunasContemChaveEstrangeira.get(i))) {
                        coluna.seteChaveEstrangeira(true);
                        coluna.setColunaForeignKeyReferencia(colunasReferenciadas.get(i));
                        coluna.setTabelaForeignKeyReferencia(tabelasReferenciadas.get(i));
                    }
                }

                tabela.addColunas(coluna);
            }
            bd.addTabela(tabela);
        }
        return bd;
    }
}
