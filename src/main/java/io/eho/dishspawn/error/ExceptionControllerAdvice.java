//package io.eho.dishspawn.error;
//
//import com.jogamp.opengl.Threading;
//import io.eho.dishspawn.exception.ResourceNotFoundException;
//import io.eho.dishspawn.exception.UsernameAlreadyExistsException;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//@RequestMapping("${server.error.path")
//public class ExceptionControllerAdvice {
//
//    public static final String DEFAULT_ERROR_VIEW = "ooops";
//
////    @ExceptionHandler(AuthenticationException.class)
////    public ModelAndView handleAuthenticationException(AuthenticationException ae) {
////        System.out.println("in handleAuthenticationException method in ExceptionControllerAdvice");
////        String message = ae.getMessage();
////        return errorView(message);
////    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ModelAndView resourceNotFound(ResourceNotFoundException rnfe) {
//        System.out.println("in resourceNotFound method in ExceptionControllerAdvice");
//        String message = rnfe.getMessage();
//        return errorView(message);
//    }
//
//    @ExceptionHandler(UsernameAlreadyExistsException.class)
//    public ModelAndView usernameAlreadyExists(UsernameAlreadyExistsException uaee) {
//        System.out.println("in usernameAlreadyExists method in ExceptionControllerAdvice");
//        String message = uaee.getMessage();
//        return errorView(message);
//    }
//
////    @ExceptionHandler(NullPointerException.class)
////    public ModelAndView generalNPE(NullPointerException npe) {
////        System.out.println("in generalNPE method in ExceptionControllerAdvice");
////        String message = "not found";
////        return errorView(message);
////    }
//
////    @ExceptionHandler(Exception.class)
////    public ModelAndView backupException(Exception e) {
////        System.out.println("in backup exception handler");
////        String message = e.getMessage();
////        return errorView(message);
////    }
////
////    @ExceptionHandler(Exception.class)
////    public ModelAndView defaultExceptionHandler() {
////
////    }
//
//
////    @ExceptionHandler(Exception.class)
////    public ModelAndView defaultExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
////
////        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
////            throw e;
////        }
////
////        System.out.println("in default exception handler method");
////        System.out.println(e);
////        System.out.println(req.getRequestURL());
////        System.out.println(e.getMessage());
////
////        ModelAndView mav = new ModelAndView();
////        String message = e.getMessage();
////        mav.addObject("exception", e);
////        mav.addObject("url", req.getRequestURL());
////        mav.addObject("message", message);
////        mav.setViewName(DEFAULT_ERROR_VIEW);
////        return mav;
////    }
//
//    private ModelAndView errorView(String message) {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("message", message).setViewName(DEFAULT_ERROR_VIEW);
//        return mav;
//    }
//}
