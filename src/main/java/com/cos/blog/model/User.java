package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//ORM -> java(모든언어) Object -> 테이블로 매핑해주는 기술
@Entity //User 클래스가 MySQL에 테이블이 생성이 된다.		
public class User {
	
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스, auto-increment
	
	@Column(nullable = false, length = 30)
	private String username;
	
	@Column(nullable = false, length = 100) //100까지 주는이유는(비밀번호 암호화로 등록된기 때문에)
	private String password;
	
	@ColumnDefault("'user'")
	private String role;  // Enum을 쓰는게 좋다(들어오는 값에 종류가 한정되고 다른값이 들어오면 안됨(it에서는 도메인이라고도함) 값: admin, user, manager)
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@CreationTimestamp //시간 자동 입력
	private Timestamp createDate;
	
	
}
