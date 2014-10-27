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
import TestApplications.Views.Test7View;
import TestApplications.Workers.CSVWorker;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import TestApplications.Workers.SendAttachmentInEmailWorker;
import TestApplications.frameTest1;
import TestApplications.frameTest2;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class EnterController {
    private final EnterView view;
    
    private JSONObject usr;
    private JSONArray questionsArray;
    
    private final String to = "a.a.titorenko@gmail.com";
    private final String from = "samsonov68rus@gmail.com";
    private final String password = "cfvcjyjdfhn`v";
    private String filename;
    
    public EnterController(EnterView view, JSONObject usr) throws Exception {
        this.view = view;
        this.usr = usr;
        this.questionsArray = JSONWorker.open("tests/test1.json");
        this.filename = "user"+usr.get("ID")+"res.csv";
        this.view.FIOLabel.setText(usr.get("lastName").toString() + " "
                +usr.get("name").toString() +" "
                + usr.get("firstName").toString());
        for (int i = 0; i < this.questionsArray.size(); i++) {
            this.view.testsComboBox.addItem(JSONWorker.get(questionsArray, i).get("name"));
        }
    }
    
    public void updateTestBtn() throws Exception {
        int j = (int) ((JSONArray)this.usr.get("testsArray")).get(this.view.testsComboBox.getSelectedIndex());
        if (j == 1) {
            this.view.startTestButton.setEnabled(false);
            this.view.startTestButton.setText("Вы уже прошли этот тест");
            
        } else {
            this.view.startTestButton.setEnabled(true);
            this.view.startTestButton.setText("Пройти тест");
        }
    }
    public void updateSendButton() {
        JSONArray sendedResults = (JSONArray) usr.get("sendedResults");
        JSONArray complitedTests = (JSONArray) usr.get("testsArray");
        if (sendedResults.equals(complitedTests)) {
            this.view.buttonResults.setEnabled(false);
            this.view.buttonResults.setText("<html>Вы не прошли новых <p>тестов</html>");
        } else {
            this.view.buttonResults.setEnabled(true);
            this.view.buttonResults.setText("<html>Отправить результаты</html>");
        }
    }

    public void sendResults() {
        try {
            CSVWorker.makeCSVTemplate((int) usr.get("ID"));
            SendAttachmentInEmailWorker.send(this.to, this.from, this.password, this.filename);
            this.updateSendButton();
            usr.put("sendedResults", (JSONArray) usr.get("testsArray"));
            FileWorker.write("users/users.json", usr);
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
            int j = (int) ((JSONObject)this.questionsArray.get(i)).get("type");
            if (j==1){
                try {
                    new frameTest1((JSONObject) questionsArray.get(i), usr).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j==2){
                try {
                    new frameTest2((JSONObject) questionsArray.get(i), usr).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j == 3) {
                try {
                    new Test3View((JSONObject) questionsArray.get(i), usr).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j == 4) {
                try {
                    new Test4View((JSONObject) questionsArray.get(i), usr).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (j == 5) {
                new Test5View((JSONObject) questionsArray.get(i), usr).setVisible(true);
            }
            if (j == 7) {
                new Test7View((JSONObject) questionsArray.get(i), usr).setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(EnterView.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
