package com.tdt.sys.modular.third.factory;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.request.*;

/**
 * OAuth2 请求的构建器
 *
 * @author www.qiqucode.com
 * @Date 2019/6/9 16:49
 */
public class OAuthRequestFactory {

    /**
     * 服务器基础地址
     */
    private static final String BASE_URL = "http://120.78.198.249:8080/tdt-vip-main";

    /**
     * 根据具体的授权来源，获取授权请求工具类
     *
     * @author www.qiqucode.com
     * @Date 2019/6/9 16:49
     */
    public static AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case "dingtalk":
                authRequest = new AuthDingTalkRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/dingtalk")
                        .build());
                break;
            case "baidu":
                authRequest = new AuthBaiduRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/baidu")
                        .build());
                break;
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/github")
                        .build());
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/gitee")
                        .build());
                break;
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/weibo")
                        .build());
                break;
            case "coding":
                authRequest = new AuthCodingRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/tencentCloud")
                        .build());
                break;
            case "tencentCloud":
                authRequest = new AuthTencentCloudRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/tencentCloud")
                        .build());
                break;
            case "oschina":
                authRequest = new AuthOschinaRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/oschina")
                        .build());
                break;
            case "alipay":
                // 支付宝在创建回调地址时，不允许使用localhost或者127.0.0.1，所以这儿的回调地址使用的局域网内的ip
                authRequest = new AuthAlipayRequest(AuthConfig.builder()
                        .clientId("2019100868170274")
                        .clientSecret("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDJkdHUSMtZECwjvnQkILccEhyI22tLnLgEniPDlPeBj9rseC8pdkEcfwsyj1VERyVM+v9jzaV+/NIluxbUQLy+MquXTFhCLGBjbxjP94upMyl2L3obrM2DWcQvn83PScydJSeRhiJcfWheaNFzWI0GZ35rTTPdeapkfmiko/TqOhmSPQKEF638ropLb/UPc4DzbTDl6WyVRVS6pSPb6cvXtfQ9YxO+TjhpDGk0ildwY5qplKG+I6WAdUDMBqbqLxU8P00+mhP/MSIxNoq6UN1A6MLbbNkWwDAXInO8vhmEkk09R4rorPAtQKxKoMXA8MWnQn87Bj8E235ykNx/2t7HAgMBAAECggEAIQbFbJTozJT7xneELA84MV8+UaYx9FefRxJqJbeYvh/F7QzcHV0E2hhuyz/05pUJpY1SnxWo0h+2hvwah3h79GX0+3tE0sLWoDQpyplyxqxdVMH+rd4oZiclH/B9FkOvROg7jXp+0syxdxaRiuatZKk8a3Rg1R8ELicQGjnK/T1w+2/qDQPSteoK+LJPgcsHEdUWZWTYVWqMb1cShdW00SqxmjFJhIijDFPhuaS3ieS7snbK7kartYK+vAVypD6vvD23LlbMEJ4+2s+E/H0psZKvnSQLYutesj3s0ClPErPmzcD8wUBYv3yC2BAZcyi2kEd325tI6vpTwxng3c0OwQKBgQD6e8zi/piNj6k+oeflcvjBodDzv1gNqrd/vvvYz8fbxeKzk6sTv0nGHjbPN7XCEPuP5JSfs9mgYvAQgzG02BTPwChE8+gPNPOjQUdx4jPWVMW+JT7NSzTV1tQ54V63NoA5aK+FYDyyx7cS1JvdH2tKFh1Sh1K1ff/TOZGSl7fg1wKBgQDOAj9bh6UuyL3As6gxdl6YS40SIXvrtl6tyrRbwwgspsrwD4tgmjc8oMgC2Ykr4zDKFdsFlz57Q8wh3dS9Mx0uMbhyYYkA2K4hmZ9InthEOQmTeRvFia29Y7fEUiS076OERVEnwyfDt+aFqN9Y/EE1vPaicuAbSCCxh7ozEz4DkQKBgFbTJQEmGUEwpAzW2Gg7gyiza5eiIuaR34uDaw8Lbw6qOFvg+vMcYnkvLfaELBe6BUkqha4aDHzA7s/6efdi00v0IsFL/ouO4kkbrqU2MJvBxM2VYYCN6iGZlvT9PvSpCqdbt0O3STL7cWUwl1p9spX6tm1OBQhuRYg1M1WqC43/AoGBAIJQCaPseiDN+roa6xnetpe/ssz1QV4pTlrBDrW8+T7Fgzm3m4LQCoDfc6XZ1Vtdq08p5BH1SiDPEiGaIShNXPgLm5E/AVr3ta6rboaRdFwzeEtBIMAkBzL2ERiunWmW7OiOsHdmi5DSk4+Y8y9m4hkMHzdDU1/ukD+8i5wjX+cBAoGAJiJj5EbrPlmS52RLIuJC9k8AP/1BDyg0P4/nmEbrMHCYnwdLoQOg20HyZPCNq0ZnmEtepH1Tbqxbaq3hBoV0HHR3NlVDnuHKk5MET5RI4Fi+719nwctBdPR4IXvMnCTi6+F0/EnmVmMIcnVbx8xUNMVKgy7RIbJT9MftMpRLH0I=")
                        .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhD+UZ36NcMGxoJHAYU3JKTmf2VWRXcdnWlyD9fFDnxvpoYpAYl+lPUAAvwxRh89ECZoHOLPGJic5cDsG7lXQ2jmKodYsLL/9jVXmOewoQpzTPcKW3cmZWYj2n38TQ+nuVyKSZic9upLTRA3z4PU8AOgyf3Q0Up+zWMrx4+x15PCqe6NJkSDkADQpIDH7jpRsF5rsUvJ0tm1Sie4Q+tQSWhtB3HE2UEEPTEWI4ercn1dBJ9DpcbsIDeD8QIyyilMlxgxuyogM3Qwzbpx/dEEq//4M69G7HDwejqj4uvF9J05IuNvIjMdcd1DGXNoMMTybjziRLwK6/0PThSzzjRrlKwIDAQAB")
                        .redirectUri(BASE_URL + "/oauth/callback/alipay")
                        .build());
                break;
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/qq")
                        .build());
                break;
            case "wechat":
                authRequest = new AuthWeChatRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/wechat")
                        .build());
                break;
            case "csdn":
                authRequest = new AuthCsdnRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/wechat")
                        .build());
                break;
            case "taobao":
                authRequest = new AuthTaobaoRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/taobao")
                        .build());
                break;
            case "google":
                authRequest = new AuthGoogleRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/google")
                        .build());
                break;
            case "facebook":
                authRequest = new AuthFacebookRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/facebook")
                        .build());
                break;
        }

        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }

        return authRequest;
    }

}
