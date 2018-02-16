package infoartex.artex.componentes;

import infoartex.artex.bundles.Notificaciones;
import infoartex.artex.dominio.Departamento;
import infoartex.artex.dominio.Entidad;
import infoartex.artex.dominio.MedioInformatico;
import infoartex.artex.dominio.Torre;
import infoartex.artex.vistas.Administrar;

import java.util.List;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
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
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class EditarMedioInformatico extends ComponenteGenerico{
	//obligado
		private final CustomLayout container;
	    private final VerticalLayout main;
	    private final Panel detallesPC;
	    private ComboBox departamentos;
	    @SuppressWarnings("unused")
		private List<Departamento> lista;
	    private MedioInformatico medio;
	    private ComboBox estado;
	    private BeanFieldGroup<MedioInformatico> validador;
	    private BeanFieldGroup<Torre> validadorTorre;
	  
	    //Constructor del componente
	    public EditarMedioInformatico (final Administrar view) {
			super(view);
			main = new VerticalLayout();
			//usar formulario de los layouts que tiene el tema
	        container=new CustomLayout("formulario");
	        detallesPC=new Panel("Detalles de los componentes de la torre");
	        //metodo que arranca los componentes de vaadin
		}  
	    
	  //debe llamarse asi setEntidad, en todos los que hagas
		public void setEntidad(Entidad entidad){
			//lo de adentro lo cambias a tu gusto, pero te queda parecido, segun la entidad
			this.medio=(MedioInformatico)entidad;
	        validador=new BeanFieldGroup<>(MedioInformatico.class);
	        validadorTorre=new BeanFieldGroup<>(Torre.class);
			if(medio instanceof Torre){
				validadorTorre.setItemDataSource((Torre) medio);
			}else{
				validador.setItemDataSource(medio);
			}
		}
	    
	    public void initComponents(final Administrar view) {
	    	main.removeAllComponents();
			Panel datosBasico = new Panel("Rellene los siguientes campos");
	        datosBasico.setSizeUndefined();
	        FormLayout formularioBasico = new FormLayout();
	        formularioBasico.setMargin(true);
	        formularioBasico.setWidth("100%");
	        
	        final TextField inventario = new TextField("Inventario");
	        inventario.setNullRepresentation("");
	        inventario.setImmediate(true);
	        inventario.setWidth("100%");
	        inventario.setStyleName("form-control");
	        
	        departamentos=new ComboBox("Departamento");
	        departamentos.addItem("Seleccione");
	        lista=cargarDepartamentos(departamentos);
	        departamentos.setNullSelectionItemId("Seleccione");
	        departamentos.setEnabled(false);
	        departamentos.setImmediate(true);
	        
	        final ComboBox tipo = new ComboBox("Elija el tipo de medio");
	        tipo.addItem("Seleccione");
	        tipo.setNullSelectionItemId("Seleccione");
	        tipo.addItem("Torre");
	        tipo.addItem("Monitor");
	        tipo.addItem("Mouse");
	        tipo.addItem("Teclado");
	        tipo.addItem("UPS");
	        tipo.addItem("Impresora");
	        tipo.addItem("Scanner");
	        tipo.addItem("Switch");
	        tipo.addItem("Modem Externo");
	        tipo.addItem("Video BIN");
	        tipo.setEnabled(false);
	        
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
	        
	       estado = new ComboBox("Estado del medio");
	        estado.addItem("Seleccione");
	        estado.setNullSelectionItemId("Seleccione");
	        estado.addItem("Explotación");
	        estado.addItem("Ocioso");
	        estado.addItem("Espera de pieza");
	        estado.setEnabled(false);
	        estado.setImmediate(true);
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
	        otros.setWidth("100%");
	        otros.setStyleName("form-control");
	        
	        datosPC.addComponents(fuente,board,procesador,ram,tiporam,hdd,marcahdd,optica,red,mac,otros);
	        datosPC.setMargin(true);
	        datosPC.setWidth("100%");
	        detallesPC.setContent(datosPC);
	        detallesPC.setWidth("100%");
	        
	        

	        formularioBasico.addComponents(inventario,departamentos,tipo,marca,modelo,estado);
	        datosBasico.setContent(formularioBasico);
	        
	        HorizontalLayout controles=new HorizontalLayout();
	        
	       
	        departamentos.setValue(medio.getDepartamento().getNombre());
	        tipo.setValue(medio.getTipo());
	        estado.setValue(medio.getEstado());
	        if(medio instanceof Torre){
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
	        	validadorTorre.bind(red , "red");
	        	validadorTorre.bind(mac, "mac");
	        	validadorTorre.bind(otros, "otros");
	        }else{
	        	validador.bind(inventario, "inventario");
	 	        validador.bind(marca, "marca");
	 	        validador.bind(modelo, "modelo");
	        }
	        
	        NativeButton actualizar=new NativeButton("Actualizar", new Button.ClickListener() {

	            @Override
	            public void buttonClick(Button.ClickEvent event) {
	            	try {
	            		if(medio instanceof Torre){
	            			validadorTorre.commit();
							getFachada().actualizarEntidad(validadorTorre.getItemDataSource().getBean());
	            		}else{
						validador.commit();
						getFachada().actualizarEntidad(validador.getItemDataSource().getBean());
	            		}
						Notificaciones.NotificarSubmit("Medio informático actualizado correctamente");
	                    view.nuevoContenido(new ListarMediosInformaticos(view));
					} catch (CommitException e) {
						Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
					} catch (Exception e) {
						Notificaciones.NotificarError("Errores en los datos por favor revise los campos señalados");
					}
	                	
	                
	            }
	        }); 
	        actualizar.setStyleName("btn btn-info botonControl");
	        actualizar.setIcon(FontAwesome.EDIT);
	        NativeButton cancelar=new NativeButton("Cancelar", new Button.ClickListener() {

	            @Override
	            public void buttonClick(Button.ClickEvent event) {
	                view.contenidoAnterior();
	            }
	        });
	        
	        cancelar.setStyleName("btn btn-danger botonControl");
	        cancelar.setIcon(FontAwesome.CLOSE);
	        
	        
	        NativeButton cambiarEstado=new NativeButton("Cambiar estado", new Button.ClickListener() {

	            @Override
	            public void buttonClick(Button.ClickEvent event) {
	            	Window win=new Window("Cambiar estado de un medio");
					win.setContent(new CambiarEstadoMedioInformatico(view, medio,win));
					win.center();
					win.setResizable(false);
					win.setImmediate(true);
					win.setModal(true);
					win.setWidth("600px");
					getUI().addWindow(win);
	            }
	        }); 
	        cambiarEstado.setStyleName("btn btn-primary botonControl");
	        cambiarEstado.setIcon(FontAwesome.RETWEET);
	        
	        HorizontalLayout adicional=new HorizontalLayout();
	        adicional.addComponents(cambiarEstado);
	        adicional.setStyleName("controles");
	        adicional.setSpacing(true);
	        controles.addComponents(actualizar,cancelar);
	        controles.setStyleName("controles");
	        controles.setSpacing(true);
	        datosBasico.setWidth("100%");
	        main.addComponents(datosBasico,controles);
	        if(medio instanceof Torre){
	        	datosPC.addComponent(adicional);
	        	main.addComponent(detallesPC,main.getComponentCount()-1);
	        }else{
	        	formularioBasico.addComponent(adicional);
	        }
	        container.addComponent(main,"formulario");;
	        Label nombreForm=new Label("Editar medio informático");
	        nombreForm.setStyleName("text-left encabezado");
	        container.addComponent(nombreForm, "nombreFormulario");
	        setCompositionRoot(container);
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
	    
	    public void cambiarDepartamento(String nombDepartamento){
	    	departamentos.setValue(nombDepartamento);
	    }
	    
	    public void cambiarEstado(String nombEstado){
	    	estado.setValue(nombEstado);
	    }
}
