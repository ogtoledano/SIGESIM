package infoartex.artex.componentes;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
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
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Laptop;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.PC;
import infoartex.artex.dominio.Torre;
import infoartex.artex.vistas.Administrar;



@SuppressWarnings("serial")
public class ConformarPC extends ComponenteGenerico{
	
	//obligado
	private final CustomLayout container;
	private final VerticalLayout main;

	public ConformarPC(final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        //metodo que arranca los componentes de vaadin
        initComponents(view);
	}

	public void initComponents(final Administrar view) {
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
        departamentos.addItem("Seleccione");
        departamentos.setNullSelectionItemId("Seleccione");
        
        final List<MedioInformatico> listaMedios=new LinkedList<>();
        
        final List<Departamento> lista=cargarDepartamentos(departamentos);
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
        tiporam.setImmediate(true);
        
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
        
        
        final CheckBox tipo=new CheckBox("Laptop");
        tipo.setImmediate(true);
        
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
                	cargarMediosInformaticosFiltroVacio(departamentos.getValue().toString(),selector);
                }else{
                	cargarMediosFiltrados(selector, buscar.getValue(),departamentos.getValue().toString());
                }
            }
        }); 
        filtrar.setStyleName("btn btn-primary botonControl");
        filtrar.setIcon(FontAwesome.SEARCH);
        filtrar.setEnabled(false);
        opcionesFiltrado.addComponents(buscar,filtrar);
        opcionesFiltrado.setComponentAlignment(filtrar, Alignment.MIDDLE_RIGHT);
        opcionesFiltrado.setComponentAlignment(buscar, Alignment.MIDDLE_RIGHT);
        
        formularioMedios.addComponents(opcionesFiltrado,selector);
        medios.setContent(formularioMedios);
        departamentos.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				listaMedios.clear();
				if(departamentos.getValue()!=null){
					listaMedios.addAll(cargarMediosInformaticos(departamentos.getValue().toString(), selector));
					filtrar.setEnabled(true);
				}else{
					selector.removeAllItems();
					filtrar.setEnabled(false);
				}
			}
		});
        
        //Validación mediante el beanFieldGroup, el método bind mapea cada campo con el atributo de la clase, 
        //por defecto estos atributos tienen valor null
        final BeanFieldGroup<PC> validador=new BeanFieldGroup<PC>(PC.class);
        validador.setItemDataSource(new PC());
        validador.bind(nombrePC, "nombre");
        validador.bind(responsable, "responsable");
        
        final BeanFieldGroup<Laptop> validadorLaptop=new BeanFieldGroup<>(Laptop.class);
        validadorLaptop.setItemDataSource(new Laptop());
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
        tipo.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(tipo.getValue()){
					main.addComponent(datosLaptop, main.getComponentCount()-1);
					Laptop laptop=new Laptop(nombrePC.getValue(), responsable.getValue(), "", "", "", "", "", "", "", "", "", "", "", "");
					validadorLaptop.setItemDataSource(laptop);
					validador.unbind(nombrePC);
					validador.unbind(responsable);
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
				}else{
					main.removeComponent(datosLaptop);
					PC pc=new PC(nombrePC.getValue(), responsable.getValue());
					validador.setItemDataSource(pc);
					if(validadorLaptop.getPropertyId(nombrePC)!=null){
						validadorLaptop.unbind(nombrePC);
						validadorLaptop.unbind(responsable);
						validadorLaptop.unbind(inventario);
				        validadorLaptop.unbind(modelo);
				        validadorLaptop.unbind(serie);
				        validadorLaptop.unbind(modeloPlaca);
				        validadorLaptop.unbind(procesador);
				        validadorLaptop.unbind(capram);
				        validadorLaptop.unbind(marcaHDD);
				        validadorLaptop.unbind(capHDD);
				        validadorLaptop.unbind(optica);
				        validadorLaptop.unbind(display);
				        validadorLaptop.unbind(red);
					}
					validador.bind(nombrePC, "nombre");
			        validador.bind(responsable, "responsable");
				}
			}
		});
        
        NativeButton registrar=new NativeButton("Registrar", new Button.ClickListener() {

            @SuppressWarnings("unchecked")
			@Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                	//registrar en la BD
                	//ejecutar validacion
                	if(departamentos.getValue()!=null){
                	if(tipo.getValue())	{
                		if(tiporam.getValue()!=null){
                		if(validarLaptop(listaMedios,(Collection<Object>)selector.getValue())){
                		validadorLaptop.commit();
                        validadorLaptop.getItemDataSource().getBean().setMedios(new LinkedList<MedioInformatico>());
                        validadorLaptop.getItemDataSource().getBean().setTiporam(tiporam.getValue().toString());
                        actualizarMediosAPC(listaMedios, (Collection<Object>)selector.getValue(), validadorLaptop.getItemDataSource().getBean());
                        actualizarDepartamento(lista, departamentos.getValue().toString(), validadorLaptop.getItemDataSource().getBean());
                        Notificaciones.NotificarSubmit("Laptop registrada correctamente");
                        view.reemplazarContenido(new ConformarPC(view));
                		}else{
                			Notificaciones.NotificarError("No puede asignarle un medio de tipo Torre a una Laptop");
                		}
                		}else{
                			Notificaciones.NotificarError("Debe elegir un tipo de memoria RAM para la Laptop");
                		}
                	}else{
                		if(validarPC(listaMedios,(Collection<Object>)selector.getValue())){
                			validador.commit();
                			validador.getItemDataSource().getBean().setMedios(new LinkedList<MedioInformatico>());
                			actualizarMediosAPC(listaMedios, (Collection<Object>)selector.getValue(), validador.getItemDataSource().getBean());
                			actualizarDepartamento(lista, departamentos.getValue().toString(), validador.getItemDataSource().getBean());
                			Notificaciones.NotificarSubmit("PC registrada correctamente");
                			view.reemplazarContenido(new ConformarPC(view));
                		}else{
                			Notificaciones.NotificarError("La PC debe contener exáctamente una torre");
                		}
                	}
                	}else{
                		Notificaciones.NotificarError("Debe elegir un departamento");
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
        main.addComponents(datosBasico,tipo,medios,reg);
        container.addComponent(main, "formulario");
        Label nombreForm=new Label("Conformar PC");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        actualizar(validador.getItemDataSource().getBean().getClass());
        setCompositionRoot(container);		
	}
	
	private void actualizar(Class<?> clazz) {
		Label elementos =new Label(fachada.cantidadElementos(clazz)+" PC registradas");
		elementos.setStyleName("text-right");
        container.addComponent(elementos, "datos");
		
	}
	
	private List<Departamento> cargarDepartamentos(ComboBox departamentos){
		@SuppressWarnings("unchecked")
		List<Departamento> lista=(List<Departamento>) getFachada().listarTodos(Departamento.class);
		for(Departamento d:lista){
			departamentos.addItem(d.getNombre());
		}
		return lista;
	}
	
	private void actualizarDepartamento(List<Departamento> lista, String departamento ,PC i) throws Exception{
		boolean encontrado=false;
		int k=0;
		while(!encontrado&&k<lista.size()){
			if(lista.get(k).getNombre().equals(departamento)){
				encontrado=true;
			}else{
				k++;
			}
		}
		Departamento dep=lista.get(k);
		i.setDepartamento(dep);
		fachada.actualizarEntidad(i);
		dep.getComputadoras().add(i);
		fachada.actualizarEntidad(dep);
	}
	
	@SuppressWarnings("unchecked")
	private List<MedioInformatico> cargarMediosInformaticosFiltroVacio(String departamento,TwinColSelect selector){
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
	private List<MedioInformatico> cargarMediosInformaticos(String departamento,TwinColSelect selector){
		List<MedioInformatico> lista=(List<MedioInformatico>) getFachada().listarMedioSinUso(departamento,MedioInformatico.class);
		selector.removeAllItems();
		selector.setValue(new LinkedList<Object>());
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
		List<MedioInformatico> lista=(List<MedioInformatico>) getFachada().filtrarRapidoMedios(criterio,departamento,aux, MedioInformatico.class);
		for(MedioInformatico d:lista){
			selector.addItem(d.getInventario()+" - "+d.getTipo());
		}
		//para que cargue los ya elegidos
		for(Object v:valores){
			selector.addItem(v);
		}
		selector.setValue(valores);
		return lista;
	}
	
	private void actualizarMediosAPC(List<MedioInformatico> lista, Collection<Object> listaMedioPC, PC i) throws Exception{
		fachada.registrarEntidad(i);
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
		if(encontrado){
		MedioInformatico mi=lista.get(k);
		mi.setComputadora(i);
		mi.setNombComputadora(i.getNombre());
		fachada.actualizarEntidad(mi);
		i.getMedios().add(mi);
		}
		}
		fachada.actualizarEntidad(i);
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
			if(encontrado){
			lista2.add(lista.get(k));
			}
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
			if(encontrado){
			lista2.add(lista.get(k));
			}
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
