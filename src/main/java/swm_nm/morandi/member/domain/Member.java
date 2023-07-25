package swm_nm.morandi.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String nickname;
    private String bojId;

    @Column(length = 200000)
    private String thumbPhoto;

    @Enumerated(EnumType.STRING)
    private SocialType socialInfo;

    public void setBojId(String bojId) {
        this.bojId = bojId;
    }

    public void editProfile(String nickname, String bojId, String thumbPhoto) {
        this.nickname = nickname;
        this.bojId = bojId;
        this.thumbPhoto = thumbPhoto;
    }
}
