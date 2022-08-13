package cn.itcast.user.web;

import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;


    @Value("${pattern.dateformat}")
    private String dateformat;

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id, @RequestParam(value = "red", required = false) String red) {
        System.out.println(red);
        return userService.queryById(id);
    }

    @GetMapping("/now")
    public String now(@RequestHeader(required = false) String name) {
        System.out.println(name);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }
}
