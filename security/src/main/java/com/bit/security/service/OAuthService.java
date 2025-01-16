package com.bit.security.service;

import com.bit.security.model.AuthDTO;
import com.bit.security.model.KaKaoMemberInfo;
import com.bit.security.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {
    @Autowired
  private BCryptPasswordEncoder passwordEncoder;

//    @Autowired
//  private UserService USER_SERVICE;
    @Autowired
    private UserService userService;


  @Override
    public OAuth2User loadUser(OAuth2UserRequest req){
      OAuth2User oAuth2User=super.loadUser(req);
      KaKaoMemberInfo memberInfo=new KaKaoMemberInfo(oAuth2User.getAttributes());

      String email=memberInfo.getEmail();
      UserDTO userDTO=userService.selectByEmail(email);

    System.out.println(email);

      if(userDTO != null){
        System.out.println("if 문 통과");
        return new AuthDTO(userDTO,memberInfo.getAttributes());
      }else{
        System.out.println("else 문 통과");
        UserDTO temp=new UserDTO();
        temp.setUsername(email.substring(0,email.indexOf('@')).replace("@",""));
        temp.setPassword(passwordEncoder.encode("password"));
        temp.setNickname(memberInfo.getName());
        temp.setEmail(email);
        System.out.println(temp);
        userService.insert(temp);

        return new AuthDTO(userService.selectByEmail(email),memberInfo.getAttributes());
      }
  }

}
