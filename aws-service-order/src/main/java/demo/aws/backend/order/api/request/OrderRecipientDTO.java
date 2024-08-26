package demo.aws.backend.order.api.request;

import lombok.Data;

@Data
public class OrderRecipientDTO {
    private String userName;
    private String email;
    private String phoneNumber;
    private int proviceId;
    private int districtId;
    private int wardId;
    private String detail;
}
