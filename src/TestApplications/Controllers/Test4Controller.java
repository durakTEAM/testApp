/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Controllers;

import TestApplications.Views.Test4View;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class Test4Controller extends TestController {
    private Test4View view;
    public LinkedList<String> questions = new LinkedList<>();
    private int[] ans = new int[25];
    private int step = 0;
    
    public Test4Controller(Test4View view, JSONObject usr, JSONObject test) throws FileNotFoundException {
        super(usr, test);
        this.view = view;
        try (Scanner in = new Scanner(new File((String) test.get("qPath")))) {
            while(in.hasNext())
                this.questions.add(in.nextLine());
        }
    }
    
    public void update() {
        if (step == 0) {
            this.view.btnPrev.setEnabled(false);
        } else {
            this.view.btnPrev.setEnabled(true);
        }
        if (step == this.questions.size()-1) {
            this.view.btnNext.setText("Finish");
        } else {
            this.view.btnNext.setText("Next >");
        }
        this.view.slider.setValue(ans[step]);
        this.view.labelQuestins.setText(step+1 + ") " + this.questions.get(step));
    }
    
    public void next() throws Exception {
        if (step+1 < ans.length) {
            ans[step++] = this.view.slider.getValue();
            update();
        } else {
            finishTest();
        }
        
    }
    
    public void prev() {
        if (step - 1 >= 0) {
            step--;
            update();
        } 
    }
    void updateUsr(JSONObject usr, CharSequence key, Object v) {
        JSONArray temp = (JSONArray) usr.get(key);
        temp.set(n.intValue(), v);
        usr.put(key, temp);
    }
    @Override
    int getTestCnt() {
        int res = 0;
        for (int i : this.ans) {
            res += i;
        }
        return res;
    }
}
