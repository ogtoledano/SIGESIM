/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package infoartex.artex.reportes;


import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;


/**
 *
 * @author administrador
 */

@SuppressWarnings("deprecation")
public class ControladorJasperReporter {
    private JasperPrint jasperPrint;

    public ControladorJasperReporter(String urlReporte) throws SQLException, JRException {

    }
    
    public void initJasperReport(){
    }


	public void generarReportePDF(String urlArchivoReporte) throws JRException{
		@SuppressWarnings("rawtypes")
		JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(urlArchivoReporte));
        exporter.exportReport();
    }
	
    public void generarReportePDF(String urlArchivoReporte, JasperPrint jp) throws JRException{
		@SuppressWarnings("rawtypes")
		JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jp); 
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(urlArchivoReporte));
        exporter.exportReport();
    }
	
    public void generarReporteDocx(String urlArchivoReporte) throws JRException{
		@SuppressWarnings("rawtypes")
		JRExporter exporter = new JRDocxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(urlArchivoReporte));
        exporter.exportReport();
    }

    public void generarReporteHTML(String urlArchivoReporte) throws JRException{
		@SuppressWarnings("rawtypes")
		JRExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(urlArchivoReporte));
        exporter.exportReport();
    }

    public void generarReporteXlsx(String urlArchivoReporte) throws JRException{
		@SuppressWarnings("rawtypes")
		JRExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint); 
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(urlArchivoReporte));
        exporter.exportReport();
    }
    
}
