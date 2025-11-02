package org.likelion._thon.silver_navi.domain.user.service;

import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.web.dto.*;

public interface UserService {
     void signUp(SignUpReq signUpReq);

     SignInRes signIn(SignInReq signInReq);

     UserDetailsRes userDetails(User user);

     void updateUser(User user, UserUpdateReq dto);

     UserInfoRes userInfo(User user);

    void updateRadius(User user, RadiusUpdateReq req);
}
