package com.xzy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.xzy.db.Db;
import com.xzy.pojo.Admin;
import com.xzy.servlet.core.ServletBase;
import com.xzy.utils.Md5Encrypt;
@WebServlet("/login")
public class LoginServlet extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6543116681904724300L;

	@Override
	public void index(Mapping map) throws ServletException, IOException
	{
		   map.forward("page/login.jsp");

	}
	/**
	 * 验证用户名密码
	 * @param map
	 * @throws ServletException
	 * @throws IOException
	 */
	public void checkLogin(Mapping map) throws ServletException, IOException
	{
		String srand=(String)map.getReq().getSession().getAttribute("randomCode");
		String rand=map.getString("rand");
		if(rand.equals(srand))
		{
			String email=map.getString("email");
			String pwd=map.getString("upwd");
			
			String sql="select * from admin where email=? and upwd=?";
			try {
				Admin admin=Db.query(sql, new BeanHandler<Admin>(Admin.class),email,Md5Encrypt.md5(pwd));
		        if(null!=admin)
		        {
		           map.setSesstionAttr("loged", admin);
		           map.setAttr("msg", "登录成功！");
		           map.redirect("admin/msg");
		        }else
		        {
		        	map.setAttr("err", "用户名或密码不正确！");
					map.forward("page/login.jsp");
		        }
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}else
		{
			map.setAttr("err", "验证码不正确！");
			map.forward("page/login.jsp");
		}

	}

}
