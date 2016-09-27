package br.com.tcc.conexao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import java.util.Map;
import javax.swing.JOptionPane;

public class ConexaoMongoDB {

    private MongoClient mongoClient;
    private DB db;
    private DBCollection coll;

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

    public String criarBanco(String nome) {
        db = mongoClient.getDB(nome);
        return db.getName();
    }

    public String criarColecao(String nome) {
        this.coll = db.createCollection(nome, new BasicDBObject());
        return coll.getName();
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
     
    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public DB getDb() {
        return db;
    }
    
    public DBCollection getColl(String colecao) {
        coll = db.getCollection(colecao);
        return coll;
    }   
}
