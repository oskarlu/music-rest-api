package se.sevenfeet.musicapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;

@Controller
public class CatchAllErrorController implements ErrorController {

    public static final Logger LOG = LoggerFactory.getLogger(CatchAllErrorController.class);

    @RequestMapping("/error")
    public String handleError(WebRequest request, Model model) {
        LOG.debug("*** CatchAllErrorController.handleError({})", request.getContextPath());
        HttpStatus httpStatus = getStatus(request);
        model.addAttribute("status", httpStatus.value());
        model.addAttribute("error", httpStatus.name());
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private HttpStatus getStatus(WebRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, RequestAttributes.SCOPE_REQUEST);

        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            LOG.error("Exception while trying to get HttpStatus from statusCode: {}", statusCode);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
