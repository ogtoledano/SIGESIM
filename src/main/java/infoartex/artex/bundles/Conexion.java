/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infoartex.artex.bundles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author administrador
 */
public class Conexion {
    private String param;
    private String user;
    private String pass;
    private Connection conex;

    public Conexion(String dir, String user, String pass) throws SQLException {
        param="jdbc:postgresql:"+dir;
        this.user=user;
        this.pass=pass;
        conex=(Connection) DriverManager.getConnection(param, this.user, this.pass);
    }
    
    public Connection getConexion(){
        return this.conex;
    }
    
    
    
}
