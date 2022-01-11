package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// html 파일이 아니라 데이터를 리턴해주는 컨트롤러
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		} catch (Exception e) {
			return "시스템 오류가 발생했습니다.";
		}
		return null;
	}
	
	
	
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,  @RequestBody User requestUser) {//json 데이터 요청=> Java Object(MessageConverter의 Jackson라이브러리가 변환처리)
		System.out.println("id = "+id);
		System.out.println("password = "+requestUser.getPassword());
		System.out.println("emial = "+requestUser.getEmail());
		
		
		//더티 체킹(1, 2)
		//영속화
		User user = userRepository.findById(id).orElseThrow(()->{ return new
		IllegalArgumentException("수정에 실패했습니다."); });
		//데이터 수정
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
				
		//save 함수는 id(키)를 전달하지 않으면 insert를 해주고
		//save 함수는 id(키)를 전달하면 해당 id 대한 데이터가 있으면 update를 해주고
		//save 함수는 id(키)를 전달하면 해당 id 대한 데이터가 없으면 insert를 함
		userRepository.save(user); 
					
		return user;
	}		
	
	//전체테이블 조회
	@GetMapping("/dummy/user")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건에 데이터를 리턴
	@GetMapping("/dummy/user/users")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		//실제내용 및 패이징 관련 정보도 같이 가져오기
		Page<User> pageingUser = userRepository.findAll(pageable);
		
		//실제 내용만 리턴
		//List<User> users = userRepository.findAll(pageable).getContent();
		
		//내용만 가져오게 수정
		List<User> users = pageingUser.getContent();
		
		return users;
	}
	
	/*@PostMapping("/dummy/join")
	public String join(String username, String password, String email) { //key=value(form전송시 약속된 큐직)
		System.out.println("username : "+ username);
		System.out.println("password : "+ password);
		System.out.println("email : "+ email);
		return "회원가입이 완료되었습니다.";
	}*/

	@PostMapping("/dummy/join")
	public String join(User user) { //key=value(Object로도 받을수 있음)
		System.out.println("id : "+ user.getId());
		System.out.println("username : "+ user.getUsername());
		System.out.println("password : "+ user.getPassword());
		System.out.println("email : "+ user.getEmail());
		System.out.println("role : "+ user.getRole());
		System.out.println("createData : "+ user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
				
		return "회원가입이 완료되었습니다.";
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/1
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		//user/1을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
		//그럼 return null 이 리턴이 되자나..그럼 프로그램에 문제가 있지 않겠니?
		//Optional로 너의 User객체를 감싸서 가져올테니 null 인지 아닌지 판단해서 return해
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		
		});
		
		//람다식
		/*
		 * User user = userRepository.findById(id).orElseThrow(()->{ return new
		 * IllegalArgumentException("해당 유저는 없습니다. id : "+id); });
		 */

		//검색이 안될시 오류는 발생하지 않고 null 값을 리턴함
		/*User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
			@Override
			public User get() {
				return new User();
			}
		
		});*/

		//요청 : 웹브라우저
		//user 객채 = 자바 오브젝트
		//변환 (웹브라우저가 이해할수 있는 데이터) -> json(예전에는 Gson.jar 라이브러리를 사용해서 변환)
		//스프링부트 = MessageConverter 라는 애가 응답시에 자동 작동
		//만약에 자바오브젝트를 리턴하게 되면 MessageConverter 가 Jackson 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던저줍니다.
		return user;
	}

	
}
