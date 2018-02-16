package infoartex.artex.componentes;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.ConfiguracionGlobal;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.TipoMedio;
import infoartex.artex.dominio.Torre;
import infoartex.artex.dominio.Traslado;
import infoartex.artex.vistas.Administrar;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class RegistrarMedio extends ComponenteGenerico  {
	
	//obligado
	private final CustomLayout container;
    private final VerticalLayout main;
    private final Panel detallesPC;
    private List<Departamento> lista;
    
  
    //Constructor del componente
    public RegistrarMedio (final Administrar view) {
		super(view);
		main = new VerticalLayout();
		//usar formulario de los layouts que tiene el tema
        container=new CustomLayout("formulario");
        detallesPC=new Panel("Detalles de los componentes de la torre");
        //metodo que arranca los componentes de vaadin
        initComponents(view);
	}  
    
    private void initComponents(final Administrar view) {
		Panel datosBasico = new Panel("Rellene los siguientes campos");
        datosBasico.setSizeUndefined();
        FormLayout formularioBasico = new FormLayout();
        formularioBasico.setMargin(true);
        formularioBasico.setWidth("100%");
        @SuppressWarnings("unchecked")
		List<ConfiguracionGlobal> config=(List<ConfiguracionGlobal>) fachada.listarTodos(ConfiguracionGlobal.class);
        String ccosto=config.isEmpty()?"0":config.get(0).getCentroCostoSucursal();
        
        final TextField inventario = new TextField("Inventario");
        inventario.setNullRepresentation("");
        inventario.setImmediate(true);
        inventario.setWidth("100%");
        inventario.setStyleName("form-control");
        
        final ComboBox departamentos=new ComboBox("Departamento");
        departamentos.addItem("Seleccione");
        lista=cargarDepartamentos(departamentos);
        departamentos.setNullSelectionItemId("Seleccione");
        departamentos.setImmediate(true);
        
        final ComboBox tipo = new ComboBox("Elija el tipo de medio");
        tipo.addItem("Seleccione");
        tipo.setNullSelectionItemId("Seleccione");
        cargarTipomedio(tipo);
        
        final TextField marca = new TextField("Marca");
        marca.setNullRepresentation("");
        marca.setImmediate(true);
        marca.setWidth("100%");
        marca.setStyleName("form-control");
        
        final TextField modelo = new TextField("Modelo");
        modelo.setNullRepresentation("");
        modelo.setImmediate(true);
        modelo.setWidth("100%");
        modelo.setStyleName("form-control");
        
        final ComboBox estado = new ComboBox("Estado del medio");
        estado.addItem("Seleccione");
        estado.addItem("Explotación");
        estado.addItem("Ocioso");
        estado.addItem("Espera de pieza");
        estado.setNullSelectionItemId("Seleccione");
        estado.setValue("Explotación");
        /**
         * Detalles PC
         */
        
        FormLayout datosPC = new FormLayout();
        final TextField fuente=new TextField("Cap. de la Fuente (W)");
        fuente.setNullRepresentation("");
        fuente.setImmediate(true);
        fuente.setWidth("100%");
        fuente.setStyleName("form-control");
        
        final TextField board=new TextField("Motherboard");
        board.setNullRepresentation("");
        board.setImmediate(true);
        board.setWidth("100%");
        board.setStyleName("form-control");
        
        final TextField procesador=new TextField("Procesador");
        procesador.setNullRepresentation("");
        procesador.setImmediate(true);
        procesador.setWidth("100%");
        procesador.setStyleName("form-control");
        
        final TextField ram=new TextField("Memoria RAM (Mbytes)");
        ram.setNullRepresentation("");
        ram.setImmediate(true);
        ram.setWidth("100%");
        ram.setStyleName("form-control");
        
        final ComboBox tiporam = new ComboBox("Tipo de RAM");
        tiporam.addItem("Seleccione");
        tiporam.setNullSelectionItemId("Seleccione");
        tiporam.addItem("DIM");
        tiporam.addItem("DDR");
        tiporam.addItem("DDR2");
        tiporam.addItem("DDR3");
        tiporam.addItem("DDR4");
        
        final TextField hdd=new TextField("Disco Duro (Gbytes)");
        hdd.setNullRepresentation("");
        hdd.setImmediate(true);
        hdd.setWidth("100%");
        hdd.setStyleName("form-control");
        
        final TextField marcahdd=new TextField("Marca Disco Duro ");
        marcahdd.setNullRepresentation("");
        marcahdd.setImmediate(true);
        marcahdd.setWidth("100%");
        marcahdd.setStyleName("form-control");
        
        
        final TextField optica=new TextField("Unidad Óptica");
        optica.setNullRepresentation("");
        optica.setImmediate(true);
        optica.setWidth("100%");
        optica.setStyleName("form-control");
        
        final TextField red=new TextField("Adaptador de red");
        red.setNullRepresentation("");
        red.setImmediate(true);
        red.setWidth("100%");
        red.setStyleName("form-control");
        
        final TextField mac=new TextField("Dirección MAC");
        mac.setNullRepresentation("");
        mac.setImmediate(true);
        mac.setWidth("100%");
        mac.setStyleName("form-control");
        
        final TextField otros=new TextField("Otros dispositivos");
        otros.setNullRepresentation("");
        otros.setImmediate(true);
        otros.setSizeFull();
        otros.setStyleName("form-control");
        
        datosPC.addComponents(fuente,board,procesador,ram,tiporam,hdd,marcahdd,optica,red,mac,otros);
        datosPC.setMargin(true);
        datosPC.setWidth("100%");
        detallesPC.setContent(datosPC);
        detallesPC.setWidth("100%");
        
        final BeanFieldGroup<MedioInformatico> validador=new BeanFieldGroup<>(MedioInformatico.class);
		validador.setItemDataSource(new MedioInformatico());
        validador.bind(inventario, "inventario");
        validador.bind(marca, "marca");
        validador.bind(modelo, "modelo");
        
        final BeanFieldGroup<Torre> validadorTorre=new BeanFieldGroup<>(Torre.class);  
        validadorTorre.setItemDataSource(new Torre());
		validadorTorre.bind(fuente, "fuente");
		validadorTorre.bind(board, "board");
		validadorTorre.bind(procesador, "procesador");
		validadorTorre.bind(ram, "ram");
		validadorTorre.bind(tiporam, "tiporam");
		validadorTorre.bind(hdd, "hdd");
		validadorTorre.bind(marcahdd, "marcahdd");
		validadorTorre.bind(optica, "optica");
		validadorTorre.bind(red, "red");
		validadorTorre.bind(mac, "mac");
		validadorTorre.bind(otros, "otros");
		inventario.setValue(ccosto+"-");
        tipo.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
					if(tipo.getValue()!=null&&tipo.getValue().equals("Torre")){
						main.addComponent(detallesPC, main.getComponentCount()-1);
						Torre torre=new Torre(inventario.getValue(),"Torre",marca.getValue(),modelo.getValue(),"","","","","","","","","","","","");
						validadorTorre.setItemDataSource(torre);
						validador.unbind(inventario);
						validador.unbind(marca);
						validador.unbind(modelo);
						validadorTorre.bind(inventario, "inventario");
						validadorTorre.bind(marca, "marca");
						validadorTorre.bind(modelo, "modelo");   
						validadorTorre.bind(fuente, "fuente");
						validadorTorre.bind(board, "board");
						validadorTorre.bind(procesador, "procesador");
						validadorTorre.bind(ram, "ram");
						validadorTorre.bind(tiporam, "tiporam");
						validadorTorre.bind(hdd, "hdd");
						validadorTorre.bind(marcahdd, "marcahdd");
						validadorTorre.bind(optica, "optica");
						validadorTorre.bind(red, "red");
						validadorTorre.bind(mac, "mac");
						validadorTorre.bind(otros, "otros");
			            
			            
					}else{
						main.removeComponent(detallesPC);
						MedioInformatico medio=new MedioInformatico(inventario.getValue(),"",marca.getValue(),modelo.getValue(),"");   
						validador.setItemDataSource(medio);
						if(validadorTorre.getPropertyId(inventario)!=null){
							validadorTorre.unbind(inventario);
							validadorTorre.unbind(marca);
							validadorTorre.unbind(modelo);
							validadorTorre.unbind(fuente);
							validadorTorre.unbind(board);
							validadorTorre.unbind(procesador);
							validadorTorre.unbind(ram);
							validadorTorre.unbind(tiporam);
							validadorTorre.unbind(hdd);
							validadorTorre.unbind(marcahdd);
							validadorTorre.unbind(optica);
							validadorTorre.unbind(red);
							validadorTorre.unbind(mac);
							validadorTorre.unbind(otros);
						}
						validador.bind(inventario, "inventario");
			            validador.bind(marca, "marca");
			            validador.bind(modelo, "modelo");
					}
			}
		});
        
        tipo.setImmediate(true);

        formularioBasico.addComponents(inventario,departamentos,tipo,marca,modelo,estado);
        datosBasico.setContent(formularioBasico);
        
        HorizontalLayout controles=new HorizontalLayout();
        
        NativeButton registrar=new NativeButton("Registrar", new Button.ClickListener() {

			@Override
            public void buttonClick(Button.ClickEvent event) {

                	if(departamentos.getValue()!=null){
                		if(tipo.getValue()!=null){
                			if(estado.getValue()!=null){
                			if(tipo.getValue().equals("Torre"))	{
                                try {		
                                validadorTorre.commit();
                                validadorTorre.getItemDataSource().getBean().setTipo(tipo.getValue().toString());
                                validadorTorre.getItemDataSource().getBean().setEstado(estado.getValue().toString());
                                GregorianCalendar gc=new GregorianCalendar();
                                gc.setTime(new Date());
                                int horaGC=gc.get(GregorianCalendar.HOUR_OF_DAY)>12||gc.get(GregorianCalendar.HOUR_OF_DAY)==0?Math.abs(gc.get(GregorianCalendar.HOUR_OF_DAY)-12):gc.get(GregorianCalendar.HOUR_OF_DAY);
                                int minutosGC=gc.get(GregorianCalendar.MINUTE);
                                String horasave=horaGC+":"+(minutosGC>=0&&minutosGC<10?"0"+minutosGC:minutosGC)+" "+(gc.get(GregorianCalendar.HOUR_OF_DAY)>=0&&gc.get(GregorianCalendar.HOUR_OF_DAY)<12?"AM":"PM");
                                validadorTorre.getItemDataSource().getBean().getHistorial().add(new Traslado((fachada.maximoID(Traslado.class)+1)+"","Creado con estado: "+estado.getValue(),""+VaadinSession.getCurrent().getAttribute("usuario"),new Date(),horasave,"No definido","No definido"));
                                actualizarDepartamento(lista, departamentos.getValue().toString(), validadorTorre.getItemDataSource().getBean());
                    			Notificaciones.NotificarSubmit("Medio informático registrado correctamente");
                    			inventario.setValue("");
                                marca.setValue("");
                                modelo.setValue("");
                                //torre
                                fuente.setValue("");
                            	board.setValue("");
                            	procesador.setValue("");
                            	ram.setValue("");
                            	tiporam.setValue("");
                            	hdd.setValue("");
                            	marcahdd.setValue("");
                            	optica.setValue("");
                            	red.setValue("");
                            	mac.setValue("");
                            	otros.setValue("");
                            	validadorTorre.setItemDataSource(new Torre("","Torre","","","","","","","","","","","","","",""));
                            	validadorTorre.bind(inventario, "inventario");
        						validadorTorre.bind(marca, "marca");
        						validadorTorre.bind(modelo, "modelo");   
        						validadorTorre.bind(fuente, "fuente");
        						validadorTorre.bind(board, "board");
        						validadorTorre.bind(procesador, "procesador");
        						validadorTorre.bind(ram, "ram");
        						validadorTorre.bind(tiporam, "tiporam");
        						validadorTorre.bind(hdd, "hdd");
        						validadorTorre.bind(marcahdd, "marcahdd");
        						validadorTorre.bind(optica, "optica");
        						validadorTorre.bind(red, "red");
        						validadorTorre.bind(mac, "mac");
        						validadorTorre.bind(otros, "otros");
                            	lista=cargarDepartamentos(departamentos);
                            	actualizar(validadorTorre.getItemDataSource().getBean().getClass());
                                }catch(Exception ex){
                                	Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
                                    //Logger.getLogger(RegistrarMedio.class.getName()).log(Level.SEVERE, null, ex);
                                }
                			}else{
                				try{
                				validador.commit();
                                validador.getItemDataSource().getBean().setTipo(tipo.getValue().toString());
                                GregorianCalendar gc=new GregorianCalendar();
                                gc.setTime(new Date());
                                int horaGC=gc.get(GregorianCalendar.HOUR_OF_DAY)>12||gc.get(GregorianCalendar.HOUR_OF_DAY)==0?Math.abs(gc.get(GregorianCalendar.HOUR_OF_DAY)-12):gc.get(GregorianCalendar.HOUR_OF_DAY);
                                int minutosGC=gc.get(GregorianCalendar.MINUTE);
                                String horasave=horaGC+":"+(minutosGC>=0&&minutosGC<10?"0"+minutosGC:minutosGC)+" "+(gc.get(GregorianCalendar.HOUR_OF_DAY)>=0&&gc.get(GregorianCalendar.HOUR_OF_DAY)<12?"AM":"PM");
                                validador.getItemDataSource().getBean().getHistorial().add(new Traslado((fachada.maximoID(Traslado.class)+1)+"","Creado con estado: "+estado.getValue(),""+VaadinSession.getCurrent().getAttribute("usuario"),new Date(),horasave,"No definido","No definido"));
                                validador.getItemDataSource().getBean().setEstado(estado.getValue().toString());
                                actualizarDepartamento(lista, departamentos.getValue().toString(), validador.getItemDataSource().getBean());
                                Notificaciones.NotificarSubmit("Medio informático registrado correctamente");
                                validador.setItemDataSource(new MedioInformatico("","","","",""));
                                validador.bind(inventario, "inventario");
                                validador.bind(marca, "marca");
                                validador.bind(modelo, "modelo");
                                lista=cargarDepartamentos(departamentos);
                                actualizar(validador.getItemDataSource().getBean().getClass());
                				}catch(Exception ex){
                					Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
                                   // Logger.getLogger(RegistrarMedio.class.getName()).log(Level.SEVERE, null, ex);
                				}
                			}
                		}else{
                			Notificaciones.NotificarError("Debe seleccionar un estado para el medio");
                		}
                	   }else{
                		Notificaciones.NotificarError("Debe seleccionar el tipo de medio");
                		}
                	}else{
                		Notificaciones.NotificarError("Debe elegir un departamento");
                	}
                
            }
        }); 
        
        registrar.setStyleName("btn btn-info botonControl");
        NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                view.contenidoAnterior();
            }
        });
        
        cancelar.setStyleName("btn btn-danger botonControl");
        registrar.setIcon(FontAwesome.CHECK);
        cancelar.setIcon(FontAwesome.CLOSE);
        controles.addComponents(registrar,cancelar);
        controles.setStyleName("controles");
        controles.setSpacing(true);
        datosBasico.setWidth("100%");
        main.addComponents(datosBasico,controles);
        container.addComponent(main,"formulario");
        Label nombreForm=new Label("Registrar nuevo medio informático");
        nombreForm.setStyleName("text-left encabezado");
        container.addComponent(nombreForm, "nombreFormulario");
        actualizar(validador.getItemDataSource().getBean().getClass());
        setCompositionRoot(container);
    }
    
    public void actualizar(Class<?> clazz){
    	Label medios =new Label(fachada.cantidadElementos(clazz)+" Medios Informáticos Registrados");
    	medios.setStyleName("text-right");
    	
        container.addComponent(medios, "datos");
    }
    
    public boolean esValidoField(TextField field){
    	return field.getValue()!=null&&!field.getValue().equals("");
    }
    
    private List<Departamento> cargarDepartamentos(ComboBox departamentos){
		@SuppressWarnings("unchecked")
		List<Departamento> lista=(List<Departamento>) getFachada().listarTodos(Departamento.class);
		for(Departamento d:lista){
			departamentos.addItem(d.getNombre());
		}
		return lista;
	}
	
    private void actualizarDepartamento(List<Departamento> lista, String departamento ,MedioInformatico i) throws Exception{
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
		dep.getMedio().add(i);
		fachada.actualizarEntidad(dep);
	}
    
    private List<TipoMedio> cargarTipomedio(ComboBox tipomedios){
		@SuppressWarnings("unchecked")
		List<TipoMedio> lista=(List<TipoMedio>) getFachada().listarTodos(TipoMedio.class);
		for(TipoMedio d:lista){
			tipomedios.addItem(d.getDenominacion());
		}
		return lista;
	}
    
}

