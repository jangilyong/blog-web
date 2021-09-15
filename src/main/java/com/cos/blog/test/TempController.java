package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {
	
	//정적인 파일만 조회되 (application.yml 파일 mvc 설정되면 경로 오류 발생)
	@GetMapping("/temp/home")
	public String tempHome() {
		//파일리턴 기본경로 : src/main/resources/static
		//리턴명 : /home.html
		//폴더경로 : src/main/resources/static/home.html
		return "/home.html";
	}
	
	//정적인 파일만 조회되 (application.yml 파일 mvc 설정되면 경로 오류 발생)
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/a.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//application.yml 파일 mvc 설정
		//prefix : /WEB-INF/views/ -- 파일명 앞에 붙여줌
		//suffix : .jsp --파일명 뒤에 붙여줌
		//품네임 : /WEB-INF/views/temp.jsp
		return "temp";
	}
	

}
