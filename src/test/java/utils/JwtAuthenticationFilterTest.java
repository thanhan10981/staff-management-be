package utils;

import com.example.staffmanagementsystem.utils.CustomUserDetailsService;
import com.example.staffmanagementsystem.utils.JwtAuthenticationFilter;
import com.example.staffmanagementsystem.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {

    @Test
    void doFilter_validToken_setAuthentication() throws Exception {

        JwtTokenUtil jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        CustomUserDetailsService userDetailsService =
                Mockito.mock(CustomUserDetailsService.class);

        JwtAuthenticationFilter filter =
                new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        UserDetails userDetails = new User(
                "admin",
                "123",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        Mockito.when(jwtTokenUtil.getUsernameFromJwt("valid-token"))
                .thenReturn("admin");
        Mockito.when(userDetailsService.loadUserByUsername("admin"))
                .thenReturn(userDetails);
        Mockito.when(jwtTokenUtil.validateToken("valid-token", userDetails))
                .thenReturn(true);

        filter.doFilter(request, response, chain);

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(auth);
        assertEquals("admin", auth.getName());
    }
}
