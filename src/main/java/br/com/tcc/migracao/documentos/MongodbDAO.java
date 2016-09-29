/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.migracao.documentos;

import br.com.tcc.auxiliares.MapaTabelas;
import br.com.tcc.bancoRelacional.Banco;
import br.com.tcc.bancoRelacional.Tabela;
import br.com.tcc.conexao.relacional.Conexao;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author claysllanxavier
 */
public class MongodbDAO {

    private final MongoClient mongoClient;
    private DB db;
    private DBCollection coll;

    public MongodbDAO(MongoClient mongo) {
        this.mongoClient = mongo;
    }

    public void criarBanco(String nome) {
        this.db = mongoClient.getDB(nome);
    }

    public void criarColecao(String nome) {
        this.coll = db.createCollection(nome, new BasicDBObject());
    }

    public DB getDb() {
        return db;
    }

    public DBCollection getColl(String colecao) {
        coll = db.getCollection(colecao);
        return coll;
    }

    public void save(Tabela tabela, ResultSet tupla) throws SQLException {
        Map<String, Object> mapTabela = new MapaTabelas().converterToMap(tabela, tupla);
        getColl(tabela.getNome()).insert(new BasicDBObject(mapTabela));
    }

    public void migrarDados(Conexao c, Banco banco, String nomeBanco) throws SQLException {
        criarBanco(nomeBanco);
        for (Tabela tabela : banco.getTabelas()) {
            criarColecao(tabela.getNome());
            int valor_salvo = 1;
            int subPartes = 10;
            long inicio = 0;

            long total = 20198310;
            long sub_total = total / subPartes;

            for (int i = 0; i < subPartes; i++) {
                String sql = "SELECT * FROM " + tabela.getNome() + " LIMIT " + inicio + "," + sub_total;
                try (PreparedStatement stmt = c.getC().prepareStatement(sql)) {
                    ResultSet resultado = stmt.executeQuery();
                    while (resultado.next()) {
                        save(tabela, resultado);
                        valor_salvo++;
                    }
                }
                inicio = inicio + sub_total;
            }
        }
    }
}
