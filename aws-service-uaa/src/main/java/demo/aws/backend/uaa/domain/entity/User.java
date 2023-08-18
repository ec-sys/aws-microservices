package demo.aws.backend.uaa.domain.entity;

import demo.aws.core.framework.security.model.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private Date birthDate;
    private String address;
}
