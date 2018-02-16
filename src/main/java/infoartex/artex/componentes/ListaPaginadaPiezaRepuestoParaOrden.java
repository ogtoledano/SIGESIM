package infoartex.artex.componentes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import infoartex.artex.bundles.Paginador;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.PiezaRepuesto;

@SuppressWarnings("serial")
public class ListaPaginadaPiezaRepuestoParaOrden extends ListaPaginada<PiezaRepuesto>{

	private List<PiezaRepuesto> piezas;
	
	public ListaPaginadaPiezaRepuestoParaOrden(CustomComponent view, int size,
			int tuplas, Class<?> clazz) {
		super(view, size, tuplas, clazz);
		// TODO Auto-generated constructor stub
		filtros.put("noOrden", "Disponible");
		addColumnas("Código","Factura","Tipo","Cantidad","No. Orden");
		addCampos("codigo");
        addCampos("factura");
        addCampos("tipo");
        addCampos("cantidad");
        addCampos("noOrden");
        piezas=new LinkedList<>();
		paginador=new Paginador<>(Integer.parseInt(""+cantidadXPaginas.getValue()),tuplas,"" ,campos,filtros, clazz);
	}
	
	public void cargarDatos(final CustomComponent view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (paginador.devolverElementosActuales().size() == 0) {
			paginador.pagAnterior();
		}
		
		List<PiezaRepuesto> aux=paginador.devolverElementosActuales();
		Field[] fields=clazz.getDeclaredFields();
		tabla.removeAllItems();
		for(final PiezaRepuesto objeto:aux){
			Object [] obj=new Object[campos.size()+1];
			int i=0;
			for(Field f:fields){
				if(campos.indexOf(f.getName())!=-1){
					if(f.getType()==java.util.Date.class){
						SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
						obj[i++]=format.format(f.get(objeto))+"";
					}else{
					obj[i++]=f.get(objeto)+"";
					}
				}
			}
			HorizontalLayout opciones=new HorizontalLayout();
			opciones.setWidthUndefined();
			final CheckBox elegir=new CheckBox();
			elegir.setValue(piezas.contains(objeto));
			elegir.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					if(elegir.getValue()){
						piezas.add(objeto);
						if(view instanceof GenerarOrdenTrabajo){
							((GenerarOrdenTrabajo)view).llenarListaPiezasRepuesto(piezas);
						}else{
							//implementar para el editar
						}
					}else{
						int pos=piezas.indexOf(objeto);
						piezas.remove(pos);
						((GenerarOrdenTrabajo)view).eliminarElementoPanel(pos);
					}
			}});
			elegir.setImmediate(true);
			opciones.addComponents(elegir);
			obj[i]=opciones;	
			tabla.addItem(obj,((Entidad)objeto).getId());
		}
		tabla.setPageLength(tabla.size());
	}
	
	
	
}
