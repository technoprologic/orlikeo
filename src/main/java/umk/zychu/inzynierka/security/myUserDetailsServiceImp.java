package umk.zychu.inzynierka.security;


import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;  
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.UserDAO;


@Service("myUserDetailsService")
public class myUserDetailsServiceImp implements UserDetailsService  {

	@Autowired
	private UserDAO userDAO;
		
	 @Transactional(readOnly = true)
	  public UserDetails loadUserByUsername(String email)
	      throws UsernameNotFoundException, DataAccessException {

			User user = userDAO.getUserByEmail(email);

			if (user == null) {
				throw new UsernameNotFoundException("Nie znaleziono uzytkownika o loginie: " + email);
			}

			Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
			roles.add(new SimpleGrantedAuthority(Roles.ROLE_USER.toString()));

			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			
			return new org.springframework.security.core.userdetails.User(
					user.getEmail(), 
					user.getPassword(), 
					enabled, 
					accountNonExpired, 
					credentialsNonExpired, 
					accountNonLocked, 
					roles
				);
			}

	}