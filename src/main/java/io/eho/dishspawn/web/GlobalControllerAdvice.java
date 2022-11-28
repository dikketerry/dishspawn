//package io.eho.dishspawn.web;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
////    @ExceptionHandler({})
//    public String handleNotFound(HttpServletRequest request, Model model) {
//        model.addAttribute("url", request.getRequestURL());
//        return "404";
//    }
//}
