package com.lalala.passport.component;

public record UserInfo(
        Long id,
        String email,
        String nickname,
        String role,
        Boolean isActivated,
        String accessedAt,
        String createdAt,
        String deletedAt) {
    @Override
    public String deletedAt() {
        if (deletedAt == null) return "";
        return deletedAt;
    }

    public boolean isAdmin() {
        return role.equals("ADMIN");
    }
}
