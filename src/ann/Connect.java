/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author David
 */
public class Connect {
    private static Connection conn;
    
    public static Connection getConn() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ipm", "root", "");
        }
        return conn;
    }
}
