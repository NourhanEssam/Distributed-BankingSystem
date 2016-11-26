/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed.bankingsystem;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

/**
 *
 * @author Essam
 */
public class Server extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
        try {
            DBConnect connect = new DBConnect();
            connect.getData("SELECT * FROM APP.HEY");
            
            //1.Create Server Socket
            ServerSocket server = new ServerSocket(1234);
            while (true) {
                //2.accept connection
                Socket c = server.accept();
                System.out.println("Client Arrived");
                ClientHandler ch = new ClientHandler(c);
                ch.start();

            }
            //6.Close the server if needed
            //server.close();

        } catch (Exception e) {
            System.out.println("Something Went Wrong");
        }
    }

    int Login (String Username, String Password)
    {
        int ID=0;
        
        return ID;
    }
    
    float CheckBalance (int ID)
    {
        float Balance=0;
        
        return Balance;
    }
    
    float Deposite (int ID, float amount)
    {
        float Balance=0;
        
        return Balance;
    }
    
    float Withdraw (int ID, float amount)
    {
        float Balance=0;
        
        return Balance;
    }
    
    float TransferIn (int ID, float amount)
    {
        float Balance=0;
        
        return Balance;
    }
    
    float TransferOut (int IDIn, int IDOut, float amount)
    {
        float Balance=0;
        
        return Balance;
    }
    
    // View History To Be Implemented after the database
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
