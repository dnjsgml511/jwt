package com.portfolio.api.login;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.api.config.jwt.JwtTokenUtil;
import com.portfolio.api.config.jwt.JwtUserDetailsService;
import com.portfolio.api.entity.excel.Member;
import com.portfolio.api.login.repository.excel.MemberRepository;
import com.portfolio.api.test.TestSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class LoginController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailService;

	final MemberRepository memberRepository;
	final PasswordEncoder encode;

	// 로그인
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println("createAuthenticationToken");
		final Member member = userDetailService.authenticateByIdAndPassword(authenticationRequest.getId(),
				authenticationRequest.getPassword());
		final String token = jwtTokenUtil.generateToken(member.getId());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	// 회원가입
	@PostMapping("/api/member")
	public String saveMember(@RequestBody MemberDto memberDto) throws Exception {
		System.out.println("saveMember");
		Optional<Member> id = memberRepository.findById(memberDto.getId());

		try {
			id.get();
			return "duplicate";
		} catch (Exception e) {
			memberRepository.save(Member.createMember(memberDto.getId(), encode.encode(memberDto.getPassword()),
					memberDto.getUsertype()));
			return "success";
		}
	}

	@Autowired
	private TestSupport test;

	@GetMapping("/hello")
	public String firstPage() throws Exception {
		List<Member> wow = test.test();
		for (Member member : wow) {
			System.out.println(member);
		}
		return "Hello World";
	}
}

@Data
class JwtRequest {
	private String id;
	private String email;
	private String password;
}

@Data
@AllArgsConstructor
class JwtResponse {
	private String token;
}

@Data
class MemberDto {
	private String id;
	private String email;
	private String password;
	private String usertype;
}
