package com.keeghan.eShop.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;

    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull()
    private Integer stockQuantity;

    private Long categoryId;

    private LocalDateTime createdAt;
}