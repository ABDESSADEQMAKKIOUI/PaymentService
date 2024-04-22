package com.example.PymentService.controller;

import com.example.PymentService.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaypalController {
    private PaypalService paypalService ;
    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping("/add")
    public RedirectView addPayment() {
        String cancelUrl = "https://localhost:8087/sucsses";
        String returnUrl = "https://localhost:8087";
        try {
          Payment payment = paypalService.crZatePayment(
                  10.0 ,
                  "USD" ,
                  "paypal",
                   "intent",
                  "description" ,
                  cancelUrl,
                  returnUrl);
          for (Links links : payment.getLinks()){
              if (links.getRel().equals("approval_url")){
                  return new RedirectView(links.getHref()) ;
              }

          }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return  new RedirectView("https://api.paypal.com");
    }
@GetMapping("/sucsses")
    public String paymentSucsses(
            @RequestParam("paymentId")String paymentId,
            @RequestParam("payerId")String payerId) {
        try {
           Payment payment = paypalService.executePayment(paymentId,payerId);
           if(payment.getState().equals("approved")) {
               return "sucsses";
           }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    return "sucsses";
}
    @GetMapping("/cancel")
    public String paymentCancled(){
        return "cancel";
    }

}
