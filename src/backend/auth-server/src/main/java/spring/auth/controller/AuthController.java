package spring.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.auth.common.annotation.ApiErrorException;
import spring.auth.common.exception.ExceptionStatusProvider;
import spring.auth.common.exception.docs.OtherServiceExceptionDocs;
import spring.auth.common.exception.docs.SignInExceptionDocs;
import spring.auth.common.exception.docs.SignUpExceptionDocs;
import spring.auth.common.exception.response.ClientUnauthorizedException;
import spring.auth.config.jwt.JwtUtils;
import spring.auth.config.security.UserDetailsImpl;
import spring.auth.dto.request.ChangePasswordRequest;
import spring.auth.dto.request.SignInRequest;
import spring.auth.dto.request.SignUpRequest;
import spring.auth.dto.response.UserAndTokenResponse;
import spring.auth.dto.response.UserInfoResponse;
import spring.auth.service.UserService;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="Auth", description="Auth API")
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;



    // TODO annotation 중복 해결

    @Operation(summary = "Post Client SignUp", description = "클라이언트의 회원가입 요청을 수행합니다")
    @ApiErrorException(SignUpExceptionDocs.class)
    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signUpUser(@Validated @RequestBody SignUpRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }
        return userService.signUp(request);
    }

    @Operation(summary = "Post Client SignIn", description = "클라이언트의 로그인 요청을 수행합니다")
    @ApiErrorException(SignInExceptionDocs.class)
    @PostMapping("/signin")
    public ResponseEntity<UserAndTokenResponse> signInUser(@Validated @RequestBody SignInRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }
        return userService.signIn(request);
    }

    @Operation(summary = "Post Client SignOut", description = "회원의 로그아웃 요청을 수행합니다")
    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(HttpServletRequest request) {
        return userService.signOut(request);
    }

    @Operation(summary = "Post Access Token Refresh", description = "회원의 액세스 토큰을 재발급합니다")
//    @SecurityRequirement(name = "access-token")
    @ApiErrorException(OtherServiceExceptionDocs.class)
    @PostMapping("/refresh-token")
    public ResponseEntity<UserAndTokenResponse> refreshToken(HttpServletRequest request) {

        return userService.refreshToken(request);
    }

    @Operation(summary = "Post Password Change", description = "회원의 비밀번호를 변경합니다")
    @PostMapping("/password-change") // TODO PatchMapping?
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Validated @RequestBody ChangePasswordRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }
        if (userDetails == null) { // TODO UserDetailsImpl null 인 경우 처리
            throw ClientUnauthorizedException.EXCEPTION;
        }
        return userService.changePassword(userDetails.getUser(), request);
    }

    @Operation(summary = "Post Profile Image", description = "회원의 프로필 이미지 등록 요청을 수행합니다")
    @PostMapping("/profile-img")
    public ResponseEntity<?> uploadProfileImg(@AuthenticationPrincipal UserDetailsImpl userDetails, @Parameter(name="이미지 URL") @RequestParam("image") String imageURL) {
        if (userDetails == null) { // TODO UserDetailsImpl null 인 경우 처리
            throw ClientUnauthorizedException.EXCEPTION;
        }
        return userService.uploadProfile(userDetails.getUser(), imageURL);
    }

    @Operation(summary = "Patch Password Change", description = "회원의 프로필 이미지 삭제 요청을 수행합니다")
    @PatchMapping("/delete/profile-img")
    public ResponseEntity<?> deleteProfileImg(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) { // TODO UserDetailsImpl null 인 경우 처리
            throw ClientUnauthorizedException.EXCEPTION;
        }
        return userService.deleteProfile(userDetails.getUser());
    }

}
