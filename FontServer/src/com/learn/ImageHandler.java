/**
 * Project Name:FontServer
 * File Name:ImageHandler.java
 * Package Name:com
 * Date:2017年7月2日上午10:29:20
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.learn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.font.PropertiesUtil;

/**
 * ClassName:ImageHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年7月2日 上午10:29:20 <br/>
 * 
 * @author Yang Jx
 * @version
 * @since JDK 1.8
 * @see
 */
public class ImageHandler extends AbstractHandler {

	private static long accessCount;
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("image/*"); // 设置返回的文件类型
		baseRequest.setHandled(true);
		response.setStatus(HttpServletResponse.SC_OK);
		
		String imgFile = request.getParameter("imgFile"); // 图片文件名
		String path = PropertiesUtil.getPropMap().get("imageDocDir"); // 这里是存放图片的文件夹地址，【建议放在项目配置文件中】
		
//		imgFile = "troy.jpg";
		FileInputStream fileIs = null;
		try {
			fileIs = new FileInputStream(path + "/" + imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int i = fileIs.available(); // 得到文件大小
		byte data[] = new byte[i];
		fileIs.read(data); // 读数据

		OutputStream outStream = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
		outStream.write(data); // 输出数据
		outStream.flush();
		outStream.close();
		fileIs.close();
		
		accessCount++;
		System.out.println("第：" + accessCount + "次请求！");
		
	}

}
