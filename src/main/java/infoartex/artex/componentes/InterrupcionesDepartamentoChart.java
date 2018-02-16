package infoartex.artex.componentes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

import at.downdrown.vaadinaddons.highchartsapi.Colors;
import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import at.downdrown.vaadinaddons.highchartsapi.HighChartFactory;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.NoChartTypeException;
import at.downdrown.vaadinaddons.highchartsapi.model.Axis;
import at.downdrown.vaadinaddons.highchartsapi.model.Axis.AxisType;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.series.AreaChartSeries;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class InterrupcionesDepartamentoChart extends ComponenteGenerico {

	private VerticalLayout main;

	public InterrupcionesDepartamentoChart(Administrar view) {
		super(view);
		main = new VerticalLayout();
		ChartConfiguration area = new ChartConfiguration();
		// area.setTitle("Comportamiento de las interrupciones por departamentos");
		area.setChartType(ChartType.AREA);
		area.setBackgroundColor(Colors.WHITE);

		cargarSeries(area);

		try {
			HighChart areaChart = HighChartFactory.renderChart(area);
			areaChart.setHeight(80, Unit.PERCENTAGE);
			areaChart.setWidth(80, Unit.PERCENTAGE);
			main.addComponent(areaChart);
			main.setComponentAlignment(areaChart, Alignment.MIDDLE_CENTER);

		} catch (NoChartTypeException e) {
			e.printStackTrace();
		} catch (HighChartsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setCompositionRoot(main);
	}

	public void cargarSeries(ChartConfiguration area) {
		List<AreaChartSeries> series = new LinkedList<>();
		List<String> axisList = new LinkedList<>();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.set(Calendar.DAY_OF_MONTH, 1);
		gc.set(Calendar.MONTH, gc.get(Calendar.MONTH) + 1);
		int mesProximo = gc.get(Calendar.MONTH);
		gc.set(Calendar.MONTH, gc.get(Calendar.MONTH) - 1);
		int mesActual = gc.get(Calendar.MONTH);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		int i = 1;
		while (gc.get(Calendar.MONTH) != mesProximo) {
			axisList.add(format.format(gc.getTime()));
			gc.set(Calendar.DAY_OF_MONTH, ++i);
		}
		@SuppressWarnings("unchecked")
		List<Departamento> departamentos = (List<Departamento>) fachada
				.listarTodos(Departamento.class);
		for (Departamento dep : departamentos) {
			AreaChartSeries si = new AreaChartSeries(dep.getNombre());
			for (String fecha : axisList) {
				si.addData(dep.interrupcionesXDia(fecha));
			}
			series.add(si);
		}
		Axis axis = new Axis(AxisType.xAxis);
		axis.setTickLength(1);
		axis.setAllowDecimals(false);
		axis.setTitle("Mes: " + mesActual(mesActual));
		axis.setCategories(axisList);
		area.setxAxis(axis);
		for (AreaChartSeries s : series) {
			area.getSeriesList().add(s);
		}

	}

	public String mesActual(int mes) {
		return new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
				"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre" }[mes];
	}

}
