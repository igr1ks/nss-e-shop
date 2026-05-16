package cz.cvut.fel.nss.order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    private List<CreateOrderItemRequest> items;
}