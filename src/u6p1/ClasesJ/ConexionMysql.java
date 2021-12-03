/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package u6p1.ClasesJ;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reyes
 */
public class ConexionMysql {

    String usuario = "root",
            contraseña = "root",
            tabla = "",
            host = "localhost";
    Connection con;

    ConexionMysql(String tabla1) {
        tabla = tabla1;
    }

    public void conectar() {
        try {

            System.out.println("Conexion a la BD");
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + tabla, usuario, contraseña);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido la sig excepcion: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void desconectar() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
