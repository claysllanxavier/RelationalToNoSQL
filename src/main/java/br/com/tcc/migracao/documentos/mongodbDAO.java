/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.migracao.documentos;

import br.com.tcc.auxiliares.MapaTabelas;
import br.com.tcc.bancoRelacional.Banco;
import br.com.tcc.bancoRelacional.Coluna;
import br.com.tcc.bancoRelacional.Tabela;
import br.com.tcc.conexao.relacional.Conexao;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author claysllanxavier
 */
public class mongodbDAO {

    private final MongoClient mongoClient;
    private DB db;
    private DBCollection coll;

    public mongodbDAO(MongoClient mongo) {
        this.mongoClient = mongo;
    }

    public void criarBanco(String nome) {
        db = mongoClient.getDB(nome);
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

    public void migrar(ArrayList<String> nomeTabelas, Conexao c, Banco bd) throws SQLException {
      
        while (!nomeTabelas.isEmpty()) {
            for (Tabela tabela : bd.getTabelas()) {
                if (tabela.possuiReferencia() && !nomeTabelas.contains(tabela.tabelaReferenciada())) {
                        String sql = "SELECT * FROM " + tabela.getNome();
                        try (PreparedStatement stmt = c.getC().prepareStatement(sql)) {
                            ResultSet resultado = stmt.executeQuery();
                            while (resultado.next()) {
                                save(tabela, resultado);
                            }
                        }
                }
                else if(tabela.possuiReferencia() && nomeTabelas.contains(tabela.tabelaReferenciada())){
                      String sql = "SELECT * FROM " + tabela.getNome();
                        try (PreparedStatement stmt = c.getC().prepareStatement(sql)) {
                            ResultSet resultado = stmt.executeQuery();
                            while (resultado.next()) {
                                save(tabela, resultado);
                            }
                        }
                }
            }
        }
    }

    public String selectOne(DBCollection collection, String nomeColuna, int valorColuna) {
        DBObject result = null;
        BasicDBObject obj = new BasicDBObject();
        obj.put(nomeColuna, valorColuna);
        DBCursor cursor = collection.find(obj);
        while (cursor.hasNext()) {
            result = cursor.next();
        }
        return result.get("_id").toString();
    }
}
