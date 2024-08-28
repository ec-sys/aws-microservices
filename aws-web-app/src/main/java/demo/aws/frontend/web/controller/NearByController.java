package demo.aws.frontend.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("near-by")
public class NearByController {
    @GetMapping("/home")
    public String showLoginForm() {
        return getTemplate("home");
    }

    private String getTemplate(String name) {
        return "near_by/" + name;
    }
}
