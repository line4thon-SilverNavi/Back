package org.likelion._thon.silver_navi.domain.user.service;

import org.likelion._thon.silver_navi.domain.user.entity.User;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignInReq;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignInRes;
import org.likelion._thon.silver_navi.domain.user.web.dto.SignUpReq;
import org.likelion._thon.silver_navi.domain.user.web.dto.UserDetailsRes;

public interface UserService {
     void signUp(SignUpReq signUpReq);

     SignInRes signIn(SignInReq signInReq);

     UserDetailsRes userDetails(User user);
}
