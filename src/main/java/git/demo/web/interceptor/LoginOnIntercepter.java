package git.demo.web.interceptor;

import git.demo.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginOnIntercepter implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("요청 URI {}", requestURI);
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(SessionConst.LOGIN_MEMBER)!=null) {
            log.info("로그인된 상태로 해당페이지 필요없음");

            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
