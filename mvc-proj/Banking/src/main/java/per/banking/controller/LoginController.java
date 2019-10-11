package per.banking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import per.banking.entity.Customer;
import per.banking.repos.BankingRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {

    private static String userId;

    @Autowired
    BankingRepo bank;


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }


    @GetMapping("/user/checkbalance")
    public ResponseEntity<String> checkBalance(Authentication authentication) throws JsonProcessingException {
        List<Customer> abc = bank.findByUserName(authentication.getName());
        Customer cust = abc.get(0);
        String str ="Hello "+cust.getName()+ " Your balance is : "+cust.getBalance();
        return new ResponseEntity<String>(str, HttpStatus.OK);
    }

    @GetMapping("/user/withdraw")
    @ResponseBody
    public ResponseEntity<String> withdraw( @RequestParam String amount,Authentication authentication) throws JsonProcessingException {
        List<Customer> abc = bank.findByUserName(authentication.getName());
        Customer userCust = abc.get(0);
        Float initialBalance = userCust.getBalance();
        initialBalance = initialBalance - Float.parseFloat(amount);
        userCust.setBalance(initialBalance);
        bank.save(userCust);
        return new ResponseEntity<String>("Done", HttpStatus.OK);
    }

    @GetMapping("/user/deposit")
    @ResponseBody
    public ResponseEntity<String> deposit( @RequestParam String amount,Authentication authentication) throws JsonProcessingException {

        List<Customer> abc = bank.findByUserName(authentication.getName());
        Customer userCust = abc.get(0);
        Float initialBalance = userCust.getBalance();
        initialBalance = initialBalance + Float.parseFloat(amount);
        userCust.setBalance(initialBalance);
        bank.save(userCust);
        return new ResponseEntity<String>("Done", HttpStatus.OK);
    }


    @RequestMapping("/login")
    public String login(@RequestParam(value="username",required=false) String username) {
        if(username!=null){
        System.out.println(username);}
        return "login.html";
    }

    @RequestMapping("/home")
    public String login2() {
        return "home";
    }


    @RequestMapping("/bank")
    public String bank(Model model,Authentication authentication) {
//        System.out.println(authentication.getName());
        model.addAttribute("name",authentication.getName());
        return "bank";
    }

    @RequestMapping("/")
    public String bank2(Model model,Authentication authentication) {
//        System.out.println(authentication.getName());
        model.addAttribute("name",authentication.getName());
        return "bank";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    @GetMapping("/saveid")
    public String create(@RequestParam String userid) {
        if(userid!=null)
            userId= userid;
        System.out.println(userId);
        return ("saved id");
    }

}
