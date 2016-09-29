package br.com.tcc.conexao.nosql.documentos;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import javax.swing.JOptionPane;

public class ConexaoMongoDB {

    private MongoClient mongoClient;
    private DB db;
    private DBCollection coll;

    public boolean conectar(String local, int porta) {
        Builder o = MongoClientOptions.builder().connectTimeout(3000);
        mongoClient = new MongoClient(new ServerAddress(local, porta), o.build());
        try {
            mongoClient.getAddress();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dados Incorretos!"
                    + "\nFavor informar os dados corretos para conexão.", "ERRO", JOptionPane.ERROR_MESSAGE);
            mongoClient.close();
            return false;
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
