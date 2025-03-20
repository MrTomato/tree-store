package ai.prewave.treeStore.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SpringSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { requests ->
                requests
                    // .requestMatchers(HttpMethod.GET, "/api/tree").permitAll()
                    // .requestMatchers(HttpMethod.POST, "/api/edges").authenticated()
                    // .requestMatchers(HttpMethod.DELETE, "/api/edges").authenticated()
                    // .anyRequest().authenticated()
                    .requestMatchers("/**").permitAll();
            }
            // .httpBasic { }

        return http.build()
    }
}