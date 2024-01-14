package authserver.payload.request;

public record SignInRequest(
        String email,
        String password
) {
}
