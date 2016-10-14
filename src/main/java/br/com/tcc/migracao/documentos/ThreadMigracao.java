package br.com.tcc.migracao.documentos;

import br.com.tcc.auxiliares.No;
import br.com.tcc.bancoRelacional.Banco;
import br.com.tcc.conexao.relacional.Conexao;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claysllanxavier
 */
public class ThreadMigracao implements Runnable {

    private final MongodbDAO mongo;
    private final Conexao c;
    private final Banco banco;
    private final String nomeBD;
    private final No arvore;
    

    public ThreadMigracao(MongodbDAO mongo, Conexao conexaoRelacional, Banco bd, String nomeBD, No arvore) {
        this.mongo = mongo;
        this.c = conexaoRelacional;
        this.banco = bd;
        this.nomeBD = nomeBD;
        this.arvore = arvore;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            mongo.migrarDados(c, banco, nomeBD, arvore);
            mongo.trataRelacionamentos(banco, arvore, nomeBD);
        } catch (SQLException | InterruptedException ex) {
            Logger.getLogger(ThreadMigracao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
