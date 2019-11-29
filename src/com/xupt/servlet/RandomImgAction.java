package com.xzy.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;

import com.xzy.servlet.core.ServletBase;


@WebServlet("/randimg")
public class RandomImgAction extends ServletBase {
	//验证码图片的宽度。
    private int width=80;
    //验证码图片的高度。
    private int height=30;
  //创建一个随机数生成器类。
    Random random=new Random();
    
    
    
	@Override
	public void index(Mapping map) throws ServletException, IOException {
	
		
		BufferedImage buffImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=buffImg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,width,height);
		
		//创建字体，字体的大小应该根据图片的高度来定。
		Font font=new Font("Times New Roman",Font.PLAIN,22);
		//设置字体。
		g.setFont(font);
		
		//画边框。
		g.setColor(Color.BLACK);
		g.drawRect(0,0,width-1,height-1);
		
		//随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(new Color(160,160,160));
		for (int i=0;i<320;i++)
		{
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x,y,x+xl,y+yl);
		}
		
		
		//randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode=new StringBuffer();
		int red=0,green=0,blue=0;
		
		//随机产生4位数字的验证码。
		for (int i=0;i<4;i++)
		{
		//得到随机产生的验证码数字。
		String strRand=String.valueOf(random.nextInt(10));
		
		//产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
		red=random.nextInt(255);
		green=random.nextInt(10);
		blue=random.nextInt(10);
		
		//用随机产生的颜色将验证码绘制到图像中。
		g.setColor(new Color(red,green,blue));
		g.drawString(strRand,13*i+12,23);
		
		//将产生的四个随机数组合在一起。
		randomCode.append(strRand);
		}
		//将四位数字的验证码保存到Session中。

//Map session=ActionContext.getContext().getSession();

map.setSesstionAttr("randomCode",randomCode.toString());
//禁止图像缓存。
map.getResp().setHeader("Pragma","no-cache");
map.getResp().setHeader("Cache-Control","no-cache");
map.getResp().setDateHeader("Expires", 0);

map.getResp().setContentType("image/jpeg");

//将图像输出到Servlet输出流中。
ServletOutputStream sos=map.getResp().getOutputStream();
ImageIO.write(buffImg, "jpeg",sos);
sos.close();

	}


}
