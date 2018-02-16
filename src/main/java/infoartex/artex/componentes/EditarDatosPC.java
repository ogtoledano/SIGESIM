package infoartex.artex.componentes;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.Laptop;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.PC;
import infoartex.artex.dominio.Torre;
import infoartex.artex.vistas.Administrar;

@SuppressWarnings("serial")
public class EditarDatosPC extends ComponenteGenerico{

	private final CustomLayout container;
	private final VerticalLayout main;
	
	private PC pc;
	private BeanFieldGroup<PC> validador;
	private BeanFieldGroup<Laptop> validadorLaptop;
	
	public EditarDatosPC(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        //metodo que arranca los componentes de vaadin
	}
	
	//debe llamarse asi setEntidad, en todos los que hagas
  	public void setEntidad(Entidad entidad){
  		this.pc=(PC)entidad;
        validador=new BeanFieldGroup<>(PC.class);
        validadorLaptop=new BeanFieldGroup<>(Laptop.class);
		if(pc instanceof Laptop){
			validadorLaptop.setItemDataSource((Laptop) pc);
		}else{
			validador.setItemDataSource(pc);
		}
  	}
	
	public void initComponents(final Administrar view) {
		main.removeAllComponents();
		Panel datosBasico = new Panel("Rellene los campos");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        
        final TextField nombrePC = new TextField("Nombre de la PC");
        nombrePC.setNullRepresentation("");
        nombrePC.setImmediate(true);
        nombrePC.setStyleName("form-control");
        nombrePC.setSizeFull();
        
        final TextField responsable = new TextField("Responsable");
        responsable.setNullRepresentation("");
        responsable.setImmediate(true);
        responsable.setStyleName("form-control"); 
        responsable.setSizeFull();
        
        final ComboBox departamentos=new ComboBox("Departamento");
        departamentos.setEnabled(false);
        departamentos.setImmediate(true);
        departamentos.setSizeFull();
        departamentos.addItem(pc.getDepartamento().getNombre());
        departamentos.setValue(pc.getDepartamento().getNombre());
        final List<MedioInformatico> listaMedios=new LinkedList<>();
        departamentos.setImmediate(true);
        departamentos.setSizeFull();
        formularioBasico.addComponents(nombrePC,responsable,departamentos);
        datosBasico.setContent(formularioBasico);
        
        
        
        /*
         * Detalles Laptop
         */
        
        final Panel datosLaptop = new Panel("Rellene los campos");
        datosLaptop.setSizeUndefined();
        FormLayout formularioLaptop = new FormLayout();
        formularioLaptop.setMargin(true);
        formularioLaptop.setWidth("100%");
        
        final TextField inventario = new TextField("Inventario");
        inventario.setNullRepresentation("");
        inventario.setImmediate(true);
        inventario.setStyleName("form-control");
        inventario.setSizeFull();
        
        final TextField modelo = new TextField("Modelo");
        modelo.setNullRepresentation("");
        modelo.setImmediate(true);
        modelo.setStyleName("form-control");
        modelo.setSizeFull();
        
        final TextField serie = new TextField("Serie");
        serie.setNullRepresentation("");
        serie.setImmediate(true);
        serie.setStyleName("form-control");
        serie.setSizeFull();
        
        final TextField modeloPlaca = new TextField("Modelo placa");
        modeloPlaca.setNullRepresentation("");
        modeloPlaca.setImmediate(true);
        modeloPlaca.setStyleName("form-control");
        modeloPlaca.setSizeFull();
        
        final TextField procesador = new TextField("Procesador");
        procesador.setNullRepresentation("");
        procesador.setImmediate(true);
        procesador.setStyleName("form-control");
        procesador.setSizeFull();
        
        final TextField capram = new TextField("Capacidad de la RAM (MB)");
        capram.setNullRepresentation("");
        capram.setImmediate(true);
        capram.setStyleName("form-control");
        capram.setSizeFull();
        
        final ComboBox tiporam = new ComboBox("Tipo de RAM");
        tiporam.addItem("Seleccione");
        tiporam.setNullSelectionItemId("Seleccione");
        tiporam.addItem("DIM");
        tiporam.addItem("DDR");
        tiporam.addItem("DDR2");
        tiporam.addItem("DDR3");
        tiporam.addItem("DDR4");
        
        final TextField marcaHDD = new TextField("Marca del HDD");
        marcaHDD.setNullRepresentation("");
        marcaHDD.setImmediate(true);
        marcaHDD.setStyleName("form-control");
        marcaHDD.setSizeFull();
        
        final TextField capHDD = new TextField("Capacidad del HDD");
        capHDD.setNullRepresentation("");
        capHDD.setImmediate(true);
        capHDD.setStyleName("form-control");
        capHDD.setSizeFull();
        
        final TextField optica = new TextField("Unidad optica");
        optica.setNullRepresentation("");
        optica.setImmediate(true);
        optica.setStyleName("form-control");
        optica.setSizeFull();
        
        final TextField display = new TextField("Display");
        display.setNullRepresentation("");
        display.setImmediate(true);
        display.setStyleName("form-control");
        display.setSizeFull();
        
        final TextField red = new TextField("Red");
        red.setNullRepresentation("");
        red.setImmediate(true);
        red.setStyleName("form-control");
        red.setSizeFull();
        
        formularioLaptop.addComponents(inventario,modelo,serie,modeloPlaca,procesador,capram,tiporam,marcaHDD,capHDD,optica,display,red);
        datosLaptop.setContent(formularioLaptop);
        datosLaptop.setSizeFull();
        
        //medios
        Panel medios = new Panel("Medios de la PC");
        medios.setSizeUndefined();
        FormLayout formularioMedios = new FormLayout();
        formularioMedios.setMargin(true);
        formularioMedios.setWidth("100%");
        
        HorizontalLayout opcionesFiltrado=new HorizontalLayout(); 
        opcionesFiltrado.setStyleName("controles");
        
        final TextField buscar = new TextField();
        buscar.setNullRepresentation("");
        buscar.setImmediate(true);
        buscar.setStyleName("form-control");
        buscar.setInputPrompt("inventario, tipo de medio");
        
        final TwinColSelect selector=new TwinColSelect();
        selector.setLeftColumnCaption("Medios disponibles");
        selector.setRightColumnCaption("Medios para la PC a conformar");
        selector.setRows(10);
        selector.setWidth("90%");
        selector.setMultiSelect(true);
        selector.setImmediate(true);
        
        
        final NativeButton filtrar=new NativeButton("", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(buscar.getValue()==null||buscar.getValue().equals("")){
                	cargarMediosInformaticos(departamentos.getValue().toString(),selector);
                }else{
                	cargarMediosFiltrados(selector, buscar.getValue(),departamentos.getValue().toString());
                }
            }
        }); 
        filtrar.setStyleName("btn btn-primary botonControl");
        filtrar.setIcon(FontAwesome.SEARCH);
        opcionesFiltrado.addComponents(buscar,filtrar);
        opcionesFiltrado.setComponentAlignment(filtrar, Alignment.MIDDLE_RIGHT);
        opcionesFiltrado.setComponentAlignment(buscar, Alignment.MIDDLE_RIGHT);
        
        formularioMedios.addComponents(opcionesFiltrado,selector);
        medios.setContent(formularioMedios);
        
        
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
        listaMedios.addAll(cargarMediosInformaticosInicial(pc.getMedios(), departamentos.getValue().toString(), selector));
        if(pc instanceof Laptop){
        	validadorLaptop.bind(nombrePC, "nombre");
        	validadorLaptop.bind(responsable, "responsable");
        	validadorLaptop.bind(inventario, "inventario");
            validadorLaptop.bind(modelo, "modelo");
            validadorLaptop.bind(serie, "serie");
            validadorLaptop.bind(modeloPlaca, "modeloplaca");
            validadorLaptop.bind(procesador, "procesador");
            validadorLaptop.bind(capram, "capram");
            validadorLaptop.bind(marcaHDD, "marcahdd");
            validadorLaptop.bind(capHDD, "caphdd");
            validadorLaptop.bind(optica, "optica");
            validadorLaptop.bind(display, "display");
            validadorLaptop.bind(red, "red");
            tiporam.setValue(((Laptop)pc).getTiporam());
        }else{
        	validador.bind(nombrePC, "nombre");
            validador.bind(responsable, "responsable");
        }
        
        
        
        NativeButton registrar=new NativeButton("Actualizar", new Button.ClickListener() {

            @SuppressWarnings("unchecked")
			@Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	if(pc instanceof Laptop){
                		if(tiporam.getValue()!=null){
                		if(validarLaptop(listaMedios,(Collection<Object>)selector.getValue())){
                			validadorLaptop.commit();
                			//validadorLaptop.getItemDataSource().getBean().setMedios(new LinkedList<MedioInformatico>());
                			actualizarMediosAPC(listaMedios, (Collection<Object>)selector.getValue(), validadorLaptop.getItemDataSource().getBean());
                			Notificaciones.NotificarSubmit("Laptop actualizada correctamente");
                		}else{
                			Notificaciones.NotificarError("No puede asignarle un medio de tipo Torre a una Laptop");
                		}
                		}else{
                			Notificaciones.NotificarError("Debe elegir un tipo de memoria RAM para la Laptop");
                		}
                	}else{
                		if(validarPC(listaMedios,(Collection<Object>)selector.getValue())){
                			validador.commit();
                			//validador.getItemDataSource().getBean().setMedios(new LinkedList<MedioInformatico>());
                			actualizarMediosAPC(listaMedios, (Collection<Object>)selector.getValue(), validador.getItemDataSource().getBean());
                			Notificaciones.NotificarSubmit("PC de Escritorio actualizada correctamente");
                		}else{
                			Notificaciones.NotificarError("La PC de Escritorio debe contener exáctamente una torre");
                		}
                	}
                } catch (Exception ex) {
                	Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
                    Logger.getLogger(RegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                view.contenidoAnterior();
            }
        });
        
        registrar.setStyleName("btn btn-info botonControl");
        cancelar.setStyleName("btn btn-danger botonControl");
        registrar.setIcon(FontAwesome.CHECK);
        cancelar.setIcon(FontAwesome.CLOSE);
        HorizontalLayout reg=new HorizontalLayout();
        reg.addComponents(registrar,cancelar);
        reg.setSpacing(true);
        reg.setStyleName("controles");
        datosBasico.setWidth("100%");
        medios.setWidth("100%");
        main.addComponents(datosBasico,medios,reg);
        if(pc instanceof Laptop){
        	main.addComponent(datosLaptop, main.getComponentCount()-1);
        }
        container.addComponent(main, "formulario");
        Label nombreForm=new Label("Conformar PC");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        setCompositionRoot(container);		
	}
	@SuppressWarnings("unchecked")
	private List<MedioInformatico> cargarMediosInformaticosInicial(List<MedioInformatico> medios,String departamento,TwinColSelect selector){
		List<MedioInformatico> lista=(List<MedioInformatico>) getFachada().listarMedioSinUso(departamento,MedioInformatico.class);
		lista.addAll(medios);
		for(MedioInformatico d:lista){
			selector.addItem(d.getInventario()+" - "+d.getTipo());
		}
		Collection<Object>valores=new LinkedList<>();
		for(MedioInformatico d:medios){
			valores.add(d.getInventario()+" - "+d.getTipo());
		}
		selector.setValue(valores);
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	private List<MedioInformatico> cargarMediosInformaticos(String departamento,TwinColSelect selector){
		List<MedioInformatico> lista=(List<MedioInformatico>) getFachada().listarMedioSinUso(departamento,MedioInformatico.class);
		Collection<Object>valores=new LinkedList<>();
		valores.addAll((Collection<Object>)selector.getValue());
		selector.removeAllItems();
		selector.setValue(valores);
		for(MedioInformatico d:lista){
			selector.addItem(d.getInventario()+" - "+d.getTipo());
		}
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	private List<MedioInformatico> cargarMediosFiltrados(TwinColSelect selector, String criterio,String departamento){
		List<String> aux=new LinkedList<>();
		aux.add("inventario");
		aux.add("tipo");
		Collection<Object>valores=new LinkedList<>();
		valores.addAll((Collection<Object>)selector.getValue());
		selector.removeAllItems();
		selector.setValue(valores);
		List<MedioInformatico> lista=(List<MedioInformatico>) getFachada().filtrarRapidoMedios(criterio,departamento,aux, MedioInformatico.class);
		for(MedioInformatico d:lista){
			selector.addItem(d.getInventario()+" - "+d.getTipo());
		}
		//para que cargue los ya elegidos
		for(Object v:valores){
			selector.addItem(v);
		}
		return lista;
	}
	
	private void actualizarMediosAPC(List<MedioInformatico> lista, Collection<Object> listaMedioPC, PC i) throws Exception{
		
		List<Object> collect=new LinkedList<>();
		collect.addAll(listaMedioPC);
		List<MedioInformatico> salida=mediosActualizar(i, collect);
		for(MedioInformatico m:salida){
			m.setNombComputadora("Sin asignar");
			m.setComputadora(null);
			fachada.actualizarEntidad(m);
		}
		i.setMedios(new LinkedList<MedioInformatico>());
		for(Object medio:listaMedioPC){
		boolean encontrado=false;
		int k=0;
		while(!encontrado&&k<lista.size()){
			MedioInformatico elem=lista.get(k);
			String aux=elem.getInventario()+" - "+elem.getTipo();
			if(aux.equals(medio.toString())){
				encontrado=true;
			}else{
				k++;
			}
		}
		
		MedioInformatico mi=lista.get(k);
		mi.setComputadora(i);
		mi.setNombComputadora(i.getNombre());
		fachada.actualizarEntidad(mi);
		i.getMedios().add(mi);
		}
		fachada.actualizarEntidad(i);
	}
	
	public List<MedioInformatico> mediosActualizar(PC i, List<Object> listaMedioPC){
		List<MedioInformatico> salida=new LinkedList<>();
		List<MedioInformatico> medios=i.getMedios();
		for(MedioInformatico m:medios){
			boolean encontrado=false;
			int k=0;
			while(!encontrado&&k<listaMedioPC.size()){
				String aux=m.getInventario()+" - "+m.getTipo();
				if(aux.equals(listaMedioPC.get(k))){
					encontrado=true;
				}else{
					k++;
				}
			}
			if(!encontrado){
				salida.add(m);
			}
		}
		return salida;
	}
	
	public boolean validarPC(List<MedioInformatico> lista,Collection<Object> listaMedioPC){
		List<MedioInformatico>lista2=new LinkedList<>();
		for(Object medio:listaMedioPC){
			boolean encontrado=false;
			int k=0;
			while(!encontrado&&k<lista.size()){
				MedioInformatico elem=lista.get(k);
				String aux=elem.getInventario()+" - "+elem.getTipo();
				if(aux.equals(medio.toString())){
					encontrado=true;
				}else{
					k++;
				}
			}
			lista2.add(lista.get(k));
		}
		
		int cant=0;
		for(MedioInformatico m:lista2){
			if(m instanceof Torre){
				cant++;
			}
		}
		return cant==1;
	}
	
	public boolean validarLaptop(List<MedioInformatico> lista,Collection<Object> listaMedioPC){
		List<MedioInformatico>lista2=new LinkedList<>();
		for(Object medio:listaMedioPC){
			boolean encontrado=false;
			int k=0;
			while(!encontrado&&k<lista.size()){
				MedioInformatico elem=lista.get(k);
				String aux=elem.getInventario()+" - "+elem.getTipo();
				if(aux.equals(medio.toString())){
					encontrado=true;
				}else{
					k++;
				}
			}
			lista2.add(lista.get(k));
		}
		
		int cant=0;
		for(MedioInformatico m:lista2){
			if(m instanceof Torre){
				cant++;
			}
		}
		return cant==0;
	}

}
