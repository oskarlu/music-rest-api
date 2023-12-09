package se.sevenfeet.musicapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {

    public static final Logger LOG = LoggerFactory.getLogger(CustomErrorController.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        LOG.error("Exception thrown: {}", ex.getClass().getSimpleName(), ex);

        model.addAttribute("status", 500);
        model.addAttribute("error", ex.getClass().getSimpleName());
        model.addAttribute("message", ex.getMessage() != null ? ex.getMessage() : "null msg");
        return "error/error";
    }
}
