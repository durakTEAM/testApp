/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.EnterView;
import TestApplications.Views.Test3View;
import TestApplications.Views.Test4View;
import TestApplications.Views.Test5View;
import TestApplications.Views.Test6View;
import TestApplications.Views.Test7View;
import TestApplications.Workers.CSVWorker;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import TestApplications.Workers.SendAttachmentInEmailWorker;
import TestApplications.frameTest1;
import TestApplications.frameTest2;
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
        this.complitedTests = (JSONArray) usr.get("testsArray");
        this.sendedResults = (JSONArray) usr.get("sendedResults");
        
        this.filename = "user"+usr.get("ID")+"res.csv";
        
        this.testsArray = JSONWorker.open("tests/test1.json");
        DefaultListModel listmodel = new DefaultListModel();
        for (int i = 0; i < this.testsArray.size(); i++) {
            listmodel.addElement(JSONWorker.get(testsArray, i).get("name"));
        }
        this.view.testsComboBox.setModel(listmodel);
        this.view.testsComboBox.addListSelectionListener(this);
        
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
                temp[0]++;
            }
        }
        info[1] = String.valueOf("Пройдено тестов: " + temp[0]);
        info[2] = String.valueOf("Отправлено результатов: " + temp[1]);
        return info;
    }
    
    private void updateTestBtn() throws Exception {
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
        if (sendedResults.equals(complitedTests)) {
            this.view.menuTestSend.setEnabled(false);
            this.view.menuTestSend.setText("Вы не прошли новых тестов");
        } else {
            this.view.menuTestSend.setEnabled(true);
            this.view.menuTestSend.setText("Отправить результаты");
        }

    }

    public void sendResults() {
        try {
            CSVWorker.makeCSVTemplate((int) usr.get("ID"));
            SendAttachmentInEmailWorker.send(this.to, this.from, this.password, this.filename);
            usr.put("sendedResults", (JSONArray) usr.get("testsArray"));
            FileWorker.write("users/users.json", usr);
            this.updateSendButton();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startTest() {
        try {
            int i = this.view.testsComboBox.getSelectedIndex();
            int j = (int) ((JSONObject)this.testsArray.get(i)).get("type");
            if (j==1){
                try {
                    new frameTest1((JSONObject) testsArray.get(i), usr).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j==2){
                try {
                    new frameTest2((JSONObject) testsArray.get(i), usr).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j == 3) {
                try {
                    new Test3View((JSONObject) testsArray.get(i), usr).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j == 4) {
                try {
                    new Test4View((JSONObject) testsArray.get(i), usr).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j == 5) {
                new Test5View((JSONObject) testsArray.get(i), usr).setVisible(true);
            }
            if (j == 6){
                new Test6View((JSONObject) testsArray.get(i), usr).setVisible(true);
            }
            if (j == 7) {
                new Test7View((JSONObject) testsArray.get(i), usr).setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Пройти тест")) {
            this.startTest();
        }
        if (e.getActionCommand().equals("Закрыть программу")) {
            this.view.setVisible(false);
            this.view.dispose();
        }
        if (e.getActionCommand().equals("Отправить результаты")) {
            this.sendResults();
        }
        if (e.getActionCommand().equals("Сменить пользователя")) {
            //TODO
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
        try {
            this.updateTestBtn();
        } catch (Exception ex) {
            Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } catch (Exception ex) {
            Logger.getLogger(EnterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            this.startTest();
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
