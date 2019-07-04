package team.work.core.async;

import team.work.core.service.impl.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserAsync {
    @Autowired
    private SysUserService userService;



}
