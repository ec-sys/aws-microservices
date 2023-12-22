package demo.aws.core.common_util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTPayloadDto {
    private long userId;
    private String loginId;
    private List<String> roleNames;
}
