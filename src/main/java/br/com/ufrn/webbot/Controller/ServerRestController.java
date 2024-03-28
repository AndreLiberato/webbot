package br.com.ufrn.webbot.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrn.webbot.Request.MessageRequest;

@RestController
@RequestMapping(path = "/server")
public class ServerRestController {

    private final Map<String, String> chatResponses = Map.of(
            "olá", "Olá! Como posso ajudar?",
            "como você está?", "Estou bem, obrigado por perguntar.",
            "adeus", "Até logo!",
            "qual é o seu nome?", "Meu nome é ChatBot.",
            "quanto é 2 + 2?", "A resposta é 4.",
            "qual é a capital do Brasil?", "A capital do Brasil é Brasília.");

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody MessageRequest msg) {
        String question = msg.message().toLowerCase();
        String response = chatResponses.getOrDefault(question, "Desculpe, não entendi sua pergunta.");
        return ResponseEntity.ok(response);
    }
}
