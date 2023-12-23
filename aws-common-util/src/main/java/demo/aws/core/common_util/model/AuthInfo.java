package demo.aws.core.common_util.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private long userId;
    private String loginId;
    private List<String> roleName;
}
