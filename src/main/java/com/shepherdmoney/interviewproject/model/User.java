package com.shepherdmoney.interviewproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "MyUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String email;

    // TODO: User's credit card
    // HINT: A user can have one or more, or none at all. We want to be able to query credit cards by user
    //       and user by a credit card.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CreditCard> cards = new HashSet<>();

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // add credit card to the owner
    public void addCard(CreditCard creditCard) {
        this.cards.add(creditCard);
        creditCard.setOwner(this);
    }

    // remove credit card from the owner
    public void removeCard(CreditCard creditCard) {
        this.cards.remove(creditCard);
        creditCard.setOwner(null);
    }
}
