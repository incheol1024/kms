package com.devworker.kms;

import com.devworker.kms.exception.NotStackException;
import com.devworker.kms.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.devworker.kms.util.CommonUtil.ANONYMOUS;

@SuppressWarnings("ALL")
@Component
public class RequestHandler implements Filter {
    @Autowired
    private EhCacheCacheManager cacheManager;
    private List<String> bypassUrl = new ArrayList();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            String url = ((HttpServletRequest) servletRequest).getRequestURI();
            if(url.equals("/")){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            for (String bypass : bypassUrl){
                if(url.contains(bypass)){
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }

            String user = CommonUtil.getCurrentUser();
            if (user.equals(ANONYMOUS)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            Cache cache = cacheManager.getCache("requestCache");
            String key = user + url;
            if (cache.get(key) != null) {
                throw new NotStackException("Too Many Request");
            }
            cache.put(key, 1);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        bypassUrl.add("ico");
        bypassUrl.add("component");
        bypassUrl.add("/login");
        bypassUrl.add("/error");
        bypassUrl.add("js");
    }

    @Override
    public void destroy() {
        Cache cache = cacheManager.getCache("requestCache");
        cache.clear();
    }
}
