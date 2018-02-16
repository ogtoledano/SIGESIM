package infoartex.artex.reportes;


import infoartex.artex.dominio.PC;
import infoartex.artex.dominio.Software;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

public class ReporteSoftwareAutorizado {
	private JasperReport reporte;
    private JasperPrint jasperPrint;
    private String responsable;
    private String area;
    private String dirReporte;
    private String dirSubReporte;
    private PC pc;
    private List<Software> software;

    public ReporteSoftwareAutorizado(PC pc) throws JRException {
    	this.pc=pc;
        this.responsable = pc.getResponsable();
        this.area = pc.getDepartamento().getNombre();
        software=new LinkedList<>();
        cargarBienes();
        JasperCompileManager.compileReportToFile(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/software_autorizado.jrxml");
        JasperCompileManager.compileReportToFile(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/softwares.jrxml");
        this.dirReporte=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/software_autorizado.jasper";
        this.dirSubReporte=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/";
        estructurarReporte();
    }
    
    private void cargarBienes(){
    	/*List<MedioInformatico> lista=pc.getMedios();
        for(MedioInformatico m:lista){
        	bienes.add(new Bien(m.getInventario(), m.tipo+" "+m.marca+" "+m.getModelo()));
        }*/
    	software.add(new Software("1", "Windows 8 pro", "Rodrigo Gonzalez", "21-4-2017"));
    	software.add(new Software("2", "Microsoft Office", "Rodrigo Gonzalez", "21-4-2017"));
    	software.add(new Software("3", "Mozilla Firefox/Thunderbird", "Rodrigo Gonzalez", "21-4-2017"));
    	software.add(new Software("4", "Kaspersky Lab", "Rodrigo Gonzalez", "21-4-2017"));
    }
    
    private void estructurarReporte() throws JRException{
        this.reporte=(JasperReport) JRLoader.loadObject(new File(dirReporte));
        Map<String, Object> map = new HashMap<>();
        map.put("entidad", "Sucursal Holguín");
        map.put("area", area);
        map.put("puesto", pc.getNombre());
        map.put("listado", software);
        map.put("dirSubReporte", dirSubReporte);
        String dirImg=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/artexlogo.png";
        map.put("logo", dirImg);
        //para que no salga en blanco
        JRDataSource vacio = new JREmptyDataSource(1);
        jasperPrint = JasperFillManager.fillReport(reporte, map,vacio);
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

	public JasperReport getReporte() {
		return reporte;
	}

	public void setReporte(JasperReport reporte) {
		this.reporte = reporte;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDirReporte() {
		return dirReporte;
	}

	public void setDirReporte(String dirReporte) {
		this.dirReporte = dirReporte;
	}

	public String getDirSubReporte() {
		return dirSubReporte;
	}

	public void setDirSubReporte(String dirSubReporte) {
		this.dirSubReporte = dirSubReporte;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}
}
