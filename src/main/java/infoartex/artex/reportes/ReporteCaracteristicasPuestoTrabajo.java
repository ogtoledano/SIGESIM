package infoartex.artex.reportes;
import infoartex.artex.dominio.Laptop;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.PC;
import infoartex.artex.dominio.Periferico;
import infoartex.artex.dominio.Torre;

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

public class ReporteCaracteristicasPuestoTrabajo {
	private JasperReport reporte;
    private JasperPrint jasperPrint;
    private String responsable;
    private String dirReporte;
    private String dirSubReporte;
    private PC pc;
    private List<Periferico> perifericos;

    public ReporteCaracteristicasPuestoTrabajo(PC pc) throws JRException {
    	this.pc=pc;
        this.responsable = pc.getResponsable();
        perifericos=new LinkedList<>();
        JasperCompileManager.compileReportToFile(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/caracteristicas_puesto_trabajo.jrxml");
        JasperCompileManager.compileReportToFile(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/perifericos.jrxml");
        this.dirReporte=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/caracteristicas_puesto_trabajo.jasper";
        this.dirSubReporte=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/";
        estructurarReporte();
    }
    
    private void cargarPerifericos(Map<String, Object> map){
    	List<MedioInformatico> lista=pc.getMedios();
    	map.put("invTeclado", " ");
    	map.put("invMouse", " ");
    	map.put("invBocinas", " ");
    	map.put("invModem", " ");
        for(MedioInformatico m:lista){
        	if(!(m instanceof Torre)){
        		switch (m.getTipo()) {
					case "Teclado":{
					//	map.replace("invTeclado", m.getInventario());
						break;
					}
					case "Mouse":{
					//	map.replace("invMouse", m.getInventario());
						break;
					}
					case "Bocinas":{
					//	map.replace("invBocinas", m.getInventario());
						break;
					}
					case "Modem":{
					//	map.replace("invModem", m.getInventario());
						break;
					}
        		}
        		perifericos.add(new Periferico(m.getTipo(), m.getModelo(),"No definido", m.getInventario()));
        	}
        }
    }
    
    private void estructurarReporte() throws JRException{
        this.reporte=(JasperReport) JRLoader.loadObject(new File(dirReporte));
        Torre torre=obtenerTorre(pc.getMedios());
        Map<String, Object> map = new HashMap<>();
        cargarPerifericos(map);
        map.put("computadora", pc.getNombre());
        map.put("area", pc.getDepartamento().getNombre());
        map.put("responsable", pc.getResponsable());
        String dirImg=VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF/classes/infoartex/artex/reportes/artexlogo.png";
        map.put("logo", dirImg);
        if(torre!=null){
        	map.put("noInventario",torre.getInventario());
        	map.put("serie", " ");
        	map.put("modeloBoard", torre.getBoard());
        	map.put("modeloCPU",torre.getProcesador());
        	map.put("tiporam", torre.getTiporam());
        	map.put("capacidadRAM", torre.getRam());
            map.put("capacidadHDD", torre.getHdd());
            map.put("modeloHDD", " ");
        }else{
        	map.put("noInventario",((Laptop)pc).getInventario());
        	map.put("serie", ((Laptop)pc).getSerie());
        	map.put("modeloBoard", ((Laptop)pc).getModeloplaca());
        	map.put("modeloCPU", ((Laptop)pc).getProcesador());
        	map.put("tiporam", ((Laptop)pc).getTiporam());
        	map.put("capacidadRAM", ((Laptop)pc).getCapram());
            map.put("capacidadHDD", ((Laptop)pc).getCaphdd());
            map.put("modeloHDD", ((Laptop)pc).getMarcahdd());
        }
        map.put("velocidadCPU", " ");
        map.put("noSerieHdd", " ");
        
        map.put("listado", perifericos);
        map.put("dirSubReporte", dirSubReporte);
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
	
	public Torre obtenerTorre(List<MedioInformatico> lista){
		Torre salida=null;
		boolean enc=false;
		int i=0;
		while(i<lista.size()&&!enc){
			if(lista.get(i) instanceof Torre){
				enc=true;
				salida=(Torre) lista.get(i);
			}else{
				i++;
			}
		}
		
		return salida;

	}
}
