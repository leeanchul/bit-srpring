package com.ac.Cmovie;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


@SpringBootApplication
public class CmovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmovieApplication.class, args);
	}
	@Bean // Spring 한테 특정 클래스 객체의 생성/ 소멸을 맡긴다.
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource); // DB 연결에 필요한 정보를 로딩
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/config.xml")); // mybatis 설정 파일 로딩
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mappers/*.xml");
		sqlSessionFactoryBean.setMapperLocations(resources);

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
