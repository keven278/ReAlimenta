package app;

import util.Conexao;
import java.sql.Connection;
import java.DriveManager.*;


public class TesteConexao {
    public static void main(String[] args) {
        try{
            Connection conn = Conexao.getConnection();
                    System.out.println("Conexão realizada com sucesso!");
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
