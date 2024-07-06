package demo.aws.sample.graphql.repository;

import demo.aws.sample.graphql.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

}