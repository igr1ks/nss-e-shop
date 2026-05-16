package cz.cvut.fel.nss.order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CreateOrderItemRequest {

    private Long productId;
    private int quantity;
}