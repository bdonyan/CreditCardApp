package com.shepherdmoney.interviewproject.controller;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.service.CreditCardService;
import com.shepherdmoney.interviewproject.vo.request.AddCreditCardToUserPayload;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import com.shepherdmoney.interviewproject.vo.response.CreditCardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("/credit-card")
    public ResponseEntity<Integer> addCreditCardToUser(@RequestBody AddCreditCardToUserPayload payload) {
        try {
            CreditCard creditCard = creditCardService.addCreditCardToUser(
                    payload.getUserId(),
                    payload.getCardNumber(),
                    payload.getCardIssuanceBank() // This should match the field name in AddCreditCardToUserPayload
            );
            return ResponseEntity.ok(creditCard.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Consider logging the exception or returning its message
        }
    }

    @GetMapping("/credit-cards/all")
    public ResponseEntity<List<CreditCardView>> getAllCardsOfUser(@RequestParam int userId) {
        List<CreditCard> cards = creditCardService.getAllCardsByUserId(userId);
        List<CreditCardView> cardViews = cards.stream()
                .map(card -> new CreditCardView(card.getId(), card.getNumber(), card.getIssuanceBank()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cardViews);
    }

    @GetMapping("/credit-card/user-id")
    public ResponseEntity<Integer> getUserIdForCreditCard(@RequestParam String creditCardNumber) {
        Integer userId = creditCardService.getUserIdByCreditCardNumber(creditCardNumber);
        return userId != null ? ResponseEntity.ok(userId) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/credit-card/update-balance")
    public ResponseEntity<String> updateCreditCardBalance(@RequestBody List<UpdateBalancePayload> payloads) {
        try {
            creditCardService.updateCardBalances(payloads);
            return ResponseEntity.ok("Balances updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}