package com.ez.herb.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private Logger logger=LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle()호출 - 컨트롤러 수행전 호출되는 메서드!!");
		//클라이언트의 요청을 컨트롤러에 전달하기 전에 호출됨
		
		//로그인이 필요한 페이지인 경우 처리
		HttpSession session=request.getSession();
		String userid=(String) session.getAttribute("userid");
		
		if(userid==null || userid.isEmpty()) { //로그인 안된 경우
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.print("<script type='text/javascript'>");
			out.print("alert('먼저 로그인하세요!!');");
			out.print("location.href='"+request.getContextPath() +"/login/login.do';");
			out.print("</script>");
			
			return false; //요청 처리를 종료함
		}else { //로그인 된 경우
			return true; //다음 컨트롤러가 수행됨
			
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle()호출 - 컨트롤러 수행 후 호출되는 메서드");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("afterCompletion()호출");
		//클라이언트의 요청을 처리한 뒤, 
		//즉 뷰를 통해서 클라이언트에 응답을 전송한 뒤에 실행됨
	}

}
