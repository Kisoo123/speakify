package com.project.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{
    private Long id;                 // 사용자 ID (고유값)
    private String username;          // 사용자명 (로그인 ID)
    private String password;          // 암호화된 비밀번호
    private String role;              // 권한 (예: USER, ADMIN)
    private String profilePictureUrl; // 프로필 사진 URL
    private String email;             // 이메일 주소
    private String displayName;       // 닉네임 또는 표시 이름
    private String statusMessage;     // 상태 메시지 (예: "온라인", "통화중" 등)
    private String uniqueTag; // 유저식별코드

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}