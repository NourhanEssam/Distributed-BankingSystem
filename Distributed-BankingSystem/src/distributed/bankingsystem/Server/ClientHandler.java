package distributed.bankingsystem.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class ClientHandler  extends Thread {
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
                    case "0": //Login
                        dos.writeUTF(Login (parsedRequest[1],parsedRequest[2]));
                        System.out.println("case 0");
                    break;
                    case "1": //Checkamount
                        dos.writeUTF(CheckBalance(parsedRequest[1]));
                        System.out.println("case 1");
                    break;
                    case "2": //Deposite
                        dos.writeUTF(Deposite(parsedRequest[1], parsedRequest[2]));
                    break;
                    case "3": //Withdraw
                        dos.writeUTF(Withdraw(parsedRequest[1], parsedRequest[2]));
                    break;
                    case "4": //Transfer In
                        dos.writeUTF(TransferIn(parsedRequest[1], parsedRequest[2], parsedRequest[3]));
                    break;
                    case "5": //Transfer Out
                        dos.writeUTF(TransferOut(parsedRequest[1], parsedRequest[2], parsedRequest[3],parsedRequest[4]));
                    break;
                    case "6": //Handle Transfer from Outside
                        Deposite(parsedRequest[1], parsedRequest[2]);
                    break;
                    case "7": //View History

                    break;
                    default:
                        endconn = true;
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
    
    String TransferOut (String BankID,String IDIn, String IDOut, String amount)
    {
        String Balance = Withdraw(IDIn, amount);
        System.out.println("distributed.bankingsystem.Server.ClientHandler.TransferOut()..");
        if(Balance != "-1") 
        {
            try{
                
            DBConnect connect = new DBConnect();
            String databaseReply = connect.getData("SELECT * FROM APP.banks WHERE ID = " + BankID ,"IP");
            String[] splittedreply = databaseReply.split(",");
            String IP = splittedreply[0];
            databaseReply = connect.getData("SELECT * FROM APP.banks WHERE ID = " + BankID ,"PNO");
            splittedreply = databaseReply.split(",");
            Integer PNO = Integer.parseInt(splittedreply[0]);
            Socket client = new Socket(IP, PNO);
            DataInputStream disc = new DataInputStream(client.getInputStream());
            DataOutputStream dosc = new DataOutputStream(client.getOutputStream());
            dosc.writeUTF("6"+","+IDOut+","+amount);
            client.close();
            }
            catch (Exception e) 
            {
            System.out.println("Error " +e);
            }
        }
        return Balance;
    }
}