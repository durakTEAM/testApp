/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test10View;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import static javax.swing.SwingConstants.TOP;
import org.json.simple.JSONObject;

/**
 *
 * @author aleksejtitorenko
 */
public class Test10Controller extends TestController implements ActionListener {
private Test10View  view;
private ArrayList<String[]> questions = new ArrayList<>();
private JLabel labelSituation = new JLabel();
private JLabel labelFeels = new JLabel();
private JButton buttonNext;
private int[] key = new int[108];
private List <Integer> answers = new ArrayList<>();
private int step;
private ArrayList <JSlider> sliders = new ArrayList<>();
    Test10Controller(JSONObject test, JSONObject usr) throws FileNotFoundException {
        super(usr, test);
        this.buttonNext = new JButton();
        view = new Test10View();
        view.setVisible(true);
        sliders.add(view.jSlider1);
        sliders.add(view.jSlider2);        
        sliders.add(view.jSlider4);
        sliders.add(view.jSlider5);
        sliders.add(view.jSlider6);
        sliders.add(view.jSlider7);
        view.add(labelSituation);
        view.add(buttonNext);
        buttonNext.setText("Далее");
        buttonNext.setSize(90, 30);
        buttonNext.setLocation(150, 250);
        labelSituation.setSize(new Dimension(370, 250));
        labelSituation.setLocation(10, 10);
        labelSituation.setVerticalAlignment(TOP);
        view.add(labelFeels);
        labelFeels.setSize(new Dimension(250, 150));
        labelFeels.setLocation(20, 100);
        labelFeels.setVerticalAlignment(TOP);
        view.pack();
        StringBuilder allText = new StringBuilder();
        try(Scanner in = new Scanner(new File("tests/test10"), "UTF-8")){
            while(in.hasNextLine()){
                String c = in.nextLine();
                if(c.equals("#")) {
                    allText.append(c);
                } else {
                    allText.append(c).append("\n");
                }
            }
        }catch (FileNotFoundException ex)  {
            Logger.getLogger(Test9Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] temp = allText.toString().split("#");
    for (String temp1 : temp) {
        questions.add(temp1.split("\n"));
    }
        this.setListeners();
        this.updateLabels();        
    }
    private void setListeners(){
        buttonNext.addActionListener(this);
    }
    private void updateLabels(){
        StringBuilder temp = new StringBuilder();
        labelSituation.setText("<html>"+questions.get(step)[0]+"</html>");
        labelSituation.setFont(new Font("Arial", Font.BOLD , 16));
        
        temp.append("<html><ul>");
        for (int i = 1; i < questions.get(step).length; i++){
            temp.append("<li>").append(questions.get(step)[i]).append("</li>");
        }
        temp.append("</ul></html>");
        labelFeels.setText(temp.toString());
        labelFeels.setFont(new Font("Arial", 0, 18));
    }
    @Override
    int getTestCnt() {
        try(Scanner inKey = new Scanner(new File("tests/key10"), "UTF-8")){
            while(inKey.hasNextLine()){
                for (int i = 0; i < 108; i++){
                this.key[i] = inKey.nextInt();
                }
            }
        } catch (FileNotFoundException ex) {
        Logger.getLogger(Test10Controller.class.getName()).log(Level.SEVERE, null, ex);
    }
        int res = 0;
        for (int i =0; i <108; i++){
            if(answers.get(i).equals(key[i])){
                res++;
            }
        }
        
        return res;
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JSlider slider : sliders){
            answers.add(slider.getValue());
            slider.setValue(0);
        }
        if (step == 16){
            this.buttonNext.setText("Завершить тест");
        }
        if (step == 17){
            
            try {
                this.finishTest();
            } catch (Exception ex) {
                Logger.getLogger(Test10Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            view.setVisible(false);
            view.dispose();
        }else{
        
        step++;   
        this.updateLabels();
        }
    }
}
