package io.eho.dishspawn.error;

import io.eho.dishspawn.exception.ResourceNotFoundException;
import io.eho.dishspawn.exception.UsernameAlreadyExistsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@RequestMapping("${server.error.path")
public class ExceptionControllerAdvice {

    public static final String DEFAULT_ERROR_VIEW = "ooops";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView resourceNotFound(ResourceNotFoundException rnfe) {
        System.out.println("in resourceNotFound method in ExceptionControllerAdvice");
        String message = rnfe.getMessage();
        return errorView(message);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ModelAndView usernameAlreadyExists(UsernameAlreadyExistsException uaee) {
        System.out.println("in usernameAlreadyExists method in ExceptionControllerAdvice");
        String message = uaee.getMessage();
        return errorView(message);
    }

    private ModelAndView errorView(String message) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message).setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
