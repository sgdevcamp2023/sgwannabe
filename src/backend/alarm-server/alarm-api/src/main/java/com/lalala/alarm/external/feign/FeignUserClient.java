package com.lalala.alarm.external.feign;

import com.lalala.alarm.external.feign.dto.UserInfoResponse;
import com.lalala.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVER")
public interface FeignUserClient {

    @GetMapping("/v1/api/users/{id}")
    BaseResponse<UserInfoResponse> getUserById(@PathVariable("id") Long id);

}
