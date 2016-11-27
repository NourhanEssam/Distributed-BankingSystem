/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.bankingsystem.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author Essam
 */
class ClientHandler  extends Thread {

    //field
    Socket c;

    public ClientHandler(Socket c) {
        this.c = c;
    }
    
    
    @Override
    public void run() {

        try {
            boolean endconn = false;
            DataInputStream dis = new DataInputStream(c.getInputStream());
            DataOutputStream dos = new DataOutputStream(c.getOutputStream());
            while (true) {
                String request = dis.readUTF();
                String[] parsedRequest = request.split(",");
                switch (parsedRequest[0]) {
                    case "0": // Login -> Username,Password,0 .. if -1 re-enter the data
                        dos.writeUTF(Login (parsedRequest[1],parsedRequest[2]));
                        System.out.println("case 0");
                    break;
                    case "1": //Checkamount -> ID,1 .. if -1 then empty
                        dos.writeUTF(CheckBalance(parsedRequest[1]));
                        System.out.println("case 1");
                    break;
                    case "2": //Deposite -> ID,amount,2
                        dos.writeUTF(Deposite(parsedRequest[1], parsedRequest[2]));
                        System.out.println("case 2");
                    break;
                    case "3": //Deposite -> ID,amount,2
                        dos.writeUTF(Withdraw(parsedRequest[1], parsedRequest[2]));
                        System.out.println("case 3");
                    break;
                    case "4": //Deposite -> ID,amount,2
                        dos.writeUTF(TransferIn(parsedRequest[1], parsedRequest[2], parsedRequest[3]));
                        System.out.println("case 4");
                    break;
                    default:
                        endconn = true;
                        System.out.println("case bye");
                    break;
                }
                if (endconn) {
                    dos.writeUTF("bye");
                    break;
                }
            }
            
            c.close();
            dis.close();
            dos.close();


            /*
            DBConnect connect = new DBConnect();
            String result = connect.getData("SELECT * FROM APP.users","username");
            String result2 = connect.getData("SELECT * FROM APP.users WHERE username = 'Ahmed'","ID");
            
            String[] data2 = result2.split(",");
            for(int i=0;i<data2.length;i++)
            System.out.println(data2[i]);
            
            
            // parse string
            String[] data = result.split(",");
*/
        } 
        catch (Exception e) 
        {
            System.out.println("Error " +e);
        }

    }
    
        String Login (String Username, String Password)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE username = '" + Username +"' and password = '" +Password +"'","ID");
        String[] splittedreply = databaseReply.split(",");
        if(splittedreply[0] == "") return "-1";
        return splittedreply[0];
    }
    
    String CheckBalance (String ID)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE ID = " + ID ,"amount");
        String[] splittedreply = databaseReply.split(",");
        if(splittedreply[0] == "") return "-1";
        return splittedreply[0];
    }
    
    String Deposite (String ID, String amount)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE ID = " + ID ,"amount");
        String[] splittedreply = databaseReply.split(",");
        float newamount;
        if(splittedreply[0] == "") return "-1";
        newamount = Float.parseFloat(splittedreply[0]) + Float.parseFloat(amount);
        connect.updateData("UPDATE APP.users SET amount = " + Float.toString(newamount) + "WHERE ID = " + ID );
        return Float.toString(newamount);
    }
    
    String Withdraw (String ID, String amount)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE ID = " + ID ,"amount");
        String[] splittedreply = databaseReply.split(",");
        float newamount;
        if(splittedreply[0] == "") return "-1";
        if(Float.parseFloat(splittedreply[0]) < Float.parseFloat(amount))
            return "-1";
        else
            newamount = Float.parseFloat(splittedreply[0]) - Float.parseFloat(amount);
        connect.updateData("UPDATE APP.users SET amount = " + Float.toString(newamount) + "WHERE ID = " + ID );
        return Float.toString(newamount);
    }
    
    String TransferIn (String ID1, String ID2, String amount)
    {
        String Balance = Withdraw(ID1, amount);
        if(Balance != "-1") Deposite(ID2, amount);
        return Balance;
    }
    
    float TransferOut (int IDIn, int IDOut, float amount)
    {
        float Balance=0;
        
        return Balance;
    }

}