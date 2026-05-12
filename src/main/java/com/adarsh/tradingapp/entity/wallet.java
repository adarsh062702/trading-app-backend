package com.adarsh.tradingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name ="wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal balance;

    @JsonIgnoreProperties("wallet")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
