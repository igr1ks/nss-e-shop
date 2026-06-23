package cz.cvut.fel.nss.order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AvailabilityResponse {

    private Long productId;
    private int requestedQuantity;
    private boolean available;
}