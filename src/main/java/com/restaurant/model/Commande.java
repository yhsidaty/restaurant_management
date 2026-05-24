package com.restaurant.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commandes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "plat_id")
    private Plat plat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommandeStatus status;

    @Column(nullable = false)
    private Integer tableNumber;

    @ManyToOne
    @JoinColumn(name = "serveur_id")
    private User serveur;

    @PrePersist
    protected void onCreate() {
        if (date == null) date = LocalDateTime.now();
        if (status == null) status = CommandeStatus.EN_ATTENTE;
    }
}
