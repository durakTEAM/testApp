/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestApplications;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author artemsamsonov
 */
public class FileWorker {
    static void write(CharSequence file, JSONObject obj) throws FileNotFoundException, Exception {
        JSONArray x;
        try (FileReader f = new FileReader((String) file)) {
            JSONParser parser = new JSONParser();
            x = (JSONArray) parser.parse(f);
            int l = ((Long)obj.get("ID")).intValue();
            x.set(l, obj);
            FileWriter fileW = new FileWriter((String) file);
            fileW.append(x.toString());
            fileW.flush();
        }
    }
}
