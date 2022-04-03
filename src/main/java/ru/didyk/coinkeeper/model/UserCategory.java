package ru.didyk.coinkeeper.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user_category")
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user;

    private boolean spending = Boolean.TRUE;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCategory", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Currency> currencies;

}
