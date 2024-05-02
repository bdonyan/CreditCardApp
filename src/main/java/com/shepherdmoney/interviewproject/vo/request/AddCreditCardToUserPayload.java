package com.shepherdmoney.interviewproject.vo.request;

import com.shepherdmoney.interviewproject.model.CreditCard;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class AddCreditCardToUserPayload {

    private int userId;

    private String cardIssuanceBank;

    private String cardNumber;
}
