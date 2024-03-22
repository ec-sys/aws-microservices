package demo.aws.backend.chat.repository;

import demo.aws.backend.chat.domain.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String> {
    List<Member> findByIdIn(List<String> memberIds);
}

