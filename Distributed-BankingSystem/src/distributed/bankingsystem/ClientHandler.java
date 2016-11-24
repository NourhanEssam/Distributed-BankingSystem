/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.bankingsystem;

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

            //3.create comm channel
            DataInputStream dis
                    = new DataInputStream(c.getInputStream());
            DataOutputStream dos
                    = new DataOutputStream(c.getOutputStream());
            while (true) {
                    //4.perform I/O
                //a.asks for account no.
                dos.writeUTF("Please enter account No.");
                String accountNo = dis.readUTF();
                //perform check with DB
                dos.writeUTF("Valid Account No." + accountNo
                        + "\nEnter Password");
                String psword = dis.readUTF();
                //perform check with DB
                dos.writeUTF("correct password" + psword
                        + "\nEnter Payment Amount");
                String paymentAmount = dis.readUTF();
                //perform check balance
                dos.writeUTF("Payment successful amount =" + paymentAmount
                        + "\nDo you want to perform another payment[Y/N]?");
                String choice = dis.readUTF();

                if (choice.equalsIgnoreCase("N")) {
                    dos.writeUTF("bye");
                    break;
                }
            }

            //5.terminate connection with client
            c.close();
            dis.close();
            dos.close();

        } 
        catch (Exception e) 
        {
            System.out.println("Something went wrong");
        }

    }

}