package com.smartprospect.smartprospect.pdfgenerator.controller;

import com.smartprospect.smartprospect.pdfgenerator.service.PdfGenerateService;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller @RequestMapping("file")
@RequiredArgsConstructor @Slf4j
public class CatalogGeneratorController {
    @Value("${pdf.directory}")
    private String folderPath;
    private final PdfGenerateService pdfGenerateService;
    private final UserService userService;

    @RequestMapping("download")
    @ResponseBody
    public void show(HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        String fileName = currentUser.getEmail()+"-catalog.pdf";
        Map<String, Object> data = new HashMap<>();
        data.put("products", currentUser.getProducts());
        pdfGenerateService.generatePdfFile("catalog", data, fileName);

        if (fileName.indexOf(".pdf")>-1) response.setContentType("application/pdf");

        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");

        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath+fileName);
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf))>0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
