package se.sevenfeet.musicapi.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {

    public static final Logger LOG = LoggerFactory.getLogger(CustomErrorController.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        LOG.error("*** CustomErrorController.handleException({})", ex.getClass().getSimpleName(), ex);

        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        return "error/error";
    }
}
