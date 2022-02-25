package com.devjsky.android.whereuat.model;

import lombok.Data;

/**
 * ClassName            KakaoUser
 * Created by JSky on   2022-02-22
 * <p>
 * Description
 */
@Data
public class KakaoUser {
    public Long userId;
    public String email;
    public String nickname;
    public String profileImageUrl;
    public String kakaoUserToken;
}
