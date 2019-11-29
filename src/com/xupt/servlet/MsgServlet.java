package com.xzy.servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.xzy.db.Db;
import com.xzy.db.PageDiv;
import com.xzy.pojo.Msg;
import com.xzy.servlet.core.ServletBase;
@WebServlet("/admin/msg")
public class MsgServlet extends ServletBase {

	@Override
	public void index(Mapping map) throws ServletException, IOException {
		// 查询所有留言
		
		/*List<Msg> allmsg=null;
		
		try {
			allmsg=Db.query("select a.name as adminName,m.* from msg m inner join admin a on  m.admin_id=a.id order by m.id desc", new BeanListHandler<Msg>(Msg.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.setAttr("allmsg", allmsg);*/
		
		int pageSize=5;
		int pageNo=1;
		if(map.getInt("pageNo")>1)pageNo=map.getInt("pageNo");
		
		PageDiv<Msg> pd=null;
		try {
			List<Msg>  list=Db.query("select a.name as adminName,m.* from msg m inner join admin a on  m.admin_id=a.id order by m.id desc limit ?,?", new BeanListHandler<Msg>(Msg.class),(pageNo-1)*pageSize,pageSize);
		    Object objtt=Db.query("select count(id) from msg", new ArrayHandler())[0];
		    int totalCount=0;
		    if(objtt instanceof BigInteger)
		    {
		    	totalCount=((BigInteger)objtt).intValue();
		    }else if(objtt instanceof Long)
		    {
		    	totalCount=((Long)objtt).intValue();
		    }
		    pd=new PageDiv<Msg>(pageNo, pageSize, totalCount, list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		map.setAttr("pd", pd);
		
		
		map.forward("page/main.jsp");

	}
	
	public void msg_add(Mapping map) throws ServletException, IOException {
		  
		    map.setSesstionAttr("token", UUID.randomUUID().toString());
		
		
              map.forward("page/msg_add.jsp");
	}
	
	
	public void msg_saveadd(Mapping map) throws ServletException, IOException {
		
		
		String stoken=(String)map.getReq().getSession().getAttribute("token");
		String token=map.getString("token");
		if(token.equals(stoken))
		{
			map.getReq().getSession().removeAttribute("token");
		  //存到数据库
		Msg msg=new Msg();
		map.getBean(msg);
		msg.setCtime(new Date());
		
		String sql="insert into msg(title,content,ctime,admin_id) values(?,?,?,?)";
		//msg.setContent(msg.getContent().replace(" ", "&nbsp;"));
		//msg.setContent(msg.getContent().replace("\r\n", "<br/>"));
		
		try {
			Db.update(sql,msg.getTitle(),msg.getContent(),msg.getCtime(),msg.getAdmin_id());
			map.setAttr("msg", "留言成功");
		} catch (SQLException e) {
			map.setAttr("msg", "留言失败");
			e.printStackTrace();
		}
		
		}else
		{
		   map.setAttr("err", "可能已经处理过了");	
		}
		index(map);
     
}
	
	
	public void showMsg(Mapping map) throws ServletException, IOException {
		int id=map.getInt("id");
		try {
			if(id>0)
			{
				String sql="select * from msg where id=?";
				Msg msg=Db.query(sql, new BeanHandler<Msg>(Msg.class),id);
				map.setAttr("msg", msg);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.forward("page/show.jsp");
	}

	public void edit(Mapping map) throws ServletException, IOException {
		int id=map.getInt("id");
		try {
			if(id>0)
			{
				String sql="select * from msg where id=?";
				Msg msg=Db.query(sql, new BeanHandler<Msg>(Msg.class),id);
				map.setAttr("msg", msg);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.forward("page/msg_edit.jsp");
	}
	
	
	
	public void msg_saveedit(Mapping map) throws ServletException, IOException {
		  //存到数据库
		Msg msg=new Msg();
		map.getBean(msg);
		msg.setCtime(new Date());
		
		String sql="update  msg set title=?,content=?,ctime=? where id=?";
		//msg.setContent(msg.getContent().replace(" ", "&nbsp;"));
		//msg.setContent(msg.getContent().replace("\r\n", "<br/>"));
		
		try {
			Db.update(sql,msg.getTitle(),msg.getContent(),msg.getCtime(),msg.getId());
			map.setAttr("msg", "修改留言成功");
		} catch (SQLException e) {
			map.setAttr("msg", "修改留言失败");
			e.printStackTrace();
		}
		index(map);
	}
	
	public void del(Mapping map) throws ServletException, IOException {
		int id=map.getInt("id");
		try {
			if(id>0)
			{
				String sql="delete from msg where id=?";
				Db.update(sql,id);
				map.setAttr("msg", "删除留言成功");
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 index(map);
	}
}
