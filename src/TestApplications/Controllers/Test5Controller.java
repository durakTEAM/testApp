/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test5View;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test5Controller 
    extends TestController
    implements ActionListener {
    
    private final Test5View view;
    
    private LinkedList<String> questions = new LinkedList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<String> key = new ArrayList<>();
    private StringBuilder result = new StringBuilder();
    
    private int step = 0;
    
    public Test5Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(usr, test);
        this.view = new Test5View();
        this.setListeners();
        this.view.setTitle((String) test.get("name"));
        try (Scanner in = new Scanner(new File((String) test.get("qPath")))) {
            while(in.hasNext())
                this.questions.add(in.nextLine());
        } 
        try (Scanner in = new Scanner(new File((String) test.get("key")))) {
            while(in.hasNext())
                this.key.add(in.nextLine());
        } 
        this.view.labelQuestion.setText(this.str());
    }
    
    public void a() throws Exception {
        this.answers.add(step+"а");
        if(this.step == this.questions.size()) {
            this.finishTest();
            return;
        }
        this.view.labelQuestion.setText(this.str());
    }
    
    public void b() throws Exception {
        this.answers.add(step+"б");
        if(this.step == this.questions.size()) {
            this.finishTest();
            return;
        }
        this.view.labelQuestion.setText(this.str());
    }
    
    private String str() {
        StringBuilder str = new StringBuilder(this.questions.get(step++));
        str.insert(0, "<html><h2>").append("</html>").insert(str.indexOf("  а"),
                "</h2><p align='leading'>&#32;&#32;").insert(str.indexOf("  б"), "<p align='leading'>&#32;&#32;");
        return str.toString();
    }
    
    int getTestCnt() {
        int cnt = 0;
        this.result.append(this.key.get(0)).append(";");
        for (int i = 0; i < this.answers.size(); i++) {
            if (this.key.get(i+1).equals(this.answers.get(i))) {
                cnt++;
            }
        }
        this.result.append(cnt).append(";\n");
        int i = 101;
        TreeSet<String> ans = new TreeSet<>();
        ans.addAll(this.answers);
        while (i < this.key.size()) {
            cnt = 0;
            String[] k = this.key.get(i).split("#");
            this.result.append(k[0]).append(";");
            for (String j : k) {
                if (ans.contains(j)) {
                    cnt++;
                }
            }
            this.result.append(cnt).append("\n");
            i++;
        }
        return 0;
    }
    @Override
    public void finishTest() throws Exception {
        this.getTestCnt();
        JSONWorker.updateUsr(usr, "testsArray", 1, n.byteValue());
        JSONWorker.updateUsr(usr, "testsResults", this.result.toString(), n.byteValue());
        try {
            FileWorker.write("users/users.json", usr);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(view, "<html>Не удалось обновить users.json<p>"
                    + ex.getMessage() + "<p>Попробуйте отправить результаты</html>");
        }
        this.view.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("A")) try {
            this.a();
        } catch (Exception ex) {
            Logger.getLogger(Test5Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(e.getActionCommand().equals("B")) try {
            this.b();
        } catch (Exception ex) {
            Logger.getLogger(Test5Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setListeners() {
        this.view.btnA.addActionListener(this);
        this.view.btnB.addActionListener(this);
    }
}
