/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.AuthView;
import TestApplications.Views.EnterView;
import TestApplications.Views.Test3View;
import TestApplications.Views.Test4View;
import TestApplications.Views.Test5View;
import TestApplications.Views.Test6View;
import TestApplications.Views.Test7View;
import TestApplications.Views.Test8View;
import TestApplications.Workers.CSVWorker;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import TestApplications.Workers.SendAttachmentInEmailWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class EnterController implements ActionListener, ListDataListener, 
        ListSelectionListener,WindowListener, MouseListener {
    
    private final EnterView view;
    
    private JSONObject usr;
    private JSONArray testsArray;
    private JSONArray sendedResults;
    private JSONArray complitedTests;
    
    private final String to = "a.a.titorenko@gmail.com";
    private final String from = "samsonov68rus@gmail.com";
    private final String password = "cfvcjyjdfhn`v";
    private String filename;
    
    public EnterController(EnterView view, JSONObject usr) throws Exception{
        this.view = view;
        this.usr = usr;
        
        this.filename = "user"+usr.get("ID")+"res.csv";
        
        try {
            this.testsArray = JSONWorker.open("tests/test1.json");
            if(this.testsArray.size()!=((JSONArray)(usr.get("testsArray"))).size()) {
                throw new Exception("Bad tests.json file");
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
            this.view.setVisible(false);
            this.view.dispose();
        }
        DefaultListModel listmodel = new DefaultListModel();
        for (int i = 0; i < this.testsArray.size(); i++) {
            listmodel.addElement(JSONWorker.get(testsArray, i).get("name"));
        }
        this.view.testsComboBox.setModel(listmodel);
        this.view.testsComboBox.addListSelectionListener(this);
    }
    
    private void setUserInfo() {
        String[] info = this.getUserInfo();
        this.view.setTitle(info[0]);
        this.view.labelCntOfFinishedTests.setText(info[1]);
        this.view.labelOfSendedTests.setText(info[2]);
    }
    
    private String[] getUserInfo() {
        String[] info = new String[3];
        info[0] = usr.get("lastName").toString() + " "
                +usr.get("name").toString() +" "
                + usr.get("firstName").toString();
        int[] temp = new int[2];
        for (int i = 0; i < this.testsArray.size(); i++) {
            if ((int)this.complitedTests.get(i) == 1) {
                temp[0]++;
            }
            if ((int)this.sendedResults.get(i) == 1) {
                temp[1]++;
            }
        }
        info[1] = String.valueOf("Пройдено тестов: " + temp[0]);
        info[2] = String.valueOf("Отправлено результатов: " + temp[1]);
        return info;
    }
    
    private void updateTestBtn() {
        if (this.view.testsComboBox.getSelectedIndex() == -1) {
            this.view.menuTestStart.setEnabled(false);
            System.out.println(this.view.getName() + ": В списке не выбран ни один элемент");
            return;
        }
        int j = (int) ((JSONArray)this.usr.get("testsArray")).get(this.view.testsComboBox.getSelectedIndex());
        if (j == 1) {
            this.view.menuTestStart.setEnabled(false);
            this.view.menuTestStart.setText("Вы уже прошли этот тест");
            
        } else {
            this.view.menuTestStart.setEnabled(true);
            this.view.menuTestStart.setText("Пройти тест");
        }
    }
    
    public void updateSendButton() {
        this.complitedTests = (JSONArray) usr.get("testsArray");
        this.sendedResults = (JSONArray) usr.get("sendedResults");
        if (sendedResults.equals(complitedTests)) {
            this.view.menuTestSend.setEnabled(false);
            this.view.menuTestSend.setText("Вы не прошли новых тестов");
        } else {
            this.view.menuTestSend.setEnabled(true);
            this.view.menuTestSend.setText("Отправить результаты");
        }

    }

    public void sendResults() throws Exception {
        try {
            CSVWorker.makeCSVTemplate((int) usr.get("ID"));
            try {
                SendAttachmentInEmailWorker.send(this.to, this.from, this.password, this.filename);
                usr.put("sendedResults", (JSONArray) usr.get("testsArray"));
                FileWorker.write("users/users.json", usr);
                this.updateSendButton();
                this.setUserInfo();
                JOptionPane.showMessageDialog(view, "Результаты отправлены!");
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage());
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        } 
    }

    public void startTest() throws FileNotFoundException, IOException, Exception {
        int i = this.view.testsComboBox.getSelectedIndex();
        int j = (int) ((JSONObject)this.testsArray.get(i)).get("type");
        TestController test;
        if (j==1){
            test = new Test1Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j==2){
            test = new Test2Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j == 3) {
            test = new Test3Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j == 4) {
            test = new Test4Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j == 5) {
            test = new Test5Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j == 6){
            test = new Test6Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j == 7) {
            test = new Test7Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j == 8){
            new Test8Controller((JSONObject) testsArray.get(i), usr);
        }
        if (j == 9) {
            new Test9Controller((JSONObject) testsArray.get(i), usr);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Пройти тест")) {
            try {
                this.startTest();
            } catch (Exception ex) {
                Logger.getLogger(EnterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getActionCommand().equals("Закрыть программу")) {
            this.view.setVisible(false);
            this.view.dispose();
        }
        if (e.getActionCommand().equals("Отправить результаты")) {
            try {
                this.sendResults();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Не удалось отправить результаты");
            }
        }
        if (e.getActionCommand().equals("Сменить пользователя")) {
            this.view.setVisible(false);
            this.view.dispose();
            try {
                new AuthView().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(EnterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void intervalAdded(ListDataEvent e) {

    }

    @Override
    public void intervalRemoved(ListDataEvent e) {

    }

    @Override
    public void contentsChanged(ListDataEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.updateTestBtn();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
        try {
            this.updateSendButton();
            this.updateTestBtn();
            this.setUserInfo();
        } catch (Exception ex) {
            Logger.getLogger(EnterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && this.view.menuTestStart.isEnabled()) {
            try {
                this.startTest();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Не удалось запустить тест");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
