package com.antgroup.antchain.xbuilders.base;

import com.alipay.api.AlipayClient;
import com.antgroup.antchain.xbuilders.XbuildersServerApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = XbuildersServerApplication.class)
@AutoConfigureMockMvc
public abstract class TestBase {

    @Autowired
    protected ApplicationContext applicationContext;

    @MockBean
    protected AlipayClient alipayClient;

    @MockBean
    protected RedissonClient redissonClient;


}
