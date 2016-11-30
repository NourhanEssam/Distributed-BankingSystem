package distributed.bankingsystem.Client;
import java.util.logging.Level;
import java.util.logging.Logger;

public class choice extends javax.swing.JFrame {
    
    private String ID;
    public choice() {
        initComponents();
    }
    
    public choice(String id) {
        initComponents();
        ID=id;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        checkbutton = new javax.swing.JButton();
        deposiebutton = new javax.swing.JButton();
        withdrawbutton = new javax.swing.JButton();
        transferSameBankbutton = new javax.swing.JButton();
        transferAnotherBankbutton = new javax.swing.JButton();
        historybutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        checkbutton.setText("check your Balance");
        checkbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbuttonActionPerformed(evt);
            }
        });

        deposiebutton.setText("deposite money");
        deposiebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deposiebuttonActionPerformed(evt);
            }
        });

        withdrawbutton.setText("withdraw money");
        withdrawbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawbuttonActionPerformed(evt);
            }
        });

        transferSameBankbutton.setText("transfer money within the same Bank");
        transferSameBankbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferSameBankbuttonActionPerformed(evt);
            }
        });

        transferAnotherBankbutton.setText("transfer money to another Bank");
        transferAnotherBankbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferAnotherBankbuttonActionPerformed(evt);
            }
        });

        historybutton.setText("view your transaction history");
        historybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historybuttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deposiebutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(withdrawbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transferSameBankbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transferAnotherBankbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(historybutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(checkbutton)
                .addGap(18, 18, 18)
                .addComponent(deposiebutton)
                .addGap(18, 18, 18)
                .addComponent(withdrawbutton)
                .addGap(18, 18, 18)
                .addComponent(transferSameBankbutton)
                .addGap(18, 18, 18)
                .addComponent(transferAnotherBankbutton)
                .addGap(18, 18, 18)
                .addComponent(historybutton)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbuttonActionPerformed
        try {
            Check c = new Check(ID);
            c.setVisible(true);
            this.setVisible(false);
        } catch (Exception ex) {
            Logger.getLogger(choice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_checkbuttonActionPerformed

    private void deposiebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deposiebuttonActionPerformed
        deposite d = new deposite(ID);
        d.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_deposiebuttonActionPerformed

    private void withdrawbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawbuttonActionPerformed
        withdraw w =new withdraw(ID);
        w.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_withdrawbuttonActionPerformed

    private void transferSameBankbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferSameBankbuttonActionPerformed
        transfer_s_bank t=new transfer_s_bank(ID);
        t.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_transferSameBankbuttonActionPerformed

    private void transferAnotherBankbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferAnotherBankbuttonActionPerformed
        transfer_otherBank t=new transfer_otherBank(ID);
        t.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_transferAnotherBankbuttonActionPerformed

    private void historybuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historybuttonActionPerformed
        history h =new history(ID);
        h.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_historybuttonActionPerformed
    
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
            java.util.logging.Logger.getLogger(choice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(choice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(choice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(choice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new choice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkbutton;
    private javax.swing.JButton deposiebutton;
    private javax.swing.JButton historybutton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton transferAnotherBankbutton;
    private javax.swing.JButton transferSameBankbutton;
    private javax.swing.JButton withdrawbutton;
    // End of variables declaration//GEN-END:variables
}
