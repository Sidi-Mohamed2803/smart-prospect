package com.smartprospect.smartprospect.pdfgenerator.service;

import java.util.Map;

public interface PdfGenerateService {
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);
}
