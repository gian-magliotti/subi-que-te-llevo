/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import excepciones.pedido.ExceptionPedido;
import excepciones.usuario.ExceptionUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import simulacion.RecursoCompartido;
import usuarios.Cliente;
import vista.VentanaCliente;


public class ControladorCliente implements ActionListener {
    private RecursoCompartido recursoCompartido;
    private VentanaCliente  vista;
    private Cliente cliente;
    
            
    public ControladorCliente(RecursoCompartido recursoCompartido, VentanaCliente vista)
    {
        super();
        this.recursoCompartido = recursoCompartido;
        this.vista = vista;
        this.vista.setActionListener(this);
    }
    
    /**
     * Ejecuta el metodo que corresponda segun el action command del parametro ActionEvent
     * @param evento evento que contiene el comando de la accion solicitada desde la visa
     */
    @Override
    public void actionPerformed(ActionEvent evento) {
        switch(evento.getActionCommand()) {
            case "ATRAS REG":
            case "ATRAS SESION":
                vista.setLogin();
                break;

            case "REGISTRAR":
                vista.setRegistro();
                break;

            case "INICIAR SESION":
                vista.setInicioSesion();
                break;

            case "INTENTO REGISTRO":
                intentoRegistro();
                break;

            case "INTENTO INICIO SESION":
                intentoInicioSesion();
                break;

            case "PAGAR":
                vista.setDialogFinViaje();
                break;

            case "PEDIR VIAJE":
                vista.disablePedirViaje();
                pedirViaje();
                break;

            case "PAGAR VIAJE":
                pagarViaje();
                vista.disablePagar();
                vista.enablePedirViaje();
                break;
            
            case "ABANDONAR":
                vista.dispose();
                recursoCompartido.desconectarUsuario();
                break;
                
            case "CONTINUAR PIDIENDO":
                vista.disablePagar();
                vista.enablePedirViaje();
                break;
        }
    }

    /**
     * Deriva en el recurso compartido el registro de un nuevo cliente de la empresa
     */
    private void intentoRegistro() {
        String nombre = vista.getNombre();
        String nombreUsuario = vista.getNombreUsuario();
        String contrasena = vista.getContrasena();

        cliente = new Cliente(nombreUsuario, nombre, contrasena);
        try {
            recursoCompartido.addCliente(cliente);
            cliente.setUsandoApp(true);
            vista.setDialog("Registro exitoso","Exito");
            vista.setApp();
        } catch (ExceptionUsuario ex) {
            vista.setDialog("Nombre usuario repetido","Error");
        }

    }

    /**
     * Intenta iniciar sesion con los datos ingresados por ventana del usuario<br>
     * Si encuentra abre la app, sino comunica que el error al intentar iniciar sesion
     */
    private void intentoInicioSesion() {
        String nombreUsuario = vista.getNombreUsuario();
        String contrasena = vista.getContrasena();

        try {
            cliente = recursoCompartido.getCliente(nombreUsuario, contrasena);
            if (cliente != null) {
                cliente.setUsandoApp(true);
                vista.setDialog("Inicio sesión exitoso","Exito");
                vista.setApp();
            } else {
                vista.setDialog("Usuario no encontrado","Error");
            }
        } catch (ExceptionUsuario ex) {
            vista.setDialog("Usuario no encontrado","Error");
        }

    }

    /**
     * Deriva en el recurso compartido la peticion de un nuevo viaje del cliente que esta usando la app
     */
    private void pedirViaje() {
        double distancia = vista.getDistancia();
        LocalDateTime fecha = vista.getFecha();
        int mascota = vista.getMascota();
        int equipaje = vista.getEquipaje();
        int cantPax = vista.getCantPax();
        String zona = vista.getZona();

        try {
            recursoCompartido.pedirViaje(cliente, zona, mascota, "transporte", equipaje, cantPax, distancia, fecha);
        } catch (ExceptionPedido ex) {
            vista.enablePedirViaje();
            vista.setDialog(ex.getMessage(),"Error");

        }
    }

    /**
     * Solicita al recurso compartido finalizar el viaje del cliente que esta usando la app
     */
    private void pagarViaje() {
        recursoCompartido.pagarViaje(cliente);
        vista.setDialogFinViaje();
    }
    
}
