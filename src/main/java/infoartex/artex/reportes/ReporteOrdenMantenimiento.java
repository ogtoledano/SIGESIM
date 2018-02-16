package infoartex.artex.reportes;

import infoartex.artex.dominio.Mantenimiento;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.vaadin.server.VaadinService;

public class ReporteOrdenMantenimiento {
	private JasperReport reporte;
	private JasperPrint jasperPrint;
    private Mantenimiento mantenimiento;
	private String dirReporte;

    public ReporteOrdenMantenimiento(Mantenimiento mantenimiento) throws JRException {
    	this.mantenimiento=mantenimiento;
        JasperCompileManager.compileReportToFile(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/OrdenMantenimiento.jrxml");
        this.dirReporte=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/OrdenMantenimiento.jasper";
        estructurarReporte();
    }
    
    private void estructurarReporte() throws JRException{
        this.reporte=(JasperReport) JRLoader.loadObject(new File(dirReporte));
        Map<String, Object> map = new HashMap<>();
        map.put("entidad", "ARTEX SA");
        map.put("departamento", mantenimiento.getDepartamento().getNombre());
        map.put("consecutivo", ""+mantenimiento.getNo());
        map.put("nombretecnico", "");
        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        map.put("fecha",format.format(new Date()));
        map.put("inventario",mantenimiento.getInventario());
        map.put("tipomedio", mantenimiento.getEquipo());
        String observaciones=mantenimiento.getReporte()!=null?mantenimiento.getReporte().getObservacion():"Sin reporte de mantenimiento asociado";
        String dirImg=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/artexlogo.png";
        map.put("logo", dirImg);
        map.put("observaciones", observaciones);
        //para que no salga en blanco
        JRDataSource vacio = new JREmptyDataSource(1);
        jasperPrint = JasperFillManager.fillReport(reporte, map,vacio);
    }

	public JasperReport getReporte() {
		return reporte;
	}

	public void setReporte(JasperReport reporte) {
		this.reporte = reporte;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public Mantenimiento getMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(Mantenimiento mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	public String getDirReporte() {
		return dirReporte;
	}

	public void setDirReporte(String dirReporte) {
		this.dirReporte = dirReporte;
	}
    
    
}
