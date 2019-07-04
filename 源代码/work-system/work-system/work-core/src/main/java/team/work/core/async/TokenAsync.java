package team.work.core.async;

import team.work.core.model.SysUserToken;
import team.work.core.service.impl.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import team.work.core.model.SysUserToken;


@Component
public class TokenAsync {
    @Autowired
    private SysUserTokenService userTokenService;

    @Async
    public void updateToken(SysUserToken ut){
        userTokenService.createUserToken(ut);
    }
}
