/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications.Workers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author artemsamsonov
 */
public class FileWorker {
    /**
     * Method to write JSONObject to file with JSONArray
     * @param file path to write
     * @param obj obj to write
     * @throws FileNotFoundException
     * @throws Exception 
     */
    static public void write(CharSequence file, JSONObject obj) throws FileNotFoundException, Exception {
        try (FileReader f = new FileReader((String) file)) {
            JSONArray x = JSONWorker.open(file);
            if (x.size() == (int)obj.get("ID")) {
                x.add(obj);
            } else {
                x.set((int)obj.get("ID"), obj);
            }
            FileWriter fileW = new FileWriter((String) file);
            fileW.append(x.toString());
            fileW.flush();
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException(":Файл " + file + " не существует");
        }
    }
    static public void createFile(CharSequence path, String content) throws IOException {
        FileWriter fw;
        try {
            fw = new FileWriter((String) path);
            fw.append(content);
            fw.flush();
        } catch (IOException ex) {
            throw new IOException();
        }
    }
}
