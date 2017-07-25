package com.font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.typography.font.tools.sfnttool.SfntTool;


public class FontHandler extends AbstractHandler {
	
	private static long accessCount;
	private static final String OPTION = "-s";
	private static final String FILE_POSTFIX = ".ttf";
//	private static String sourceDir;
//	private static String destDir;
	private static String filePathSeperater;
	
	static {
//		destDir = PropertiesUtil.getPropMap().get("destDir");
		String osCode = PropertiesUtil.getPropMap().get("osType");
		if("unix".equalsIgnoreCase(osCode)){
			filePathSeperater = "/";
		}else if("windows".equalsIgnoreCase(osCode)){
			filePathSeperater = "\\";
		}
	}
	
	@Override
	public void handle(String arg0, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/json; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		
		PrintWriter printWriter = response.getWriter();
		
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line = null;
		while((line = reader.readLine()) != null){
			stringBuilder.append(line);
		}
		
		JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
		JSONArray jsonArr = jsonObject.getJSONArray("font_info");
		
		StringBuilder resultJsonList = new StringBuilder();
		if(jsonArr.size() <= 0){
			System.out.println(">>>>输入参数为空！");
		}else{
			System.out.println(">>>>收到参数：" + jsonArr.size() + "组！");
			List<String> pathList = new ArrayList<String>();
			for(Object it : jsonArr){
				JSONObject json = (JSONObject) it;
				String extract_str = json.getString("extract_str");
				String font_ori_path = json.getString("font_ori_path");
				String font_path = json.getString("font_path");
				
				if(StringUtil.isNotBlank(extract_str) && StringUtil.isNotBlank(font_path)){
					String[] paramArr = new String[4];
					paramArr[0] = OPTION;
					paramArr[1] = extract_str;
					paramArr[2] = font_ori_path;
					paramArr[3] = font_path;
					
					getWordsFromTTF(paramArr);
					pathList.add(paramArr[3]);
				}else{
					System.out.println(">>>>输入参数不完整！");
					continue;
				}
			}
			if(pathList.size() > 0){
				resultJsonList.append("{'status': 'SUCCESS', 'msg': '抽取成功！', 'subFontPath': "+ JSON.toJSONString(pathList) +"}");
			}else{
				resultJsonList.append("{'status': 'FAILED', 'msg': '抽取失败！', 'subFontPath': ''}");
			}
		}
		printWriter.write(resultJsonList.toString());
		accessCount++;
		System.out.println("第：" + accessCount + "次请求！");
	}
	
	public static void getWordsFromTTF(String[] stringArr) {
		try {
			SfntTool.main(stringArr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Deprecated
	public static String getFileFullPath(String prefix) {
		StringBuilder stringBuilder = new StringBuilder();
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		Date now = new Date();
		Random random = new Random();
		
		stringBuilder.append(prefix).append(filePathSeperater).append(dataFormat.format(now)).append(random.nextInt(10000)).append(FILE_POSTFIX);
		return stringBuilder.toString();
	}
	
}
