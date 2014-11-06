/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test5View;
import TestApplications.Workers.JSONWorker;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test2Controller
    extends TestController
    implements ActionListener {
    
    private final Test5View view = new Test5View();
    
    private LinkedList<String> questions = new LinkedList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<Integer[]> key = new ArrayList<>();
    private int result = 0;
    private int step = 0;

    public Test2Controller(JSONObject test, JSONObject usr) {
        super(usr, test);
        this.view.setTitle((String) test.get("name"));
        this.view.btnA.setText("Нет");
        this.view.btnB.setText("Да");
        this.setListeners();
        this.view.labelQuestion.setVerticalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < ((JSONArray)test.get("questions")).size(); i++) {
            this.questions.add((String) ((JSONObject)((JSONArray)test.get("questions")).get(i)).get("question"));
            Integer[] temp = new Integer[2];
            temp[0] = (Integer) JSONWorker.get((JSONArray)test.get("questions"), i).get("ans1");
            temp[1] = (Integer) JSONWorker.get((JSONArray)test.get("questions"), i).get("ans2");
            this.key.add(temp);
        }
        this.updateLabels();
    }
    
    private void setListeners() {
        this.view.btnA.addActionListener(this);
        this.view.btnB.addActionListener(this);
    }
    
    @Override
    int getTestCnt() {
        return this.result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Да")) {
            result+=key.get(step++)[0];
        }
        if (e.getActionCommand().equals("Нет")) {
            result+=key.get(step++)[1];
        }
        this.updateLabels();
    }

    private void updateLabels() {
        if (step == 40){
            try {
                this.finishTest();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Невозможно записать результат");
                this.view.setVisible(false);
                this.view.dispose();
            }
            return;
        }
        StringBuilder str = new StringBuilder(questions.get(step));
        str.insert(0, "<html><div align='leading'>&#32;&#32;");
        str.insert(str.length(), "</div></html>");
        this.view.labelQuestion.setText(str.toString());
    }
    
    @Override
    protected void finishTest() throws Exception {
        super.finishTest();
        this.view.setVisible(false);
        this.view.dispose();
    }
}
