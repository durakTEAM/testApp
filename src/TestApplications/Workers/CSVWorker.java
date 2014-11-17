/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Workers;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class CSVWorker {
    
    static public void makeCSVTemplate(int id) throws Exception {
        JSONArray arr = JSONWorker.open("tests/test1.json");
        JSONObject usr = JSONWorker.open("users/users.json", id);
        char q = 0;
        try (CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("res" + usr.get("ID") + ".csv"), "UTF-8")), '\n', q)) {
            String[] entries = new String[arr.size()+3];
            
            entries[0] = "ФИО:;" + usr.get("lastName") + " " + usr.get("name")
                    + ' ' + usr.get("firstName");
            entries[1] = "Должность:;" + usr.get("position");
            entries[2] = "Результаты тестов";
            JSONArray testArr = (JSONArray) usr.get("testsArray");
            JSONArray res = (JSONArray) usr.get("testsResults");
            String str;
            for (int i = 0; i < arr.size(); i++) {
                if ((int)testArr.get(i) == 1) {
                    str = res.get(i).toString();
                } else {
                    str = "Не пройден";
                }
                entries[i+3] = ((JSONObject)arr.get(i)).get("name") +"\n" + str + '\n';
            }
            writer.writeNext(entries);
        } catch (FileNotFoundException ex){
            throw new FileNotFoundException("Не удалось создать csv файл");
        }
    }
}
