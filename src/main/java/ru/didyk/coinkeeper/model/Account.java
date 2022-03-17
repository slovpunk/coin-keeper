package ru.didyk.coinkeeper.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "balance")
    private Long balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
