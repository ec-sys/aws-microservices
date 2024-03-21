package demo.aws.frontend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("chat")
public class ChatController {
    @GetMapping("/home")
    public String showLoginForm() {
        return getTemplate("home");
    }

    private String getTemplate(String name) {
        return "chat/" + name;
    }
}
