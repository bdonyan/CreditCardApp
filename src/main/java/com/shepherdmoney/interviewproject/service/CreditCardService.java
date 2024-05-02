package com.shepherdmoney.interviewproject.service;

import com.shepherdmoney.interviewproject.model.CreditCard;
import com.shepherdmoney.interviewproject.model.User;
import com.shepherdmoney.interviewproject.repository.CreditCardRepository;
import com.shepherdmoney.interviewproject.repository.UserRepository;
import com.shepherdmoney.interviewproject.vo.request.UpdateBalancePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private UserRepository userRepository;

    public CreditCard addCreditCardToUser(int userId, String cardNumber, String bank) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        CreditCard newCard = new CreditCard(bank, cardNumber, user); // Ensure this constructor exists.
        return creditCardRepository.save(newCard);
    }

    public List<CreditCard> getAllCardsByUserId(int userId) {
        return creditCardRepository.findByOwnerId(userId);
    }

    public Integer getUserIdByCreditCardNumber(String creditCardNumber) {
        return creditCardRepository.findByNumber(creditCardNumber)
                .map(CreditCard::getOwner)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("Credit card not found or no associated user"));
    }

    public void updateCardBalances(List<UpdateBalancePayload> payloads) {
        payloads.forEach(payload -> {
            CreditCard card = creditCardRepository.findByNumber(payload.getCreditCardNumber())
                    .orElseThrow(() -> new RuntimeException("Credit card not found"));
            card.getBalanceMap().put(payload.getBalanceDate(), payload.getBalanceAmount());
            creditCardRepository.save(card);
        });
    }
}