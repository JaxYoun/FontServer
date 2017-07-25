/**
 * Project Name:FontServer
 * File Name:YourHandler.java
 * Package Name:com.learn
 * Date:2017年7月2日下午5:30:53
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.learn;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * ClassName:YourHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年7月2日 下午5:30:53 <br/>
 * @author   Yang Jx
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class YourHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		baseRequest.setHandled(true);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		target = "your";
		
		response.getWriter().write("<b>" + target + "</b>");
	}

}

