package projectManagementSystem.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projectManagementSystem.service.AuthenticationService;
import projectManagementSystem.service.UserRoleService;

@Configuration
public class AppConfig {
    public static final Logger logger = LogManager.getLogger(AppConfig.class);
    private final AuthenticationService authService;
    private final UserRoleService userRoleService;

    @Autowired
    public AppConfig(AuthenticationService authService, UserRoleService userRoleService) {
        logger.info("AppConfig is created");

        this.authService = authService;
        this.userRoleService = userRoleService;
    }

    /**
     * this method is used to register the cors filter
     *
     * @return FilterRegistrationBean<TokenFilter>
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBean() {
        logger.info("CorsFilterBean has been created");
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        CorsFilter corsFilter = new CorsFilter();
        registrationBean.setFilter(corsFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); //set filter precedence
        return registrationBean;
    }

    /**
     * his method is used to register the token filter
     * the token filter initialized with the auth service
     * the token filter is running first in the filter chain
     *
     * @return FilterRegistrationBean<TokenFilter>
     */
    @Bean
    public FilterRegistrationBean<TokenFilter> filterRegistrationBean() {
        logger.info("FilterRegistrationBean has been created");
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        TokenFilter customURLFilter = new TokenFilter(authService);
        registrationBean.setFilter(customURLFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<PermissionFilter> filterPermissionBean() {
        logger.info("filterPermissionBean has been created");
        FilterRegistrationBean<PermissionFilter> registrationBean = new FilterRegistrationBean<>();
        PermissionFilter permissionFilter = new PermissionFilter(userRoleService);
        registrationBean.setFilter(permissionFilter);
        registrationBean.setOrder(3);
        return registrationBean;
    }
}
