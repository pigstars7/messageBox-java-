package com.xzy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.xzy.db.Db;
import com.xzy.pojo.Admin;
import com.xzy.servlet.core.ServletBase;
import com.xzy.utils.Md5Encrypt;
@WebServlet("/regist")
public class RegistServlet extends ServletBase {

	@Override
	public void index(Mapping map) throws ServletException, IOException {
		// TODO Auto-generated method stub
		map.forward("page/regist.jsp");
	}
	
	public void regist(Mapping map) throws ServletException, IOException {
     
		Admin admin=new Admin();
		map.getBean(admin);
		admin.setUpur("0100000");
		
		
		String sql="insert into admin(email,upwd,upur,name,pic) values(?,?,?,?,?)";
		try {
			Db.update(sql, admin.getEmail(),Md5Encrypt.md5(admin.getUpwd()),admin.getUpur(),admin.getName(),admin.getPic());
			map.setAttr("msg", "注册成功!");
		} catch (SQLException e) {
			map.setAttr("msg", "注册失败!");
			e.printStackTrace();
		}
		
		map.redirect("login");
	}

}
