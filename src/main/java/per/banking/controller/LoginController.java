package per.banking.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {
    @GetMapping("/register")
    @ResponseBody
    public String create(){
        System.out.println("hello");
        return("hello");
    }

}
