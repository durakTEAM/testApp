/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Views;

import TestApplications.Controllers.EnterController;
import java.io.FileNotFoundException;
import javax.swing.ListSelectionModel;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class EnterView extends javax.swing.JFrame {
    EnterController controller;
    /**
     * Creates new form enterFrame
     * @throws java.io.FileNotFoundException
     */
    public EnterView(JSONObject usr) throws FileNotFoundException, Exception {
        initComponents();
        this.testsComboBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.controller = new EnterController(this, usr);
        this.menuTestStart.addActionListener(this.controller);
        this.menuTestSend.addActionListener(this.controller);
        this.menuUserChange.addActionListener(this.controller);
        this.menuTestClose.addActionListener(this.controller);
        this.testsComboBox.addMouseListener(this.controller);
        this.addWindowListener(this.controller);
    }

    private EnterView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        testsComboBox = new javax.swing.JList();
        labelCntOfFinishedTests = new javax.swing.JLabel();
        labelOfSendedTests = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuTest = new javax.swing.JMenu();
        menuTestStart = new javax.swing.JMenuItem();
        menuTestSend = new javax.swing.JMenuItem();
        menuTestClose = new javax.swing.JMenuItem();
        menuUser = new javax.swing.JMenu();
        menuUserChange = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(49, 200));

        testsComboBox.setBackground(new java.awt.Color(208, 211, 220));
        testsComboBox.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        testsComboBox.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>", "<html><li>Item 1</li></html>" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        testsComboBox.setBounds(new java.awt.Rectangle(0, 0, 39, 200));
        jScrollPane1.setViewportView(testsComboBox);

        labelCntOfFinishedTests.setText("Пройдено тестов:");

        labelOfSendedTests.setText("Отправлено результатов: ");

        menuTest.setText("Тест");

        menuTestStart.setText("Пройти тест");
        menuTestStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTestStartActionPerformed(evt);
            }
        });
        menuTest.add(menuTestStart);

        menuTestSend.setText("Отправить результаты");
        menuTest.add(menuTestSend);

        menuTestClose.setText("Закрыть программу");
        menuTest.add(menuTestClose);

        jMenuBar1.add(menuTest);

        menuUser.setText("Пользователь");

        menuUserChange.setText("Сменить пользователя");
        menuUser.add(menuUserChange);

        jMenuBar1.add(menuUser);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelOfSendedTests)
                            .addComponent(labelCntOfFinishedTests))
                        .addGap(0, 392, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCntOfFinishedTests)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOfSendedTests)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void menuTestStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTestStartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuTestStartActionPerformed

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
            java.util.logging.Logger.getLogger(EnterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EnterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EnterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EnterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EnterView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel labelCntOfFinishedTests;
    public javax.swing.JLabel labelOfSendedTests;
    private javax.swing.JMenu menuTest;
    private javax.swing.JMenuItem menuTestClose;
    public javax.swing.JMenuItem menuTestSend;
    public javax.swing.JMenuItem menuTestStart;
    private javax.swing.JMenu menuUser;
    private javax.swing.JMenuItem menuUserChange;
    public javax.swing.JList testsComboBox;
    // End of variables declaration//GEN-END:variables
}
