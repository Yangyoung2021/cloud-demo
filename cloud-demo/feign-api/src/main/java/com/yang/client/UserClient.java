package com.yang.client;


import com.yang.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userService")
public interface UserClient {

    /**
     * 与服务提供者的接口对应的Api
     * @param id 用户id
     * @return 请求获取的用户对象
     */
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Long id);

}
