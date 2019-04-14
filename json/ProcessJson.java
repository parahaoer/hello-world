package json;

import net.sf.json.*;

import java.io.*;
import java.util.*;

public class ProcessJson {

    public static String reader(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = bufferedReader.readLine();
                while (lineTxt != null) {
                    return lineTxt;
                }
            }
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.out.println("Cannot find the file specified!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading file content!");
            e.printStackTrace();
        }
        return null;
    }

    private static void process(String txtStr) {
        System.out.println(txtStr);
        JSONObject json = JSONObject.fromObject(txtStr);
        JSONArray datas = json.getJSONArray("objects");
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            JSONObject obj = datas.getJSONObject(i).getJSONObject("cells");
            String name = obj.getString("weibo_name");
            String code = obj.getString("weibo_id");
            String url =  obj.getString("url");

            map.put("name", name);
            map.put("code", code);
            map.put("url", url);
            list.add(map);
        }
//        if (!list.isEmpty()) {
//            insert(list);
//        }
    }

    public static void main(String[] args) {
        String filePath = "C:/Users/Administrator/Desktop/工作/json/campaign-and-threat-actors.json";
        String txtString = ProcessJson.reader(filePath);
        ProcessJson.process(txtString);
    }
}
