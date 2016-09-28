package br.com.tcc.conexao.relacional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Trabalho desenvolvido para otenção de nota na disciplina de TCC Faculdade
 * Católica do Tocantins Sistemas de Informação
 *
 * @author Claysllan Xavier
 * @version 1.0
 */
public final class Conexao {

    String local;
    String usuario;
    String senha;
    Connection c;
    Statement statment;
    String str_conexao;
    String driverjdbc;

    public Conexao(){}
    
    public Conexao(String tipoBanco, String local, String porta,
            String nomeBanco, String usuario, String senha) {
        if (tipoBanco.equals("PostgreSql")) {
            setLocal(local);
            setUsuario(usuario);
            setSenha(senha);
            setStr_conexao("jdbc:postgresql://" + local + ":" + porta + "/" + nomeBanco);
            setDriverjdbc("org.postgresql.Driver");;

        } else if (tipoBanco.equals("MySQL")) {
            setLocal(local);
            setUsuario(usuario);
            setSenha(senha);
            setStr_conexao("jdbc:mysql://" + local + ":" + porta + "/" + nomeBanco+"?autoReconnect=true&useSSL=false");
            setDriverjdbc("com.mysql.jdbc.Driver");
        }

    }

    public boolean conectar() {
        try {
            Class.forName(getDriverjdbc());
            setC(DriverManager.getConnection(getStr_conexao(), getUsuario(), getSenha()));
            setStatment(getC().createStatement());
           JOptionPane.showMessageDialog (null, "O banco de dados foi conectado com sucesso!", "Conexão", JOptionPane.INFORMATION_MESSAGE);
           return true;

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Dados Incorretos!"
                    + "\nFavor informar os dados corretos para conexão.", "ERRO", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void desconectar() {
        try {
            getC().close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Connection getC() {
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public Statement getStatment() {
        return statment;
    }

    public void setStatment(Statement statment) {
        this.statment = statment;
    }

    public String getStr_conexao() {
        return str_conexao;
    }

    public void setStr_conexao(String str_conexao) {
        this.str_conexao = str_conexao;
    }

    public String getDriverjdbc() {
        return driverjdbc;
    }

    public void setDriverjdbc(String driverjdbc) {
        this.driverjdbc = driverjdbc;
    }
}
