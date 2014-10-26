/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class CSVWorker {
    
    static void makeCSVTemplate(int id) throws Exception {
        JSONArray arr = JSONWorker.open("tests/test1.json");
        JSONObject usr = JSONWorker.open("users/users.json", id);
        char q = 0;
        try (CSVWriter writer = new CSVWriter(new FileWriter("template.csv"), '\n', q)) {
            String[] entries = new String[arr.size()+3];
            
            entries[0] = "ФИО:;" + usr.get("lastName") + " " + usr.get("name")
                    + ' ' + usr.get("firstName");
            entries[1] = "Должность:;" + usr.get("position");
            entries[2] = "Результаты тестов";
            JSONArray testArr = (JSONArray) usr.get("testsArray");
            JSONArray res = (JSONArray) usr.get("testsResults");
            String str;
            for (int i = 0; i < arr.size(); i++) {
                if ((Long)testArr.get(i) == 1) {
                    str = res.get(i).toString();
                } else {
                    str = "Не пройден";
                }
                entries[i+3] = ((JSONObject)arr.get(i)).get("name") + ";" + str;
            }
            writer.writeNext(entries);
        }
    }
}
