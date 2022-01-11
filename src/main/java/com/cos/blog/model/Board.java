package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
@Entity //Board 클래스가 MySQL에 테이블이 생성이 된다.
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이됨 그래서 대용량 데이터 타입이여서 함.
	
	@ColumnDefault("0")
	private int count; //조회수
	
	/*
	 * JPA FetchType 설정 안내 
	 * 연관 관계에 있는 Entity 들 모두 가져온다 → Eager 전략
	 * 연관 관계에 있는 Entity 가져오지 않고, getter 로 접근할 때 가져온다 → Lazy 전략
	*/
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, User = One(기본 FetchType.EAGER 입)
	@JoinColumn(name="userId") //필드(컬럼) 값
	private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트르 저장할 수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY) // mappedBy(뒤에는 필드이름) 연관관계의 주인이 아니다.(FK가 아님) DB에 컬럼 생성되지 않음 (기본 FetchType.LAZY 임)
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createData;
	
		
}
