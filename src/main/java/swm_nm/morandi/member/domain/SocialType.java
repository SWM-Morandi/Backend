package swm_nm.morandi.member.domain;

public enum SocialType {
    GOOGLE("google"),
    GITHUB("github"),
    NAVER("naver");
    private final String provider;

    SocialType(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public static SocialType of(String provider) {
        for (SocialType type : SocialType.values()) {
            if (type.getProvider().equals(provider)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No such social type.");
    }
}