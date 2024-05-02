package com.shepherdmoney.interviewproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String issuanceBank;

    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public CreditCard(String bank, String cardNumber, User user) {
        this.issuanceBank = bank;
        this.number = cardNumber;
        this.owner = user;
    }

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BalanceHistory> balanceHistories = new ArrayList<>();

    // TreeMap for fast operations as listed
    @Transient
    private NavigableMap<LocalDate, Double> balanceMap = new TreeMap<>();

    // ADDITIONAL NOTE: For the balance history, you can use any data structure that you think is appropriate.
    //        It can be a list, array, map, pq, anything. However, there are some suggestions:
    //        1. Retrieval of a balance of a single day should be fast
    //        2. Traversal of the entire balance history should be fast
    //        3. Insertion of a new balance should be fast
    //        4. Deletion of a balance should be fast
    //        5. It is possible that there are gaps in between dates (note the 04-13 and 04-16)
    //        6. In the condition that there are gaps, retrieval of "closest **previous**" balance date should
    //        also be fast. Aka, given 4-15, return 4-13 entry tuple

    // fills map based on arrayList structure
    private void fillMap() {
        balanceMap.clear();
        for (BalanceHistory entry : balanceHistories) {
             balanceMap.put(entry.getDate(), entry.getBalance());
        }
    }

    // add balance and records transaction
    public void addBalance(BalanceHistory balanceHistory) {
        this.balanceHistories.add(balanceHistory);
        balanceHistory.setCreditCard(this);
        this.balanceMap.put(balanceHistory.getDate(), balanceHistory.getBalance());
    }

    // get current or closest previous balance
    public Double getBalance(LocalDate date) {
        if (balanceMap.containsKey(date)) {
            return balanceMap.get(date);
        }
        if (balanceMap.floorKey(date) == null) {
            return null;
        }
        return balanceMap.get(balanceMap.floorKey(date));
    }
}
