package infoartex.artex.componentes;


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

import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class TiposInterrupcionesChart extends ComponenteGenerico{
	private VerticalLayout main;
	
	public TiposInterrupcionesChart(final Administrar view) {
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
	
	public void cargarSeries(ChartConfiguration area) {
		PieChartSeries series=new PieChartSeries("Interrupciones");
		PieChartData s1=new PieChartData("Sin procesar",fachada.cantInterrupcionesTipo("Sin procesar"));
		PieChartData s2=new PieChartData("Siendo procesada",fachada.cantInterrupcionesTipo("Siendo procesada"));
		PieChartData s3=new PieChartData("Solucionada",fachada.cantInterrupcionesTipo("Solucionada"));
		PieChartData s4=new PieChartData("No solucionada",fachada.cantInterrupcionesTipo("No solucionada"));
		series.addData(s1);
		series.addData(s2);
		series.addData(s3);
		series.addData(s4);
		area.getSeriesList().add(series);
	}

}
