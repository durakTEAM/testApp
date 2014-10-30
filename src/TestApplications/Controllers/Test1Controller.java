/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test1View;
import TestApplications.Workers.FileWorker;
import TestApplications.Workers.JSONWorker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test1Controller 
    extends TestController 
    implements ActionListener, MouseListener, ChangeListener {
    
    private final Test1View view = new Test1View();
    
    private Integer step = 0;
    private final ArrayList<String> questions = new ArrayList();
    private final ArrayList<JSONObject[]> answers = new ArrayList<>();
    private int sum;

    public Test1Controller(JSONObject test, JSONObject usr) {
        super(usr, test);
        
        this.setListeners();
        
        for (int i = 0; i < ((JSONArray)test.get("questions")).size(); i++) {
            this.questions.add((String) (((JSONObject) ((JSONArray)test.get("questions")).get(i)).get("question")));
            JSONObject[] temp = new JSONObject[4];
            temp[0] = (JSONObject) ((JSONObject)(((JSONObject) ((JSONArray)test.get("questions")).get(i)))).get("a");
            temp[0].put("res", 0);
            temp[1] = (JSONObject) ((JSONObject)(((JSONObject) ((JSONArray)test.get("questions")).get(i)))).get("b");
            temp[1].put("res", 0);
            temp[2] = (JSONObject) ((JSONObject)(((JSONObject) ((JSONArray)test.get("questions")).get(i)))).get("c");
            temp[2].put("res", 0);
            temp[3] = (JSONObject) ((JSONObject)(((JSONObject) ((JSONArray)test.get("questions")).get(i)))).get("d");
            temp[3].put("res", 0);
            this.answers.add(temp);
        }
        this.updateLabels();
        this.getSum();
    }
    
    private void setListeners() {
        this.view.buttonNext.addActionListener(this);
        this.view.buttonPrev.addActionListener(this);
        this.view.text1.addChangeListener(this);
        this.view.text2.addChangeListener(this);
        this.view.text3.addChangeListener(this);
        this.view.text4.addChangeListener(this);
    }

    @Override
    int getTestCnt() {
        return 1;
    }
    
    @Override
    protected void finishTest() {
        this.setRes();
        int[] results = new int[12];
        for (int i = 0; i < 33; i++) {
            results[((Long)this.answers.get(i)[0].get("fact")).intValue()-1]+=(Integer)this.answers.get(i)[0].get("res");
            results[((Long)this.answers.get(i)[1].get("fact")).intValue()-1]+=(Integer)this.answers.get(i)[1].get("res");
            results[((Long)this.answers.get(i)[2].get("fact")).intValue()-1]+=(Integer)this.answers.get(i)[2].get("res");
            results[((Long)this.answers.get(i)[3].get("fact")).intValue()-1]+=(Integer)this.answers.get(i)[3].get("res");
        }
        String strOfResults = "\n1. Материальное вознаграждение;" + results[0] + ";\n"
                + "2. Комфортные условия;" + results[1] + ";\n"
                + "3. Ясность целей и критериев;" + results[2] + ";\n"
                + "4. Социальные контакты;" + results[3] + ";\n"
                + "5. Стабильные, доверительные отношения;" + results[4] + ";\n"
                + "6. Признание заслуг и достижений;" + results[5] + ";\n"
                + "7. Амбициозные цели;" + results[6] + ";\n"
                + "8. Власть, влияние, конкурентность;" + results[7] + ";\n"
                + "9. Разнообразие, перемены;" + results[8] + ";\n"
                + "10. Новые идеи, креативность;" + results[9] + ";\n"
                + "11. Личностное развитие;" + results[10] + ";\n"
                + "12. Востребованная, значимая работа;" + results[11] + ";";
        JSONWorker.updateUsr(usr, "testsResults", strOfResults, n.intValue());
        JSONWorker.updateUsr(usr, "testsArray", 1, n.intValue());
        try {
            FileWorker.write("users/users.json", usr);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage());
        } finally {
            this.view.setVisible(false);
            this.view.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next >")) this.next();
        if (e.getActionCommand().equals("< Prev")) this.prev();
        if (e.getActionCommand().equals("Finish")) this.finishTest();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
         
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

    private void updateLabels() {
        this.updateBtns();
        this.view.labelQuestion.setText("<html><div align='center'>"+this.questions.get(this.step)+"</div></html>");
        this.view.labelAns1.setText("<html>"+String.valueOf(this.answers.get(step)[0].get("ans"))+"</html>");
        this.view.labelAns2.setText("<html>"+String.valueOf(this.answers.get(step)[1].get("ans"))+"</html>");
        this.view.labelAns3.setText("<html>"+String.valueOf(this.answers.get(step)[2].get("ans"))+"</html>");
        this.view.labelAns4.setText("<html>"+String.valueOf(this.answers.get(step)[3].get("ans"))+"</html>");
    }
    
    private void updateBtns() {
        this.view.buttonPrev.setEnabled(this.step != 0);
        this.view.buttonNext.setEnabled(this.sum == 11);
        
        if (step == 32) {
            this.view.buttonNext.setText("Finish");
        } else {
            this.view.buttonNext.setText("Next >");
        }
    }
    
    private void next() {
        if (this.sum == 11) {
            this.setRes();
            step++;
            this.updateSpinners();
            this.updateLabels();
        } else {
            JOptionPane.showMessageDialog(null, "<html>Распределите баллы корректно<p>"
                    + "Необходимо распределить все 11 баллов по 4 пунктам</html>");
        }
    }

    private void prev() {
        this.step--;
        this.updateSpinners();
        this.updateLabels();
    }

    private void setRes() {
        this.answers.get(step)[0].put("res", Integer.parseInt(this.view.text1.getValue().toString()));
        this.answers.get(step)[1].put("res", Integer.parseInt(this.view.text2.getValue().toString()));
        this.answers.get(step)[2].put("res", Integer.parseInt(this.view.text3.getValue().toString()));
        this.answers.get(step)[3].put("res", Integer.parseInt(this.view.text4.getValue().toString()));
    }

    private void getSum() {
        this.sum = (int) this.view.text1.getValue() + (int) this.view.text2.getValue()
                + (int) this.view.text3.getValue() + (int) this.view.text4.getValue();
        this.updateSpinnersModel();
    }
    
    private void updateSpinnersModel() {
        this.view.text1.setModel(new javax.swing.SpinnerNumberModel(Integer.parseInt(this.view.text1.getValue().toString()), 0, 11-sum+Integer.parseInt(this.view.text1.getValue().toString()), 1));
        this.view.text2.setModel(new javax.swing.SpinnerNumberModel(Integer.parseInt(this.view.text2.getValue().toString()), 0, 11-sum+Integer.parseInt(this.view.text2.getValue().toString()), 1));
        this.view.text3.setModel(new javax.swing.SpinnerNumberModel(Integer.parseInt(this.view.text3.getValue().toString()), 0, 11-sum+Integer.parseInt(this.view.text3.getValue().toString()), 1));
        this.view.text4.setModel(new javax.swing.SpinnerNumberModel(Integer.parseInt(this.view.text4.getValue().toString()), 0, 11-sum+Integer.parseInt(this.view.text4.getValue().toString()), 1));
        
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.getSum();
        this.updateBtns();
    }

    private void updateSpinners() {
        this.sum = (int)this.answers.get(step)[0].get("res") + (int)this.answers.get(step)[1].get("res") + (int)this.answers.get(step)[2].get("res") + (int)this.answers.get(step)[3].get("res");
        this.view.text1.setModel(new javax.swing.SpinnerNumberModel((int)this.answers.get(step)[0].get("res"), 0, 11-sum+(int)this.answers.get(step)[0].get("res"), 1));
        this.view.text2.setModel(new javax.swing.SpinnerNumberModel((int)this.answers.get(step)[1].get("res"), 0, 11-sum+(int)this.answers.get(step)[1].get("res"), 1));
        this.view.text3.setModel(new javax.swing.SpinnerNumberModel((int)this.answers.get(step)[2].get("res"), 0, 11-sum+(int)this.answers.get(step)[2].get("res"), 1));
        this.view.text4.setModel(new javax.swing.SpinnerNumberModel((int)this.answers.get(step)[3].get("res"), 0, 11-sum+(int)this.answers.get(step)[3].get("res"), 1));        
    }
}

