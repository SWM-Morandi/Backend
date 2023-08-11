package swm_nm.morandi.test.repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import swm_nm.morandi.test.entity.Test;

import java.util.List;
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByMember_MemberId(Long memberId);
    List<Test> findAllByMember_MemberIdOrderByTestDateDesc(Long memberId, Pageable pageable);
}
