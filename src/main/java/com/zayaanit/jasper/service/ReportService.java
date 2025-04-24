package com.zayaanit.jasper.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReportService {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private ResourceLoader resourceLoader;

	public byte[] generateReportExample1(String reportName, Map<String, Object> parameters)
			throws JRException, SQLException, IOException {
		Resource resource = resourceLoader.getResource("classpath:reports/demo.jrxml");
		if (!resource.exists()) {
			throw new FileNotFoundException("Report file not found: " + reportName);
		}

		try (InputStream inputStream = resource.getInputStream();
				Connection connection = DataSourceUtils.getConnection(dataSource);) {

			JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

			// Export to PDF
			return JasperExportManager.exportReportToPdf(jasperPrint);

		} catch (Exception e) {
			throw new RuntimeException("Error generating report", e);
		}
	}
}