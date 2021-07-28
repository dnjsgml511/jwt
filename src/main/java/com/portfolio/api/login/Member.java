package com.portfolio.api.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "webuser")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idx;

	private String id;
	private String email;
	private String password;
	private String usertype;

	public Member(String id, String email, String password, String usertype) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.usertype = usertype;
	}

	public static Member createMember(String id, String email, String password, String usertype) {
		return new Member(id, email, password, usertype);
	}

}
