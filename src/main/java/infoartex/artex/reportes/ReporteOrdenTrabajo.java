package infoartex.artex.reportes;

import infoartex.artex.dominio.OrdenTrabajo;

import java.io.File;
import java.text.SimpleDateFormat;
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


public class ReporteOrdenTrabajo {
	private JasperReport reporte;
	private JasperPrint jasperPrint;
    private OrdenTrabajo orden;
	private String dirReporte;

    public ReporteOrdenTrabajo(OrdenTrabajo orden) throws JRException {
    	this.orden=orden;
        JasperCompileManager.compileReportToFile(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/OrdenTrabajo.jrxml");
        this.dirReporte=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/OrdenTrabajo.jasper";
        estructurarReporte();
    }
    
    private void estructurarReporte() throws JRException{
        this.reporte=(JasperReport) JRLoader.loadObject(new File(dirReporte));
        Map<String, Object> map = new HashMap<>();
        map.put("entidad", "ARTEX SA");
        map.put("consecutivo", ""+orden.getNumero());
        map.put("nombretecnico", "   ");
        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        map.put("fecha",format.format( orden.getFecha())+"");
        map.put("inventario", orden.getInventario());
        map.put("tipomedio", orden.getTipomedio());
        map.put("dictamen", orden.getDictamen());
        map.put("acciones", orden.getAcciones());
        map.put("fechasalida", format.format(orden.getFechasalida()));
        map.put("trabajador", orden.getTrabajadorentregado());
        String dirImg=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/artexlogo.png";
        map.put("logo", dirImg);
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

	public OrdenTrabajo getOrden() {
		return orden;
	}

	public void setOrden(OrdenTrabajo orden) {
		this.orden = orden;
	}

	public String getDirReporte() {
		return dirReporte;
	}

	public void setDirReporte(String dirReporte) {
		this.dirReporte = dirReporte;
	}
    
    
}
