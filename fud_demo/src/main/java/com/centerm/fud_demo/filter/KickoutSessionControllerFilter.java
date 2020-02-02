package com.centerm.fud_demo.filter;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author jerry
 */
@Slf4j
public class KickoutSessionControllerFilter extends AccessControlFilter {
    private String kickoutUrl;
    private boolean kickoutAfter=false;
    private int maxSession=1;
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCache(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject  subject=getSubject(servletRequest,servletResponse);
        if (!subject.isAuthenticated()&&!subject.isRemembered())
        {
            return true;
        }
        Session session=subject.getSession();

        String username=(String) subject.getPrincipal();
        Serializable sessionId=session.getId();
        Deque<Serializable> deque=cache.get(username);
        if (deque==null)
        {
            deque=new LinkedList<Serializable>();
            cache.put(username,deque);
        }

        if (!deque.contains(sessionId)&&session.getAttribute("kickout")==null)
        {
            deque.push(sessionId);
        }

        while(deque.size()>maxSession)
        {
            Serializable kickoutSessionId=null;
            if (kickoutAfter)
            {
                kickoutSessionId=deque.getFirst();
                kickoutSessionId=deque.removeFirst();
            }else
            {
                kickoutSessionId=deque.removeLast();
            }
            try
            {
              Session kickoutSession=sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
              if (kickoutSession!=null)
              {
                  kickoutSession.setAttribute("kickout",true);
              }
            }catch (Exception e)
            {
                log.warn("用户剔除失败");
            }
        }
        if (session.getAttribute("kickout")!=null)
        {
            try{
                subject.logout();
            }catch (Exception e)
            {
            }
            WebUtils.issueRedirect(servletRequest,servletResponse,kickoutUrl);
            return false;
        }
       return true;
    }
}
