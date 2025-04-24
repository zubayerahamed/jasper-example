package com.zayaanit.jasper.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Service
public class ReportService {

	@Autowired private DataSource dataSource;

	public byte[] generateReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException, IOException {

		File f = new File("C:\\Users\\METATUDE-07\\JaspersoftWorkspace\\MyReports\\demo.jasper");
		// Load compiled report
		InputStream reportStream = new FileInputStream(f);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
//		InputStream reportStream = resourceLoader.getResource("classpath:reports/" + reportName + ".jasper").getInputStream();

		try (Connection connection = DataSourceUtils.getConnection(dataSource);) {
			// Fill report with virtualization
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

			// Export to PDF
			return JasperExportManager.exportReportToPdf(jasperPrint);

		} catch (Exception e) {
			throw new RuntimeException("Error generating report", e);
		}
	}
}