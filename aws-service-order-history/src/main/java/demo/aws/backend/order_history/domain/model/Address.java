package demo.aws.backend.order_history.domain.model;

import lombok.Data;

@Data
public class Address {
    private int proviceId;
    private int districtId;
    private int wardId;
    private String detail;
}
