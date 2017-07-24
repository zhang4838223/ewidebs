package net.ewide.platform.modules.sys.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ewide.platform.common.config.Global;
import net.ewide.platform.common.utils.FileUtils;
import net.ewide.platform.common.utils.IdGen;
import net.ewide.platform.common.utils.StringUtils;
import net.ewide.platform.common.utils.UploadUtils;
import net.ewide.platform.common.web.BaseController;
import net.ewide.platform.modules.sys.entity.Attachment;
import net.ewide.platform.modules.sys.service.AttachmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.google.gson.Gson;


/**
 * 文件上传 Controller
 * @author TianChong
 * @version 2016年4月26日
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/fileUpload")
public class FileUploadController extends BaseController{
	@Autowired
	private AttachmentService attachmentService;
	
	@RequestMapping("testIndex")
	public String testIndex(){
		return "modules/test/testUpload";
	}
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"upload"})
	public @ResponseBody String upload(HttpServletRequest request, HttpServletResponse response, Model model){
		UploadUtils uploadUtils = new UploadUtils();
		String str[]=uploadUtils.uploadFile(request);
		if(str[1].equals("true")){
			String id=IdGen.uuid();
			Attachment attachment=new Attachment();
			attachment.setPath(str[4].toString());
			attachment.setfSize(request.getContentLength());
			attachment.setType(str[4].toString().substring(str[4].toString().indexOf(".")+1));
			attachment.setName(str[5].toString());
			attachment.setId(id);
			attachmentService.addAttachment(attachment);
			return id;
		}else{
			return "false";
		}
	}
	
	/**
	 * 获取文件属性
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"getFileInfo"})
	public @ResponseBody String getFileInfo(String id){
		String returnVal="false";
		if(!StringUtils.isBlank(id)){
			if(id.indexOf(",")>-1){
				List<Attachment> list = attachmentService.findListByIds(id.split(","));
				if(list!=null && list.size()>0){
					returnVal = new Gson().toJson(list);
				}
			}else{
				Attachment attachment = attachmentService.get(id);
				if(attachment!=null){
					List<Attachment> list= new ArrayList<Attachment>();
					list.add(attachment);
					returnVal = new Gson().toJson(list);
				}
			}
		}
		return returnVal;
	}
	
	
	/**
	 * 删除文件
	 * @return
	 */
	@RequestMapping(value = {"deleteFile"})
	public @ResponseBody String deleteFile(String id,HttpServletRequest request, HttpServletResponse response, Model model){
		Attachment attachment = attachmentService.get(id);
		if(attachment!=null){
			attachment.setDelFlag("1");
			attachmentService.delete(attachment);
			File file = new File(request.getSession().getServletContext().getRealPath("/")+attachment.getPath());
			if(file.exists()){
				file.delete();
			}
			return "true";
		}else{
			return "false";
		}
	}
	
	/**
	 * 文件下载
	 * @return
	 */
	@RequestMapping(value = {"downLoadFile"})
	public void downLoadFile(String id,HttpServletRequest request, HttpServletResponse response, Model model){
        String message="该文件不存在或已被删除";
        ServletOutputStream out = null;
		FileInputStream inputStream = null;
		try {  
    		Attachment attachment = attachmentService.get(id);
    		if(attachment==null){
    			renderString(response, message, "text/plain;charset=UTF-8");
    			return;
    		}
    		String path=request.getSession().getServletContext().getRealPath("/").replace("\\", "/");
            File file = new File(path + attachment.getPath());  
    		if(!file.exists()){
    			renderString(response, message, "text/plain;charset=UTF-8");
     			return;
            }
            response.setContentType("application/x-download");  
            response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(attachment.getName(), "UTF-8"));  
            out = response.getOutputStream();  
            inputStream = new FileInputStream(file); 
            
            int b = 0;  
            byte[] buffer = new byte[1024];  
            while (b != -1){
                b = inputStream.read(buffer);  
                out.write(buffer,0,b);  
            }
            inputStream.close();  
            out.close();
            out.flush();  
        } catch (IOException e) {  
            //e.printStackTrace();  
        	try {
				inputStream.close();
				out.close();
				out.flush();
			} catch (Exception e2) {
			}
            renderString(response, message, "text/plain;charset=UTF-8");
        } 
	}
	
	
	/**
	 * 文件预览
	 * 需启动openoffice监听 soffice -headless -accept="socket,port=8100;urp;"
	 */
	@RequestMapping(value = {"previewFile"})
	public void previewFile(String id,HttpServletRequest request, HttpServletResponse response){
		String message="该文件不存在或已被删除";
		ServletOutputStream out = null;
		FileInputStream inputStream = null;
		File fileHtml = null;
		try {
			Attachment attachment = attachmentService.get(id);
    		if(attachment==null){
    			renderString(response, message, "text/plain;charset=UTF-8");
    			return;
    		}
    		String path=request.getSession().getServletContext().getRealPath("/");
    		//需转换文件
    		File file = new File(path+attachment.getPath());
    		
    		if(Arrays.<String> asList("doc,docx,xls,xlsx,ppt".split(",")).contains(attachment.getType())){//office 文件  doc,docx,xls,xlsx,ppt
    			//转换成html目录
        		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + ".html";
        		File dirFile = new File(path+"convertHtml/");
        		if (!dirFile.exists()) {
    				dirFile.mkdirs();
    			}
        		OpenOfficeConnection con = new SocketOpenOfficeConnection(Global.getConfig("openoffice.host"),Integer.parseInt(Global.getConfig("openoffice.port"))); 
        		con.connect();
        		// 创建转换器  
        	    DocumentConverter converter = new OpenOfficeDocumentConverter(con);  
        	    // 转换文档html  
        	    fileHtml = new File(dirFile+"/"+newFileName);
        	    converter.convert(file, fileHtml);
        	    // 关闭openoffice连接  
        	    con.disconnect();  
        	    
                out = response.getOutputStream();  
                inputStream = new FileInputStream(fileHtml); 
                int b = 0;  
                byte[] buffer = new byte[1024];  
                while (b != -1){
                    b = inputStream.read(buffer);  
                    if(b!=-1)out.write(buffer,0,b);  
                }
                fileHtml.delete();
    		}else if(Arrays.<String> asList("gif,jpg,jpeg,png,bmp,html,htm".split(",")).contains(attachment.getType())){//images gif,jpg,jpeg,png,bmp,htm,html
    			 out = response.getOutputStream();
                 inputStream = new FileInputStream(file); 
                 int b = 0;  
                 byte[] buffer = new byte[1024];  
                 while (b != -1){
                     b = inputStream.read(buffer);
                     if(b!=-1)out.write(buffer,0,b);
                 }
    		}else if("txt".equals(attachment.getType())){//txt
    			renderString(response, FileUtils.readFileToString(file, "utf-8"), "text/plain;charset=UTF-8");
    		}else{
    			renderString(response, "该文件暂不支持预览！", "text/plain;charset=UTF-8");
    		}
		}catch (ConnectException e){
			renderString(response, "文件预览服务异常（未启动）", "text/plain;charset=UTF-8");
		}catch (Exception e) {
			renderString(response, "该文件暂不支持预览！", "text/plain;charset=UTF-8");
		}finally{
			try {
				if(inputStream!=null){
					inputStream.close();
				}
				if(out!=null){
					out.close();
					out.flush();
				}
				if(fileHtml!=null && fileHtml.exists()){
					fileHtml.delete();
				}
			} catch (Exception e2) {
			}
			
		}
	}
}
