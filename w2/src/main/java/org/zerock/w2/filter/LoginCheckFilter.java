package org.zerock.w2.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.zerock.w2.dto.MemberDTO;
import org.zerock.w2.service.MemberService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("Login check filter....");

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        HttpSession session = req.getSession();

        if(session.getAttribute("loginInfo") == null){

            //쿠키를 체크
            Cookie cookie = findCookie(req.getCookies(), "remember-me");

            if(cookie != null){

                log.info("cookie는 존재하는 상황");
                String uuid  = cookie.getValue();

                try {
                    MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

                    log.info("쿠키의 값으로 조회한 사용자 정보: " + memberDTO );

                    session.setAttribute("loginInfo", memberDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                chain.doFilter(request, response);
                return;
            }

            resp.sendRedirect("/login");

            return;
        }

        chain.doFilter(request, response);
    }

    private Cookie findCookie(Cookie[] cookies, String name){

        if(cookies == null || cookies.length == 0){
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies).filter(ck -> ck.getName().equals(name)).findFirst();

        return result.isPresent()?result.get():null;
    }


}
