/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author weizy
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
public class UploadServlet extends HttpServlet {
	// 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置-单位字节
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB


	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet UploadServlet</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet UploadServlet at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}
	@Override
	protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        // 1.判断是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须写有:enctype=multipart/form-data");
            writer.flush();
            return;
        }

        //2.开始配置上传参数-创建fileItem工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);

        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        //创建文件上传核心组件
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大上传文件的阈值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;


        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {

            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(request);
			
//			FileItemIterator fileItemIterator = upload.getItemIterator(request);
//			while(fileItemIterator.hasNext()){
//				System.out.println("UploadServlet:fileItemIterator:" + fileItemIterator.next());
//			}
			

            //遍历Items
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
				int i = 1;
                for (FileItem item : formItems) {
					System.out.println("UploadServlet.java:doPost:"+ i);
					i++;
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        //获取文件保存在服务器的路径
                        String filePath = uploadPath + File.separator + fileName;

                        //这个路径已经包含了图片名称，放到file对象中保存。
                        File storeFile = new File(filePath);

                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);

                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("message", "文件上传成功!" + "<br>存于："+filePath);
                    }else{
						System.out.println("UploadServlet.java:doPost:else:"+ item.getString("UTF-8"));
					}
                }
            }else{
				if(formItems==null){
					System.out.println("formItems==null");
				}else{
					System.out.println("formItems!=null:size:" + formItems.size());
				}
			}
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
        }
		response.sendRedirect("myStore.xhtml");
        // 跳转到 message.jsp
//        getServletContext().getRequestDispatcher("/index.xhtml").forward(request, response);
    }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//processRequest(request, response);
		String id = request.getParameter("id");
		System.out.println("UploadServlet.java:doGet:" + id);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
