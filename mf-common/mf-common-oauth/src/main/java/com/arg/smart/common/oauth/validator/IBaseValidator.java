package com.arg.smart.common.oauth.validator;

import com.arg.smart.common.core.web.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cgli
 * @date: 2020/2/13 13:43
 */
public interface IBaseValidator<T> {
    Result<T> validate(HttpServletRequest request, Result<T> result);
}
