package com.example.ACboard;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@SpringBootApplication
public class ACboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ACboardApplication.class, args);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		// SqlSessionFactory는 직접 생성할 수 없으므로, SqlSessionFactoryBean을 사용하여 생성한다.
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		// DB의 커넥션 정보 설정 (DataSource 객체)
		sqlSessionFactoryBean.setDataSource(dataSource);

		// MyBatis 설정 파일의 위치 지정
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/config.xml"));

		// Mapper XML 파일들의 위치를 지정 (여러 개의 Mapper XML 파일을 한 번에 설정)
		// 예: UserMapper.xml, BoardMapper.xml 등의 파일을 로드한다.
		Resource[] resources= new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mappers/*.xml");
		sqlSessionFactoryBean.setMapperLocations(resources);

		// SqlSessionFactory 객체를 반환
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory){
		// SqlSession은 DB 작업을 실행하는 데 필요한 메서드를 제공하는 인터페이스이다.

		// SqlSessionTemplate은 SqlSession의 구현체로, 트랜잭션과 리소스 관리를 자동으로 처리해준다.
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
