package software.cstl.repository;

import software.cstl.domain.MonthlySalaryDtl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.MonthType;

import java.util.List;

/**
 * Spring Data  repository for the MonthlySalaryDtl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlySalaryDtlRepository extends JpaRepository<MonthlySalaryDtl, Long>, JpaSpecificationExecutor<MonthlySalaryDtl> {

    List<MonthlySalaryDtl> findAllByMonthlySalary_Id(Long monthlySalaryId);

    @Query(value = "select m from MonthlySalaryDtl m where m.id=?1", nativeQuery = false)
    List<MonthlySalaryDtl> findAllById(Long id);

    List<MonthlySalaryDtl> findAllByMonthlySalary_YearAndMonthlySalary_MonthAndMonthlySalary_Department_IdAndEmployee_Designation_id(Integer year, MonthType monthType, Long departmentId, Long designationId);

    List<MonthlySalaryDtl> findAllByMonthlySalary_YearAndMonthlySalary_MonthAndMonthlySalary_Department_Id(Integer year, MonthType monthType, Long departmentId);

    List<MonthlySalaryDtl> findAllByMonthlySalary_YearAndMonthlySalary_Month(Integer year, MonthType monthType);


    List<MonthlySalaryDtl> findAllByMonthlySalary_YearAndMonthlySalary_MonthAndEmployee_Department_Id(Integer year, MonthType monthType,  Long departmentId);
}
