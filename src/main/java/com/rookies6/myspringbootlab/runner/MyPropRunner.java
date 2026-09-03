package com.rookies6.myspringbootlab.runner;


import com.rookies6.myspringbootlab.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {

//    @Value("${myprop.username}")
//    private String username;
//
//    @Value("${myprop.port}")
//    private int port;

    private final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Autowired
    private MyPropProperties myPropProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception{
//        // 구분을 위해 콘솔에 시작 안내 문구 출력
//        System.out.println("=== 1-4 @Value 환경변수 Load 결과 ===");
//        // @Value로 외부 설정 파일에서 성공적으로 가져온 username 값 출력
//        System.out.println("myprop.username: " + username);
//        // @Value로 외부 설정 파일에서 성공적으로 가져온 port 값 출력
//        System.out.println("myprop.port: " + port);
        logger.info("myprop.username: " + myPropProperties.getUsername());
        logger.debug("myprop.port: " + myPropProperties.getPort());
    }
}
