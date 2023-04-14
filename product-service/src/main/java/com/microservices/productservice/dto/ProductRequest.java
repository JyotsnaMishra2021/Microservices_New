package com.microservices.productservice.dto;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class ProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
}
