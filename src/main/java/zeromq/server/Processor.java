package zeromq.server;

import org.json.JSONObject;
import org.zeromq.ZMsg;


public class Processor {
    public JSONObject processMessage(String identifier, ZMsg message_content){
        System.out.println("In PROCESSOR.JAVA. Identifier = " + identifier);
        if (identifier.equals("NavisensLocUpdate")){
            String[] all_x = message_content.popString().split(",",2);
            String[] all_y = message_content.popString().split(",",2);
            System.out.println("x = " + all_x[1]);
            System.out.println("y = " + all_y[1]);
            JSONObject jsonObject = new JSONObject("{ \"lng\":\"" + all_x[1] + "\" , \"lat\" : \"" + all_y[1] + "\"}");
            return jsonObject;
        }
        return null;
    }
}