package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.ExternalUserAccount;
import com.training.blog.Entities.RefreshToken;
import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Enum.BusinessHttpCode;
import com.training.blog.Enum.EmailTemplateEngine;
import com.training.blog.Enum.LoginType;
import com.training.blog.Exception.CustomException.AppException;
import com.training.blog.Exception.CustomException.DataNotMatchException;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Payload.Request.LogoutRequest;
import com.training.blog.Payload.Request.RePasswordRequest;
import com.training.blog.Payload.Response.ResponseMessage;
import com.training.blog.Payload.Request.UserCredentialsRequest;
import com.training.blog.Payload.Response.UserInfo;
import com.training.blog.Security.JWTAuth.JWTService;
import com.training.blog.Service.Email.EmailService;
import com.training.blog.Service.ExternalIdentity.GoogleIdentityService;
import com.training.blog.Service.ExternalUserAccount.ExternalUserAccountService;
import com.training.blog.Service.InvalidToken.InvalidTokenService;
import com.training.blog.Service.RefreshToken.RefreshTokenService;
import com.training.blog.Service.UserToken.TokenService;
import com.training.blog.Utils.RandomCodeGenerator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final EmailService emailService;
    private final RandomCodeGenerator codeGenerator;
    private final TokenService tokenService;
    private final InvalidTokenService invalidTokenService;
    private final RefreshTokenService refreshTokenService;
    private final ExternalUserAccountService externalUserAccountService;
    private  final GoogleIdentityService googleIdentityService;

    public ResponseMessage login(UserCredentialsRequest login) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        MyUserDetail userDetail = (MyUserDetail) auth.getPrincipal();
        Users user = userDetail.getUser();
