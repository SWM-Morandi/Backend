package swm_nm.morandi.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swm_nm.morandi.auth.response.GoogleUserDto;
import swm_nm.morandi.auth.response.TokenDto;
import swm_nm.morandi.auth.security.JwtProvider;
import swm_nm.morandi.auth.security.SecurityUtils;
import swm_nm.morandi.exception.LoginAppException;
import swm_nm.morandi.exception.LoginErrorCode;
import swm_nm.morandi.member.domain.Member;
import swm_nm.morandi.member.dto.MemberDto;
import swm_nm.morandi.member.dto.RegisterInfoDto;
import swm_nm.morandi.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    public TokenDto registerMember(GoogleUserDto googleUserDto){
    //TODO
        //이부분에 oidc 검증로직 넣기





        //이메일이나 아이디가 이미 존재하는지 검증하는 로직도 넣기
        Optional<Member> findMember = memberRepository.findByEmail(googleUserDto.getEmail());
        //이메일이나 아이디가 존재하지 않으면 회원가입 진행
        if(findMember.isEmpty()) {
            findMember = Optional.of(Member.builder()
                    .email(googleUserDto.getEmail())
                    .nickname(googleUserDto.getName())
                    .thumbPhoto(googleUserDto.getPicture())
                    .socialInfo(googleUserDto.getType())
                    .build());
            memberRepository.save(findMember.get());
            TokenDto tokenDto = jwtProvider.getTokens(findMember.get());
            System.out.println("tokenDto = " + tokenDto);
            return tokenDto;
        }
        //토큰 발급 로직 넣기
        TokenDto tokenDto = jwtProvider.getTokens(findMember.get());
        System.out.println("tokenDto = " + tokenDto);
        return tokenDto;

    }
    public void editProfile(Long memberId, MemberDto memberDto) {
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.get();
        member.editProfile(memberDto.getNickname(), memberDto.getBojId(), memberDto.getThumbPhoto());
        // memberRepository.save(member);
    }
    public String getBojId(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.get();
        return member.getBojId();
    }

    public RegisterInfoDto memberInitialize(RegisterInfoDto registerInfoDto) {
        Long userId = SecurityUtils.getCurrentUserId();
        Member member = memberRepository.findById(userId).orElseThrow(()-> new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND,"Username not found"));
        member.setBojId(registerInfoDto.getBojId());

        return registerInfoDto;


    }
}