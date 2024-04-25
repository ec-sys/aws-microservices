package demo.aws.backend.order.domain.model;

import lombok.Data;

@Data
public class Address {
    private int proviceId;
    private int districtId;
    private int wardId;
    private String detail;
}
