package com.cj;

import com.cj.datasource.DataSourceType;
import com.cj.datasource.DynamicDataSourceHolder;
import com.cj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class DataSourceController {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @Autowired
    UserService userService;
    /**
     * 修改数据源   使用切面修改，，可以控制切面的顺序。直接修改可能会被另一个切面覆盖
     * @param dsType
     */
    @PostMapping("/dsType")
    public  void setDsType(String dsType, HttpSession httpSession){
//        DynamicDataSourceHolder.setDataSourceName(dsType);
        httpSession.setAttribute(DataSourceType.DS_SESSION_KEY,dsType);
        logger.info("数据源切换为 {}",dsType);
    }

    @GetMapping("getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
