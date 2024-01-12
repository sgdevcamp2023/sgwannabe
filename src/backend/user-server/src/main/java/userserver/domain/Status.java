package userserver.domain;

public enum Status {

    ENABLE, // 활성화(이메일 인증 완료)
    DISABLE, // 비활성화(블락 처리)
    QUIT // 탈퇴
}
