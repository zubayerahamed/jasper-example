package com.zayaanit.jasper.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zayaanit.jasper.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	@Autowired private ReportService reportService;

	@GetMapping(value = "/large", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> generateLargeReport(HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "demo") String reportName) throws IOException, JRException, SQLException {

		Map<String, Object> parameters = new HashMap<>();

		byte[] reportBytes = reportService.generateReport(reportName, parameters);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reportName + ".pdf\"")
				.contentType(MediaType.APPLICATION_PDF).body(reportBytes);
	}
}