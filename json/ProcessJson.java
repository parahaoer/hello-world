package json;

import net.sf.json.*;

import java.io.*;
import java.util.*;

public class ProcessJson {

    public static String reader(String filePath) {
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static void wirte(String jsonStr) {

        File file = new File("C:/Users/Administrator/Desktop/工作/json/test3.json");
        char[] stack = new char[1024]; // 存放括号，如 "{","}","[","]"
        int top = -1;
        StringBuffer sb = new StringBuffer();
        char[] charArray = jsonStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if ('{' == c || '[' == c) {
                stack[++top] = c; // 将括号添加到数组中，这个可以简单理解为栈的入栈
                sb.append(charArray[i] + "\n");
                for (int j = 0; j <= top; j++) {
                    sb.append("\t");
                }
                continue;
            }
            if ((i + 1) <= (charArray.length - 1)) {
                char d = charArray[i+1];
                if ('}' == d || ']' == d) {
                    top--; // 将数组的最后一个有效内容位置下标减 1，可以简单的理解为将栈顶数据弹出
                    sb.append(charArray[i] + "\n");
                    for (int j = 0; j <= top; j++) {
                        sb.append("\t");
                    }
                    continue;
                }
            }
            if (',' == c) {
                sb.append(charArray[i] + "\n");
                for (int j = 0; j <= top; j++) {
                    sb.append("\t");
                }
                continue;
            }
            sb.append(c);
        }

        Writer write = null;
        try {
            write = new FileWriter(file);
            write.write(sb.toString());
            write.flush();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void process(String srcStr, String destStr) {
        JSONObject srcjson = JSONObject.fromObject(srcStr);
        JSONArray srcdatas = srcjson.getJSONArray("objects");

        JSONObject destjson = JSONObject.fromObject(destStr);
        JSONArray destdatas = destjson.getJSONArray("objects");
//        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < srcdatas.size(); i++) {
            System.out.println(srcdatas.get(i));
            destdatas.add(srcdatas.get(i));

//            Map<String, Object> map = new HashMap<>();
//            System.out.println(datas.get(i));
//            JSONObject obj = datas.getJSONObject(i);
//            String type = obj.getString("type");
//            String id = obj.getString("id");
//            String created =  obj.getString("created");
//
//            map.put("name", type);
//            map.put("code", id);
//            map.put("url", created);
//            list.add(map);
        }

        ProcessJson.wirte(destjson.toString());

//        if (!list.isEmpty()) {
//            insert(list);
//        }
    }

    public static void main(String[] args) {
        String destPath = "C:/Users/Administrator/Desktop/工作/json/campaign-and-threat-actors.json";
        String srcPath = "C:/Users/Administrator/Desktop/工作/json/malicious-email-indicator-with-attachment.json";
        String srcStr = ProcessJson.reader(srcPath);
        String destStr = ProcessJson.reader(destPath);
        ProcessJson.process(srcStr,destStr);
    }
}
