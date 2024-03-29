package br.com.ufrn.webbot.Controller;

import java.io.File;
import java.util.Map;

import org.alicebot.ab.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrn.webbot.Request.MessageRequest;

@RestController
@RequestMapping(path = "/server")
public class ServerRestController {

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody MessageRequest msg) {
        String question = msg.message().toLowerCase();
        String response = responseFun(question);
        return ResponseEntity.ok(response);
    }

    private String responseFun(String inp){
        String out = "";
        try {
            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = false;
            Bot bot = new Bot("super", resourcesPath);
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();
            String textLine = "";
            textLine = inp;
            if ((textLine == null) || (textLine.length() < 1))
                textLine = MagicStrings.null_input;
            if (textLine.equals("q")) {
                System.exit(0);
            } else if (textLine.equals("wq")) {
                bot.writeQuit();
                System.exit(0);
            } else {
                String request = textLine;
                if (MagicBooleans.trace_mode)
                    System.out.println(
                            "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                    + ":TOPIC=" + chatSession.predicates.get("topic"));
                String response = chatSession.multisentenceRespond(request);
                while (response.contains("&lt;"))
                    response = response.replace("&lt;", "<");
                while (response.contains("&gt;"))
                    response = response.replace("&gt;", ">");
                out = response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    private String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
