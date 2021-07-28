package com.portfolio.api.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.api.config.JwtTokenUtil;
import com.portfolio.api.config.JwtUserDetailsService;

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

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println("createAuthenticationToken");
		final Member member = userDetailService.authenticateByEmailAndPassword(authenticationRequest.getEmail(),
				authenticationRequest.getPassword());
		final String token = jwtTokenUtil.generateToken(member.getEmail());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/api/member")
	public String saveMember(@RequestBody MemberDto memberDto) {
		System.out.println("saveMember");
		memberRepository.save(Member.createMember(memberDto.getId(), memberDto.getEmail(), encode.encode(memberDto.getPassword()), memberDto.getUsertype()));
		return "success";
	}

	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}
}

@Data
class JwtRequest {
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
