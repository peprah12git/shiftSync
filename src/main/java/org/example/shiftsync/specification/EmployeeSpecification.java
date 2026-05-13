package org.example.shiftsync.specification;

import org.example.shiftsync.Entity.Employee;
import org.example.shiftsync.enums.EmploymentType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> hasFullName(String name) {
        return (root, query, criteriaBuilder) -> name == null ? null
                : criteriaBuilder.like(
                criteriaBuilder.lower(root.join("user").get("fullName")),
                "%" + name.toLowerCase() + "%"
        );
    }

    public static Specification<Employee> hasDepartment(Long departmentId) {
        return (root, query, criteriaBuilder) -> departmentId == null ? null
                : criteriaBuilder.equal(root.get("department").get("id"), departmentId);
    }

    public static Specification<Employee> hasEmploymentType(EmploymentType employmentType) {
        return (root, query, criteriaBuilder) -> employmentType == null ? null
                : criteriaBuilder.equal(root.get("employmentType"), employmentType);
    }

    public static Specification<Employee> hasLocation(Long locationId) {
        return (root, query, criteriaBuilder) -> locationId == null ? null
                : criteriaBuilder.equal(root.get("primaryLocation").get("id"), locationId);
    }

    public static Specification<Employee> isActive(Boolean active) {
        return (root, query, criteriaBuilder) -> active == null ? null
                : criteriaBuilder.equal(root.get("isActive"), active);
    }

    /**
     * Restricts results to employees whose primaryLocation is in the given list.
     * Used to scope MANAGER queries to only their assigned locations.
     */
    public static Specification<Employee> hasLocationIn(List<Long> locationIds) {
        return (root, query, criteriaBuilder) ->
                (locationIds == null || locationIds.isEmpty()) ? criteriaBuilder.disjunction()
                        : root.get("primaryLocation").get("id").in(locationIds);
    }
}
