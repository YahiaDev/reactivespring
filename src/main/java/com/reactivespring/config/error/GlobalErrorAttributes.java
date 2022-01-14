package com.reactivespring.config.error;

import com.reactivespring.exception.ApiException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(request, options);

        if (getError(request) instanceof ApiException ex) {
            map.put("message", ex.getError().getMessage());
            map.put("status", ex.getError().getHttpStatus());
            map.remove("error");
            map.remove("requestId");
            //map.put("error", ex.getError().getHttpStatus().getReasonPhrase());
            return map;
        }
        // default
        map.put("status", HttpStatus.BAD_REQUEST);
        return map;
    }
}
