package demo.aws.backend.order.domain.model;

import lombok.Data;

@Data
public class OrderRecipient {
    private String name;
    private String phoneNumber;
    private Address address;
}
