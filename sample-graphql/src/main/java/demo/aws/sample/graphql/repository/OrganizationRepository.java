package demo.aws.sample.graphql.repository;

import demo.aws.sample.graphql.domain.entity.Organization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Integer>, JpaSpecificationExecutor<Organization> {
}
