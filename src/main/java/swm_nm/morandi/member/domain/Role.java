package swm_nm.morandi.member.domain;

public enum Role {
    MEMBER("ROLE_MEMBER");
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}