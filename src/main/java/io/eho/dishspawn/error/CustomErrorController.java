//package io.eho.dishspawn.error;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class CustomErrorController implements ErrorController {
//
//    @RequestMapping("/ooops")
//    public ModelAndView handleError(HttpServletRequest req) {
//
//        System.out.println("calling handleError in ErrorController");
//
//        ModelAndView mav = new ModelAndView("ooops");
//        String errorMessage = "";
//        int httpErrorCode = getErrorCode(req);
//
//        switch (httpErrorCode) {
//            case 400:
//                errorMessage = "400 - Bad Request";
//                break;
//            case 401:
//                errorMessage = "401 - Incorrect authorization";
//                break;
//            case 404:
//                errorMessage = "404 - Resource not found";
//                break;
//            default:
//                errorMessage = "Something went wrong";
//        }
//
//        mav.addObject("message", errorMessage);
//        return mav;
//
//    }
//
//    private int getErrorCode(HttpServletRequest req) {
//        return (Integer) req.getAttribute("javax.servlet.error.status_code");
//    }
//}