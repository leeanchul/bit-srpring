package com.bit.restful_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulApiApplication {
	// restFul Api Server
	// restFul api 서버는 우리가 프론트앤드와 백엔드를 분리했을 때
	// 백앤드를 담당하는 서버가 된다.

	// 기존 board 프로젝트나 secuirty 프로젝트는 해당 스프링부트 프로젝트가
	// 백엔드와 프론트엔드를 모두다 담당하지만
	// restful api 서버가 도입되면
	// 해당 서버는 백엔드만 담당하고
	// 프론트 엔드를 리액트나 다른 뷰 프레임워크 혹은 다른 스프링부트 프레임워크로 분리하여 관리하는 것이 가능하다.

	// 하지만 이렇게 분리할 때에는 프론트 쪽에서는 자바스크립트 뷰 프레임워크를 사용하고
	// 백엔드 쪽에서는 요청에 대한 응답을 JSON 으로 보내주는 방식으로 각 엔드간의 데이터 이동이 이루어진다.

	public static void main(String[] args) {
		SpringApplication.run(RestfulApiApplication.class, args);
	}

}
