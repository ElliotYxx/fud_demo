package com.centerm.fud_demo.shiro.conf;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.centerm.fud_demo.exception.GlobalExceptionHandler;
import com.centerm.fud_demo.filter.KickoutSessionControllerFilter;
import com.centerm.fud_demo.listener.Listener;
import com.centerm.fud_demo.shiro.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String,String> filterMap=new LinkedHashMap<>();
        Map<String, Filter> filter=new LinkedHashMap<>();

        filter.put("kickout",kickoutSessionControllerFilter());
        shiroFilterFactoryBean.setFilters(filter);

        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/toLogin");
        filterMap.put("/config/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/superVIP/**","authc");
        filterMap.put("/admin/**","authc");
        filterMap.put("/user/toLogin/**","anon");
        filterMap.put("/user/toRegister/**","anon");
        filterMap.put("/user/login","anon");
        filterMap.put("/user/register","anon");
        filterMap.put("/user/**","user");
        filterMap.put("/user/logout","logout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }
    @Bean
    public KickoutSessionControllerFilter kickoutSessionControllerFilter()
    {
        KickoutSessionControllerFilter kickoutSessionControllerFilter=new KickoutSessionControllerFilter();
        kickoutSessionControllerFilter.setSessionManager(sessionManager());
        kickoutSessionControllerFilter.setCache(ehCacheManager());
        kickoutSessionControllerFilter.setKickoutAfter(false);
        kickoutSessionControllerFilter.setMaxSession(1);
        kickoutSessionControllerFilter.setKickoutUrl("/user/toLogin");
        return kickoutSessionControllerFilter;
    }
    @Bean
    public Listener sessionListener()
    {
        Listener listener=new Listener();
        return listener;
    }
    @Bean
    public SimpleCookie sessionIdCookie()
    {
        SimpleCookie simpleCookie=new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean()
    {
        MethodInvokingFactoryBean factoryBean=new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }
    @Bean
    public SessionManager sessionManager()
    {
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
        Collection<SessionListener> listeners=new ArrayList<SessionListener>();
        ((ArrayList<SessionListener>) listeners).add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(ehCacheManager());
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationInterval(3600000);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
    @Bean
    public SessionDAO sessionDAO()
    {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO=new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setCacheManager(ehCacheManager());
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }
    @Bean
    public SessionIdGenerator sessionIdGenerator()
    {
        return new JavaUuidSessionIdGenerator();
    }
    @Bean
    public SecurityManager securityManager()
    {
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm(ehCacheManager()));
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    @Bean
    public UserRealm myShiroRealm(EhCacheManager ehCacheManager)
    {
        UserRealm userRealm=new UserRealm();
        userRealm.setCachingEnabled(true);
        userRealm.setCacheManager(ehCacheManager);

        userRealm.setAuthorizationCacheName("authorizationCache");
        userRealm.setAuthorizationCachingEnabled(true);

        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthenticationCachingEnabled(true);

        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher()
    {
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(5);
        return hashedCredentialsMatcher;
    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
    {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor()
    {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
    @Bean
    public EhCacheManager ehCacheManager()
    {
        EhCacheManager em=new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return em;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator()
    {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public ShiroDialect shiroDialect()
    {
        return new ShiroDialect();
    }
}
