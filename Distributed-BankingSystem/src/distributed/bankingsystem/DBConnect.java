/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.bankingsystem;

import java.sql.*;

/**
 *
 * @author Essam
 */
public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public  DBConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/DB");
            st = con.createStatement();
        }
        catch(Exception ex)
        {
            System.out.println("Error"+ex);
        }
    }
    
    public void getData() {
        try
        {
            String Query  = "SELECT * FROM APP.HEY";
            rs = st.executeQuery(Query);
            System.out.println("Records from Database");
            while(rs.next())
            {
                String Name = rs.getString("P1");
                System.out.println("P1 "+Name);
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error"+ex);
        }
    }
    
}
