package team.work.core.async;

import team.work.core.service.impl.SysUserResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by htc on 2017/10/11
 * 公共异步
 */
@Component
public class CommonAsync {
    @Autowired
    private SysUserResourcesService userResourcesService;
//    @Autowired
//    private CacheConstant cacheConstant;
    @Async
    public void initUserResources(String systemId,String userId){
        userResourcesService.getUserResources(systemId,userId);
    }


}
