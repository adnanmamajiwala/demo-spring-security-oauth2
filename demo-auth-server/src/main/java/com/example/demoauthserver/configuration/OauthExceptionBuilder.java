package com.example.demoauthserver.configuration;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;

import static org.springframework.security.oauth2.common.exceptions.OAuth2Exception.create;

@Service
public class OauthExceptionBuilder {

    public OAuth2Exception build(Exception e) {
        OAuth2Exception oAuth2Exception = e instanceof OAuth2Exception ? (OAuth2Exception) e : create(null, e.getMessage());
        addErrorCode(oAuth2Exception);
        return oAuth2Exception;
    }

    private void addErrorCode(OAuth2Exception oAuth2Exception) {
        String errorCode = oAuth2Exception.getOAuth2ErrorCode();
        oAuth2Exception.addAdditionalInformation("title", errorCode);
        oAuth2Exception.addAdditionalInformation("detail", oAuth2Exception.getMessage());
        oAuth2Exception.addAdditionalInformation("status", String.valueOf(oAuth2Exception.getHttpErrorCode()));
    }
}
