/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Views;

import TestApplications.Controllers.AuthController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aleksejtitorenko
 */

public class AuthView extends javax.swing.JFrame {

    private AuthController controller;

    public AuthView() {
        try {
            initComponents();
            this.controller = new AuthController(this);
            this.addWindowListener(this.controller);
            this.usersList.addListSelectionListener(this.controller);
            this.usersList.addMouseListener(this.controller);
            this.menuNewFile.addActionListener(this.controller);
            this.menuExit.addActionListener(this.controller);
            this.createUserButton.addActionListener(this.controller);
            this.enterUserButton.addActionListener(this.controller);
            this.usersList.addKeyListener(this.controller);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            this.setVisible(false);
            this.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createUserButton = new javax.swing.JButton();
        enterUserButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        usersList = new javax.swing.JList();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNewFile = new javax.swing.JMenuItem();
        menuExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Тесты");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        createUserButton.setText("Новый пользователь");
        createUserButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createUserButtonMouseClicked(evt);
            }
        });

        enterUserButton.setText("Войти");
        enterUserButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                enterUserButtonMousePressed(evt);
            }
        });
        enterUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterUserButtonActionPerformed(evt);
            }
        });

        usersList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.UIManager.getDefaults().getColor("List.foreground"), null));
        usersList.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        usersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        usersList.setToolTipText("Выберите пользователя");
        usersList.setSelectionBackground(java.awt.SystemColor.textHighlight);
        jScrollPane1.setViewportView(usersList);

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));

        jMenu1.setText("Файл");

        menuNewFile.setText("Новый пользователь");
        menuNewFile.setToolTipText("Создать нового пользователя");
        menuNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewFileActionPerformed(evt);
            }
        });
        jMenu1.add(menuNewFile);

        menuExit.setText("Выход");
        menuExit.setToolTipText("Выйти из программы");
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(enterUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enterUserButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createUserButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enterUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterUserButtonActionPerformed
   
    }//GEN-LAST:event_enterUserButtonActionPerformed

    private void createUserButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createUserButtonMouseClicked
       
    }//GEN-LAST:event_createUserButtonMouseClicked

    private void enterUserButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_enterUserButtonMousePressed
        
        
    }//GEN-LAST:event_enterUserButtonMousePressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void menuNewFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuNewFileActionPerformed

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
            java.util.logging.Logger.getLogger(AuthView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AuthView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AuthView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AuthView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    new AuthView().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(AuthView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createUserButton;
    public javax.swing.JButton enterUserButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuNewFile;
    public javax.swing.JList usersList;
    // End of variables declaration//GEN-END:variables
}
