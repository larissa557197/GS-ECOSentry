package org.example.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CriaConexao {
    public static Connection conn; // Consigo criar apenas uma conexão ativa
    private static final String server = "oracle.fiap.com.br";
    private static final  String port = "1521";// Porta TCP padrão do Oracle
    private static final String sid = "orcl";//SID do banco
    private static String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + sid;
    private static final String user = "RM557197";
    private static final String passwd = "281205";

    public static Connection pegarConexao() throws SQLException {
        if (conn==null|| conn.isClosed()) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(url, user, passwd);
            } catch (ClassNotFoundException e) {
                System.out.println("Erro: Driver Oracle não encontrado.");
                throw new SQLException("Erro ao carregar o driver Oracle.", e);
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                throw e;
            }

        }
        return conn;
    }
}