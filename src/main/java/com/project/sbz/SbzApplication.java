package com.project.sbz;

//import java.util.Arrays;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;



@SpringBootApplication
public class SbzApplication {
	
	//private static Logger log = LoggerFactory.getLogger(SbzApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SbzApplication.class, args);
		//ApplicationContext ctx = SpringApplication.run(SbzApplication.class, args);

		/*
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

        StringBuilder sb = new StringBuilder("Application beans:\n");
        for (String beanName : beanNames) {
            sb.append(beanName + "\n");
        }
        log.info(sb.toString());
        */
	}
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}
	
	@Bean
    public KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }
	/*
	 * KieServices ks = KieServices.Factory.get();
	 * KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("drools-spring-v2","drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));
	 * KieScanner kScanner = ks.newKieScanner(kContainer);
	 * kScanner.start(10_000);
	 * KieSession kSession = kContainer.newKieSession();
	 */
}
