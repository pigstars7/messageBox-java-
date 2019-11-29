package com.xzy.servlet.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 封装对Servlet的操作，一个Servlet多用途
 * @author Administrator
 *
 */
public abstract class ServletBase extends HttpServlet {

	
	public abstract void index(Mapping map) throws ServletException, IOException;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		  req.setCharacterEncoding("utf-8");
		  Class[] params=new Class[] {HttpServletRequest.class,HttpServletResponse.class};
		   Object[] realparam=new Object[] {req,resp};
		   
		   //读一个请求参数 action
		   String action=null!=req.getParameter("action")?req.getParameter("action"):"";
		   if("".equals(action))action="index";
		   
		   try {
			Class clazz=this.getClass();
			   Method method=clazz.getDeclaredMethod(action, ServletBase.Mapping.class);
			 
			   if(null!=method)
			   {
		           Mapping map=new Mapping(req,resp);
		          
				 
				   method.invoke(this,map);
				   map=null;
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
	
	//内部类
	public class Mapping
	{
		private  HttpServletRequest req;
		private HttpServletResponse resp;
		public Mapping() {}
		public Mapping(HttpServletRequest req, HttpServletResponse resp)
		{
			this.req=req;
			this.resp=resp;
		}
		
		/*
		 * 
		 * 获取参数
		 */
		public String getString(String param) throws ServletException, IOException
		{
			String re=null!=req.getParameter(param)?req.getParameter(param):"";
			return re;
		}
		/**
		 * 
		 * @param param
		 * @return  可能返回null
		 * @throws ServletException
		 * @throws IOException
		 */
		public String[] getStringArray(String param) throws ServletException, IOException
		{
			return req.getParameterValues(param);
		}
		
		/**
		 * 直接获取数字
		 * @param param
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public int getInt(String param) throws ServletException, IOException
		{
			int re=-1;
			if(this.getString(param).matches("\\d+"))
			{
				re=Integer.parseInt(this.getString(param));
			}
			return re;
		}
		/**
		 * 直接获取数字
		 * @param param
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public long getLong(String param) throws ServletException, IOException
		{
			long re=-1;
			if(this.getString(param).matches("\\d+"))
			{
				re=Long.parseLong(this.getString(param));
			}
			return re;
		}
		
		/**
		 * 用请求参数的值，对应填 充java 对像
		 * @param bean
		 * @throws ServletException
		 * @throws IOException
		 */
		public void getBean(Object bean)throws ServletException, IOException
		{
			try {
				Class clazz=bean.getClass();
				Field []all=clazz.getDeclaredFields();
				if(null!=all&&all.length>0)
				{
					for(Field f:all)
					{
						f.setAccessible(true);
						String fname=f.getName();//成员名
						String readparam=this.getString(fname);
						if(readparam.length()>0)
						{
								if(f.getType()==Integer.class||f.getType()==int.class)
								{
									f.set(bean, this.getInt(fname));
								}else if(f.getType()==String.class)
								{
									f.set(bean, this.getString(fname));
								}else if(f.getType()==Long.class)
								{
									f.set(bean, this.getLong(fname));
								}else if(f.getType()==Date.class)
								{
									f.set(bean, new Date());
								}
						}
					}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		public void setAttr(String key,Object value)throws ServletException, IOException
		{
			req.setAttribute(key, value);
		}
		
		public void setSesstionAttr(String key,Object value)throws ServletException, IOException
		{
			req.getSession().setAttribute(key, value);
		}
		
		public void forward(String path)throws ServletException, IOException
		{
			RequestDispatcher rd=req.getRequestDispatcher("/WEB-INF/"+path);
			rd.forward(req, resp);
		}
		public void redirect(String path)throws ServletException, IOException
		{
			 resp.sendRedirect(path);
		}
		//输出信息
		public void rander(String msg)throws ServletException, IOException
		{
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.println("<h1>"+msg+"</h1>");
			out.flush();
		}
		public HttpServletRequest getReq() {
			return req;
		}
		public void setReq(HttpServletRequest req) {
			this.req = req;
		}
		public HttpServletResponse getResp() {
			return resp;
		}
		public void setResp(HttpServletResponse resp) {
			this.resp = resp;
		}
		
		
		
	}
	
	
	
	


}
