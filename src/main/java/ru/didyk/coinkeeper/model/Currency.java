package ru.didyk.coinkeeper.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
TODO: Будет таблица с количеством разнообразной валюты, и будем использовать ProductCategory для
 добавления информации в эту таблицу переводя по текущему курсу.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "course")
    private Double course;

    @Column(name = "balance")
    private Long balance; //количество
}
