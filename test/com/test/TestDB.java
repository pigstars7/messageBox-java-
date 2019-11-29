package com.test;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.xzy.db.Db;
import com.xzy.pojo.Admin;
import com.xzy.utils.Md5Encrypt;

public class TestDB {

	@Test
	public void queryAdmin1()
	{
		String sql="select * from admin";
		
		try {
		/*	List<Admin> list=Db.query(sql, new BeanListHandler<Admin>(Admin.class));
		
		    for(Admin a:list)
		    {
		    	System.out.println(a.getId()+"\t"+a.getEmail()+"\t"+a.getUpwd());
		    }*/
			Admin a=Db.query(sql, new BeanHandler<Admin>(Admin.class));
			
		  
		    	System.out.println(a.getId()+"\t"+a.getEmail()+"\t"+a.getUpwd());
		   
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void queryAdmin2()
	{
		String sql="select * from admin";
		
		try {
			List<Admin> list=Db.query(sql, new BeanListHandler<Admin>(Admin.class));
		
		    for(Admin a:list)
		    {
		    	System.out.println(a.getId()+"\t"+a.getEmail()+"\t"+a.getUpwd());
		    }
		   
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void addAdmin()
	{
		String sql="insert into admin(email,upwd,upur) values(?,?,?)";
		try {
			Db.update(sql, "guest@qq.com",Md5Encrypt.md5("admin"),"0100");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
