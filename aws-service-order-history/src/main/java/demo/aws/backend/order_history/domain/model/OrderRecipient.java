package demo.aws.backend.order_history.domain.model;

import lombok.Data;

@Data
public class OrderRecipient {
    private String name;
    private String phoneNumber;
    private Address address;
}
