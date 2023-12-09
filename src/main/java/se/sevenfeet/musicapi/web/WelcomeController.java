package se.sevenfeet.musicapi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String getIndexPage() {
        return "index"; // refers to "index.html" in the "templates" directory.
    }
}
