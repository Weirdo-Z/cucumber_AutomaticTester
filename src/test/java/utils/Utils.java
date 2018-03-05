package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Zoey on 2018/03/05
 */
public class Utils {
    public static JSONObject toJSONObject(File file){
        JSONObject json = new JSONObject();
        try {
            FileInputStream first = new FileInputStream(file);
            try {
                byte[] bytes = new byte[first.available()];
                first.read(bytes);
                first.close();
                json = JSON.parseObject(new String(bytes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return json;
    }
}
