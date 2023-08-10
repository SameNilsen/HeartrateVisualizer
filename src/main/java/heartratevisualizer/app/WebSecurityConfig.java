package heartratevisualizer.app;

// import org.springframework.configuration
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

// import com.createam.heroku.https.HttpsEnforcer;

import jakarta.servlet.Filter;

@Configuration
public class WebSecurityConfig{

  // @Override
  // protected void configure(HttpSecurity http) throws Exception {
  //   http.requiresChannel()
  //     .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
  //     .requiresSecure();
  // }
  @Bean
  public Filter httpsEnforcerFilter(){
      return new HttpsEnforcer();
  }
}