package com.example.PymentService.services;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalService {
    private final APIContext apiContext ;

    public Payment crZatePayment(
            Double totalPayment ,
            String currency ,
            String methode ,
            String intent,
            String description ,
            String cancelUrl ,
            String returnUrl

    ) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f",totalPayment));
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod(methode);
        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        payment.setRedirectUrls(redirectUrls);
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId ,String payerId ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
       return payment.execute(apiContext,paymentExecution);
    }
}
