package distributed.bankingsystem.Server;

import java.sql.*;

public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public  DBConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Bank");
            st = con.createStatement();
        }
        catch(Exception ex)
        {
            System.out.println("Error"+ex);
        }
    }
    
    public String getData(String Query, String Column) {
        String result = "";
        try
        {
            rs = st.executeQuery(Query);
            while(rs.next())
            {
                result += rs.getString(Column);
                result += ",";
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error"+ex);
        }
        return result;
    }
    
    public void updateData(String Query) {
        try
        {
            st.executeUpdate(Query);
        }
        catch(Exception ex)
        {
            System.out.println("Error"+ex);
        }
    }
}
