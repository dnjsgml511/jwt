package com.portfolio.api.entity.excel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "webuser")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	private String id;
	private String email;
	private String password;
	private String usertype;

	public Member(String id, String password, String usertype) {
		this.id = id;
		this.password = password;
		this.usertype = usertype;
	}

	public static Member createMember(String id, String password, String usertype) {
		return new Member(id, password, usertype);
	}

}
