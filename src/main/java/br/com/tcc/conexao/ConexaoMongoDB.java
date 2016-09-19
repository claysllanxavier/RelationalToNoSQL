package br.com.tcc.conexao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import javax.swing.JOptionPane;

public class ConexaoMongoDB {

    MongoClient mongoClient;
    DB db;

    public boolean conectar(String local, int porta) {
        Builder o = MongoClientOptions.builder().connectTimeout(3000);
        MongoClient mongo = new MongoClient(new ServerAddress(local, porta), o.build());
        try {
            mongo.getAddress();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dados Incorretos!"
                    + "\nFavor informar os dados corretos para conexão.", "ERRO", JOptionPane.ERROR_MESSAGE);
            mongo.close();
            return false;
        }
    }

    public void criarBanco(String nome) {
        db = mongoClient.getDB(nome);
        System.out.println("Connect to database successfully");

    }

    public void criarColecao(String nome) {
        DBCollection coll = db.createCollection(nome, new BasicDBObject());
        System.out.println("Coleção" + coll + "criada  com sucesso");

    }
}