//        var refreshToken = "";
//        // check user's refresh token
//        if(refreshTokenService.checkRefreshToken(user.getRefreshToken())){
//            refreshToken = user.getRefreshToken().getRefreshToken();
//        } else{
//        RefreshToken refreshUserToken = refreshTokenService.createRefreshToken(user);
//        //save refresh token
//         refreshTokenService.save(refreshUserToken);
//         refreshToken = refreshUserToken.getRefreshToken();
//        }
        RefreshToken refreshToken = refreshTokenService.handle(user, user.getRefreshToken());
        var jwt = jwtService.generateToken(userDetail);
        return ResponseMessage.builder()
                .status(OK)
                .message("Login successfully")
                .data(new HashMap<>() {{
                    put("access_token", jwt);
                    put("refresh_token", refreshToken.getRefreshToken());
                }})
                .build();
    }

    //this method's used to reset the password when user forgot password
    @Transactional
    public ResponseMessage resetPassword(String email) throws MessagingException {
        Users user = userDao.findUsersByEmail(email)
                .orElseThrow(() -> new NotFoundEntityException("Your email does not exist", Users.class));
        String subject = "RESET PASSWORD FOR YOUR ACCOUNT";
        String resetPasswordCode = codeGenerator.generateRandomCode(6);
        User_Token user_token =
                User_Token.builder()
                        .token(resetPasswordCode)
                        .expiresAt(System.currentTimeMillis() + 3600000)
                        .user(Users.builder().email(email).build())
                        .build();
        //save token
        tokenService.saveUserToken(user_token);
        EmailTemplateEngine emailTemplate = EmailTemplateEngine.RESET_PASSWORD;
            emailService.sendResetPasswordCode(email,subject,resetPasswordCode,emailTemplate);
            return ResponseMessage.builder()
                    .status(OK)
                    .message("Reset password code has been sent to your email")
                    .build();
    }
    
    public ResponseMessage resetPassword(RePasswordRequest request) {
        if(request.getResetCode().isEmpty()) {
            throw new NullPointerException("Reset password code is empty");
        }
        // Check existing user
       Users user = userDao.findUsersByEmail(request.getEmail())
               .orElseThrow(()-> new AppException(BusinessHttpCode.USER_NOT_FOUND,"your email does not exist"));
        // kiểm tra resetCode có phải là của email muốn thay đổi không
        tokenService.validateResetPasswordToken(request.getEmail(), request.getResetCode());
        String encodeNewPassword = passwordEncoder.encode(request.getNewPassword());
        if(encodeNewPassword.equals(user.getPassword())){
            throw new AppException(BusinessHttpCode.NO_CHANGE_APPLY, "New password can not be same with old password");
        }
        // reset password
        userDao.resetPassword(
                request.getEmail(),
                passwordEncoder.encode(request.getNewPassword())
        );
        return ResponseMessage.builder()
               .status(OK)
               .message("Password reset successfully")
               .build();
    }

    public ResponseMessage activateAccount(String token) throws MessagingException {
        String user_email = tokenService.validateUserToken(token);
        userDao.validatedUser(user_email);
        return ResponseMessage.builder()
                .status(OK)
                .message("Your account has been activated")
                .build();
    }

    public ResponseMessage changePassword(RePasswordRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        //check email in SecurityContextHolder and email that is requested by client
        if(!email.equals(request.getEmail()))
            throw new AppException(BusinessHttpCode.NOT_ALLOW_DATA_SOURCE);
        Users user = userDao.findUsersByEmail(request.getEmail())
                .orElseThrow(()-> new AppException(BusinessHttpCode.USER_NOT_FOUND,"your email does not exist"));
        String encodeNewPassword = passwordEncoder.encode(request.getNewPassword());
        if(encodeNewPassword.equals(user.getPassword())){
            throw new AppException(BusinessHttpCode.NO_CHANGE_APPLY, "New password can not be same with old password");
        }
        userDao.changePassword(email, passwordEncoder.encode(request.getNewPassword()));
        return ResponseMessage.builder()
                .status(OK)
                .message("Your password has been changed")
                .build();
    } {}

    public ResponseMessage loginWithGoogle(String authCode){
        ResponseMessage message = new ResponseMessage();
        UserInfo userInfo = googleIdentityService.exchangeUserInfo(authCode);
        // the email is not existing --> neither google login nor traditional user account, save it
        Optional<Users> user = userDao.findUsersByEmail(userInfo.email());
        final MyUserDetail userDetail;
        if(user.isEmpty()){ //(1)
            Users userEntity = Users.builder()
                    .email(userInfo.email())
                    .enabled(true)
                    .lastName(userInfo.family_name())
                    .firstName(userInfo.given_name())
                    .avatar(userInfo.picture())
                    .build();
            ExternalUserAccount account = ExternalUserAccount.builder()
                    .provider(LoginType.GOOGLE)
                    .id(userInfo.id())
                    .user(userEntity)
                    .build();
            userEntity.setAccounts(new HashSet<ExternalUserAccount>(){{
                add(account);
            }});
       userDetail = new MyUserDetail(userDao.save(userEntity));
        } else { //user presents (2)
        Optional<ExternalUserAccount> account = externalUserAccountService.findAccountByEmail(userInfo.email());
            if(account.isPresent()){ // có account được tìm thấy mà user.email == userInfo.email
                if(account.get().getId().equals(userInfo.id())){ //kiểm tra id xem email đó có liên kết với tài khoản nào không
                    //kiem tra duoc id tai khoan trung voi id cua google khi dang nhap --> accepted
                userDetail = new MyUserDetail((account.get().getUser()));
                } else { // kiem tra duoc id tai khoan khong trung voi id cua google khi dang nhap --> not accepted
                    // tài khoản email cua google nay da duoc lien ket voi 1 tai khoan gg khac
                    //vd: ta dung abc@gmail.com nhung lai lien ket voi def@gmail.com
                    // luc nay se thong bao den nguoi dung "Tai khoan email cua ban hien dan lien ket voi 1 tai khoan google khac"
//                    message.setStatus(HttpStatus.ALREADY_REPORTED.value());
//                    message.setMessage("This account has been associated with another google account");
                    userDetail = null;
                    throw new AppException(BusinessHttpCode.ACCOUNT_ASSOCIATES_FAILED);
                }
            } else { //account.isEmpty() == true but there is an account with this email before
                // co tai khoan duoc tao boi email cua google nay truoc do roi
                // tra ve ket qua de client chuyen den trang lien ket --> bat nhap mat khau de lien ket
                message.setStatus(HttpStatus.ACCEPTED.value());
                message.setMessage("You had an account for this email ? You want to associate with this ?");
                message.setData(new HashMap<>() {{
                    put("email", userInfo.email());
                }});
                userDetail = null;
            }

        }
        if(userDetail != null){
           message.setStatus(HttpStatus.OK.value());
           message.setMessage("Login successfully");
           message.setData(new HashMap<>() {{
               put("access_token", jwtService.generateToken(userDetail));
           }});
        }
        return message;
    }

    public ResponseMessage logout(LogoutRequest req){
            invalidTokenService.save(req.getToken());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
            ResponseMessage response  = ResponseMessage.builder()
                    .status(OK)
                    .message("Log out successfully")
                    .build();
        return response;
    }

    public ResponseMessage refreshToken(String refreshToken) {
        if(!jwtService.isValidToken(refreshToken)) {
            throw new AppException(BusinessHttpCode.INVALID_REFRESH_TOKEN);
        }
       Users user =  userDao.getUsersByRefreshToken(refreshToken)
               .orElseThrow(()-> new AppException(BusinessHttpCode.USER_NOT_FOUND));
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if (auth == null) {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                   user.getEmail(),
                   user.getPassword()
           ));
       }
           var userDetail = (MyUserDetail) auth.getPrincipal();
           var jwt = jwtService.generateToken(userDetail);
           return ResponseMessage.builder()
                   .status(OK)
                   .message("Login successfully")
                   .data(new HashMap<>() {{
                       put("access_token", jwt);
                   }})
                   .build();
    }
}
