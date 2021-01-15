package prueba1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UsuarioDetailsServiceImpl usuarioDetailsService;

    public SecurityConfig(UsuarioDetailsServiceImpl usuarioDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/","/public/**","/css/**","/js/**").permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login").defaultSuccessUrl("/private/index",true)
                    .failureUrl("/auth/login?error=true").usernameParameter("usua").passwordParameter("passw")
                        .loginProcessingUrl("/auth/login-post").permitAll()
                .and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
}
