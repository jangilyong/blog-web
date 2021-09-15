package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일) @Controller

//사용자가 요청-> 응답(Data) @RestController

@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest :";
	
	//http://localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("kaitaro").password("11111").email("kai@nate.com").build();
		System.out.println(TAG+"getter : "+m.getUsername());
		m.setUsername("kor");
		System.out.println(TAG+"setter : "+m.getUsername());
		return "lombokTest 완료";
	}
	
	//http://localhost:8000/http/get(select)
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id, @RequestParam String username) {
		return "get 요청 "+id+","+username;		
	}
	
	//http://localhost:8000/http/getMember(select)
	@GetMapping("/http/getMember")
	public String getParmaAllTest(Member m) { //MessageConverter (스프링부트) 자동 맵핑시켜줌
		return "get 요청 "+m.getId()+","+m.getUsername()+","+m.getEmail();		
	}

	
	//http://localhost:8000/http/post(insert)
	@PostMapping("/http/post")	
	public String postTest(@RequestBody Member m) { //MessageConverter (스프링부트) 자동 맵핑시켜줌
		return "post 요청 "+m.getId()+","+m.getUsername()+","+m.getEmail();	
	}
	
	//http://localhost:8000/http/postBody(insert)
	@PostMapping("/http/postBody")	
	public String postBodyTest(@RequestBody String text) {
		return "post 요청"+text;	
	}
	

	//http://localhost:8000/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) { //body 데이터를 넘길때 @RequestBody라는 어노테이션으로 받을수 있음 
		return "put 요청 "+m.getId()+","+m.getUsername()+","+m.getEmail();	
	}

	//http://localhost:8000/http/delete(delate)
	@DeleteMapping("/http/delete")
	public String deleteTest(@RequestBody Member m) {
		return "delete 요청";
	}

}
