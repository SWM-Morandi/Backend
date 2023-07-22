package swm_nm.morandi.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swm_nm.morandi.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //OAuth 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드
    Optional<Member> findByEmail(String email);
}
