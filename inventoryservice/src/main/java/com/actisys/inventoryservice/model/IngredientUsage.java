package com.actisys.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ingredient_usage")
public class IngredientUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "quantity_used")
    private BigDecimal quantityUsed;

    @Enumerated(EnumType.STRING)
    private UsageReason reason;

    @Column(name = "usage_date")
    private LocalDateTime usageDate;
}
