package com.xzy.servlet.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 封装对Servlet的操作，一个Servlet多用途
 * @author Administrator
 *
 */
public abstract class ServletBase_old extends HttpServlet {

	
	public abstract void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		  req.setCharacterEncoding("utf-8");
		   Class[] params=new Class[] {HttpServletRequest.class,HttpServletResponse.class};
		   Object[] realparam=new Object[] {req,resp};
		   
		   //读一个请求参数 action
		   String action=this.getString(req, "action");
		   if("".equals(action))action="index";
		   
		   try {
			Class clazz=this.getClass();
			   Method method=clazz.getDeclaredMethod(action, params);
			   if(null!=method)
			   {
				   method.invoke(this, realparam);
			   }
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}
	
	/*
	 * 
	 * 获取参数
	 */
	public String getString(HttpServletRequest req,String param) throws ServletException, IOException
	{
		String re=null!=req.getParameter(param)?req.getParameter(param):"";
		return re;
	}
	//输出信息
	public void rander(HttpServletResponse resp,String msg)throws ServletException, IOException
	{
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.println("<h1>"+msg+"</h1>");
		out.close();
	}


}
