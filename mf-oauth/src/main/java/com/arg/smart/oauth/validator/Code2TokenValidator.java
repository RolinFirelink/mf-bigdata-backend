package com.arg.smart.oauth.validator;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.entity.AuthorizationCode;
import com.arg.smart.common.oauth.validator.IBaseValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cgli
 * @date: 2020/2/17 14:43
 */
@Component
public class Code2TokenValidator extends MultipleValidator {
    List<Class<? extends IBaseValidator<AuthorizationCode>>> validateCodeList = new ArrayList<>();

    public Code2TokenValidator() {
        this.validateClientList.add(ClientIdExistValidator.class);
        this.validateClientList.add(ClientSecretExistValidator.class);
        this.validateClientList.add(GrantTypeExistValidator.class);
        this.validateClientList.add(RedirectUriExistValidator.class);
        this.validateCodeList.add(ClientIdEqualValidator.class);
        this.validateCodeList.add(UriEqualValidator.class);
    }

    /**
     * code参数相关多个校验组合
     *
     * @param request
     * @param result
     * @return
     */
    public Result<AuthorizationCode> validateCode(HttpServletRequest request, Result<AuthorizationCode> result) {
        return validate(request, result, validateCodeList);
    }

    /**
     * 校验code换token两次传入的clientId是否一致
     */
    @Component
    public class ClientIdEqualValidator extends AbstractCodeValidator {
        @Override
        public Result<AuthorizationCode> validate(HttpServletRequest request, Result<AuthorizationCode> result) {
            Result<AuthorizationCode> result1 = getAuthCode(request, result);
            if (!result1.isSuccess()) {
                return result1;
            }
            String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
            if(!StringUtils.isEmpty(clientId) && clientId.equals(result1.getData().getClientId())){
                return result1;
            }
            return result1.setSuccess(false).setMsg("错误:获取code和token两次传入的clientId不一致");
        }
    }

    /**
     * 校验code换token两次传入的uri是否一致
     */
    @Component
    public class UriEqualValidator extends AbstractCodeValidator {
        @Override
        public Result<AuthorizationCode> validate(HttpServletRequest request, Result<AuthorizationCode> result) {
            Result<AuthorizationCode> result1 = getAuthCode(request, result);
            if (!result1.isSuccess()) {
                return result1;
            }
            String uri = request.getParameter(OAuth.OAUTH_REDIRECT_URI);
            if(!StringUtils.isEmpty(uri) && uri.equals(result1.getData().getRedirectUri())){
                return result1;
            }
            return result1.setSuccess(false).setMsg("错误:获取code和token两次传入的uri不一致");
        }
    }
}
