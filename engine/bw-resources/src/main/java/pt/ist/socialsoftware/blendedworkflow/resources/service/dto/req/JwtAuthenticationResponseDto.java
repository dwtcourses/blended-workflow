package pt.ist.socialsoftware.blendedworkflow.resources.service.dto.req;

public class JwtAuthenticationResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
