package distributed.bankingsystem.Server;

import distributed.bankingsystem.AESencrp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;

class ClientHandler  extends Thread {
    Socket c;
    
    public ClientHandler(Socket c) {
        this.c = c;
    }
    
    @Override
    public synchronized void run() {
        try {
            boolean endconn = false;
            DataInputStream dis = new DataInputStream(c.getInputStream());
            DataOutputStream dos = new DataOutputStream(c.getOutputStream());
            while (true) {
                String request = AESencrp.decrypt(dis.readUTF());
                System.out.println(request);
                String[] parsedRequest = request.split(",");
                switch (parsedRequest[0]) {
                    case "0": //Login
                        dos.writeUTF(AESencrp.encrypt(Login (parsedRequest[1],parsedRequest[2])));
                        break;
                    case "1": //Checkamount
                        dos.writeUTF(AESencrp.encrypt(CheckBalance(parsedRequest[1])));
                        break;
                    case "2": //Deposite
                        String deposite = Deposite(parsedRequest[1], parsedRequest[2]);
                        dos.writeUTF(AESencrp.encrypt(deposite));
                        if (!deposite.equals("-1"))
                            SaveInDB(parsedRequest[1], "Deposite", parsedRequest[2]);
                        break;
                    case "3": //Withdraw
                        String withdraw = Withdraw(parsedRequest[1], parsedRequest[2]);
                        dos.writeUTF(AESencrp.encrypt(withdraw));
                        if (!withdraw.equals("-1"))
                            SaveInDB(parsedRequest[1], "Withdraw", parsedRequest[2]);
                        break;
                    case "4": //Transfer In
                        String TransferIn = TransferIn(parsedRequest[1], parsedRequest[2], parsedRequest[3]);
                        dos.writeUTF(AESencrp.encrypt(TransferIn));
                        if (!TransferIn.equals("-1"))
                        {
                            SaveInDB(parsedRequest[1], "Transfer To "+parsedRequest[2], parsedRequest[3]);
                            SaveInDB(parsedRequest[2], "Transfer From "+parsedRequest[1], parsedRequest[3]);
                        }
                        break;
                    case "5": //Transfer Out
                        String TransferOut = TransferOut(parsedRequest[1], parsedRequest[2], parsedRequest[3],parsedRequest[4]);
                        dos.writeUTF(AESencrp.encrypt(TransferOut));
                        if (!TransferOut.equals("-1"))
                        {
                            SaveInDB(parsedRequest[2], "Transfer To "+parsedRequest[3]+" In Bank "+parsedRequest[1], parsedRequest[4]);
                        }
                        break;
                    case "6": //Handle Transfer from Outside
                        String Tout = Deposite(parsedRequest[1], parsedRequest[2]);
                        if (!Tout.equals("-1")) SaveInDB(parsedRequest[1], "Transfer From "+parsedRequest[3]+" From Another Bank", parsedRequest[2]);
                        break;
                    case "7": //View History
                        dos.writeUTF(AESencrp.encrypt(TransferHistory(parsedRequest[1], parsedRequest[2], parsedRequest[3], parsedRequest[4], parsedRequest[5])));
                        break;
                    case "8":
                        dos.writeUTF(AESencrp.encrypt(SignUp (parsedRequest[1],parsedRequest[2],parsedRequest[3])));
                        break;
                    default:
                        endconn = true;
                        break;
                }
                if (endconn) {
                    dos.writeUTF(AESencrp.encrypt("bye"));
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
    
    synchronized void SaveInDB (String ID,String type, String amount)
    {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms").format(new java.util.Date());
        DBConnect connect = new DBConnect();
        connect.updateData("INSERT INTO App.THistory (UID,DateTime,Type,Amonut) VALUES ( "+ ID + "," + "'" + timeStamp + "'" + "," + "'" + type + "'" + "," + amount + ")");
    }
    
    synchronized String Login (String Username, String Password)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE username = '" + Username +"' and password = '" +Password +"'","ID");
        String[] splittedreply = databaseReply.split(",");
        if(splittedreply[0].equals("")) return "-1";
        return splittedreply[0];
    }
    
    synchronized String CheckBalance (String ID)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE ID = " + ID ,"amount");
        String[] splittedreply = databaseReply.split(",");
        if(splittedreply[0].equals("")) return "-1";
        return splittedreply[0];
    }
    
    synchronized String Deposite (String ID, String amount)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE ID = " + ID ,"amount");
        String[] splittedreply = databaseReply.split(",");
        float newamount;
        if(splittedreply[0].equals("")) return "-1";
        newamount = Float.parseFloat(splittedreply[0]) + Float.parseFloat(amount);
        connect.updateData("UPDATE APP.users SET amount = " + Float.toString(newamount) + "WHERE ID = " + ID );
        return Float.toString(newamount);
    }
    
    synchronized String Withdraw (String ID, String amount)
    {
        DBConnect connect = new DBConnect();
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE ID = " + ID ,"amount");
        String[] splittedreply = databaseReply.split(",");
        float newamount;
        if(splittedreply[0].equals("")) return "-1";
        if(Float.parseFloat(splittedreply[0]) < Float.parseFloat(amount))
            return "-1";
        else
            newamount = Float.parseFloat(splittedreply[0]) - Float.parseFloat(amount);
        connect.updateData("UPDATE APP.users SET amount = " + Float.toString(newamount) + "WHERE ID = " + ID );
        return Float.toString(newamount);
    }
    
    synchronized String TransferIn (String ID1, String ID2, String amount)
    {
        String Balance = Withdraw(ID1, amount);
        if(!Balance.equals("-1")) Deposite(ID2, amount);
        return Balance;
    }
    
    synchronized String TransferOut (String BankID,String IDIn, String IDOut, String amount)
    {
        String Balance = Withdraw(IDIn, amount);
        if(!Balance.equals("-1"))
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
                dosc.writeUTF(AESencrp.encrypt("6"+","+IDOut+","+amount+","+IDIn));
                client.close();
            }
            catch (Exception e)
            {
                System.out.println("Error " +e);
            }
        }
        return Balance;
    }
    
    synchronized String TransferHistory(String ID, String Y1, String M1, String Y2, String M2)
    {
        DBConnect connect = new DBConnect();
        Integer m1 = Integer.parseInt(M1) + 1;
        Integer m2 = Integer.parseInt(M2) + 1;
        String TS1 = Y1+"-"+m1+"-01 00:00:00";
        String TS2 = Y2+"-"+m2+"-01 00:00:00";
        String databaseReply_dateTime = connect.getData("SELECT * FROM App.THistory WHERE DateTime >= "+"'"+TS1+"'"+" AND DateTime < "+"'"+TS2+"'"+" AND UID = "+ID,"DateTime");
        String[] splittedreply_dateTime = databaseReply_dateTime.split(",");
        String databaseReply_Type = connect.getData("SELECT * FROM App.THistory WHERE DateTime >= "+"'"+TS1+"'"+" AND DateTime < "+"'"+TS2+"'"+" AND UID = "+ID,"Type");
        String[] splittedreply_Type = databaseReply_Type.split(",");
        String databaseReply_amt = connect.getData("SELECT * FROM App.THistory WHERE DateTime >= "+"'"+TS1+"'"+" AND DateTime < "+"'"+TS2+"'"+" AND UID = "+ID,"Amonut");
        String[] splittedreply_amt = databaseReply_amt.split(",");
        String databaseReply = "";
        String Item;
        for(int i=0;i<splittedreply_Type.length;i++)
        {
            Item = splittedreply_Type[i]+","+splittedreply_amt[i]+","+splittedreply_dateTime[i];
            databaseReply = databaseReply+ Item+";";
        }
        if(databaseReply.equals("")) return "-1";
        return databaseReply;
    }
    synchronized  String SignUp(String Username, String Password, String Amount)
    {
        DBConnect connect = new DBConnect();
        String ID = connect.getData("SELECT MAX(ID) FROM App.users","1");
        String[] splittedreply = ID.split(",");
        if(splittedreply[0].equals("")) splittedreply[0] = "0";
        System.out.println(ID+" "+splittedreply[0]);
        int lastID=Integer.parseInt(splittedreply[0]);
        lastID++;
        
        String databaseReply = connect.getData("SELECT * FROM APP.users WHERE username = '" + Username+"'","ID");
        String[] splittedreplydb = databaseReply.split(",");
        if(!splittedreplydb[0].equals("")) return "-1";
        
        connect.updateData("INSERT INTO APP.users VALUES(" + lastID + "," + "'" + Username + "'," + "'" + Password + "'," +  Amount + ")");
        return "1";
    }
}