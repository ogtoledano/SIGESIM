package infoartex.artex.componentes;

import java.util.List;

import infoartex.artex.dominio.Departamento;
import infoartex.artex.vistas.Administrar;
import at.downdrown.vaadinaddons.highchartsapi.Colors;
import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import at.downdrown.vaadinaddons.highchartsapi.HighChartFactory;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.NoChartTypeException;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.data.PieChartData;
import at.downdrown.vaadinaddons.highchartsapi.model.series.PieChartSeries;






import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MantenimientosDepartamentoChart extends ComponenteGenerico {
	
private VerticalLayout main;
	
	public MantenimientosDepartamentoChart(final Administrar view) {
		super(view);
		main=new VerticalLayout();
		ChartConfiguration pastel = new ChartConfiguration();
		pastel.setChartType(ChartType.PIE);
		pastel.setBackgroundColor(Colors.WHITE);
		cargarSeries(pastel);
		
		try {
			HighChart pastelChart = HighChartFactory.renderChart(pastel);
			pastelChart.setHeight(100, Unit.PERCENTAGE);
			pastelChart.setWidth(100, Unit.PERCENTAGE);
			main.addComponent(pastelChart);
			main.setComponentAlignment(pastelChart, Alignment.MIDDLE_CENTER);

		} catch (NoChartTypeException e) {
			e.printStackTrace();
		} catch (HighChartsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCompositionRoot(main);
		
	}
	
	@SuppressWarnings("unchecked")
	public void cargarSeries(ChartConfiguration area) {
		PieChartSeries series=new PieChartSeries("Mantenimientos");
		List<Departamento> despartamentos= (List<Departamento>) fachada.listarTodos(Departamento.class);
		for(Departamento dep:despartamentos){
			series.addData(new PieChartData(dep.getNombre(), dep.getMantenimientos().size()));
		}
		area.getSeriesList().add(series);
	}
}
