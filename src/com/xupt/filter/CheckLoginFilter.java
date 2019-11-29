package com.xzy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xzy.pojo.Admin;
@WebFilter("/admin/*")
public class CheckLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		 HttpServletRequest req=(HttpServletRequest)arg0;
		 HttpServletResponse resp=(HttpServletResponse)arg1;
		 HttpSession session=req.getSession();
		 Admin admin=(Admin)session.getAttribute("loged");
		 if(null!=admin)
		 {
			 arg2.doFilter(req, resp);
		 }else
		 {
			 resp.sendRedirect("../login");
		 }

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
