package cz.cvut.fel.nss.orderservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    private Long userId;
    private List<CreateOrderItemRequest> items;
}