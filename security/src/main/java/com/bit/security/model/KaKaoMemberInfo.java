package com.bit.security.model;

import lombok.Data;

import java.util.Map;
@Data
public class KaKaoMemberInfo{
    private Map<String,Object> attributes;
    private Map<String,Object> accountAttributes;
    private Map<String,Object> profileAttributes;

    public KaKaoMemberInfo(Map<String,Object> paramMap){
        attributes = paramMap;
        accountAttributes = (Map<String,Object>)paramMap.get("kakao_account");

        profileAttributes = (Map<String,Object>) paramMap.get("profile");
    }

    

    public String getProviderId(){
        return attributes.get("id").toString();
    }

    public String getProvider(){
        return "kakao";
    }

    public String getName(){
        return accountAttributes.get("nickname").toString();
    }

    public String getEmail(){
        return accountAttributes.get("email").toString();
    }


}
