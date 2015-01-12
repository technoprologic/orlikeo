package umk.zychu.inzynierka.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service("customSocialUserDetailsService")
public class CustomSocialUserDetailsService implements SocialUserDetailsService {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	public SocialUserDetails loadUserByUserId(String username)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return (SocialUserDetails) userDetails;
	}

}
