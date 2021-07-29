package com.portfolio.api.config.jwt;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.api.entity.excel.Member;
import com.portfolio.api.login.Role;
import com.portfolio.api.login.repository.excel.MemberRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id));
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
		if (id.equals("admin")) {
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		}

		return new User(member.getId(), member.getPassword(), grantedAuthorities);
	}

	public Member authenticateByIdAndPassword(String id, String password) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new BadCredentialsException("Password not matched");
		}

		return member;
	}

}
