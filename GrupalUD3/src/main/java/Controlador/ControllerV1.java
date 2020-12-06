package Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.x.protobuf.Mysqlx.Error;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Modelo.Pedido;
import Modelo.PedidoDAO;
public class ControllerV1 implements Initializable {
	//Declaramos variables
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnRefrescar;
    @FXML
    private TextField tfnombre;
    @FXML
    private TextField tfapellidos;
    @FXML
    private TableView<Pedido> tblPedidos;
    @FXML
    private TableColumn colnombre;
    @FXML
    private TableColumn colapellidos;
    @FXML
    private TableColumn coldni;
    @FXML
    private TableColumn colcorreo;
    @FXML
    private TableColumn colntelefono;
    @FXML
    private TableColumn colpedido;
    @FXML
    private TableColumn colobs;
    private ObservableList<Pedido> olpedido;
    public void initialize(URL url, ResourceBundle rb) {
    	olpedido = FXCollections.observableArrayList();
    	//se guardan en las columnas los valores de las variables
    	this.colnombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    	this.colapellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
    	this.coldni.setCellValueFactory(new PropertyValueFactory("dni"));
    	this.colcorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    	this.colntelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    	this.colpedido.setCellValueFactory(new PropertyValueFactory("tpedido"));
    	this.colobs.setCellValueFactory(new PropertyValueFactory("obs"));}
    @FXML
    //funcion refrescar, nos devuelve los valores de nuestra BBDD
    private void refrescar(ActionEvent event) { 
    	//Si ya existen valores en la tabla, los elimina. Ya que queremos recuperar los de la BBDD y sino se duplican
    	if(olpedido!=null) {
    		olpedido.clear();}
    	//Muestra los valores de la BBDD a través de la clase PedidoDAO y de su funcion mostrarPedidos()
    		ArrayList<Pedido> nuevospedidos = PedidoDAO.mostrarPedidos();
        	this.olpedido.addAll(nuevospedidos);
        	this.tblPedidos.refresh();}
    @FXML
    //funcion para agregar un nuevo pedido
    private void agregarPedido(ActionEvent event) {
    	//try-catch para capturar posibles excepciones
        try {
        	//carga la ventana de vista2.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/vista2.fxml"));
            Parent root1 = loader.load();
            //carga el controlador de la vista2.fxml que es ControlerV2.java
            ControllerV2 controlador = (ControllerV2) loader.getController();
            controlador.initAttributtes(olpedido);
            //inicia la escena
            Scene scene = new Scene(root1);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            //recupera los datos que ha devuelto el controlador controladorV1 gracias al getPedido
            Modelo.Pedido nuevopedido = controlador.getPedido();
            //si no es nulo, lo incluye en la tabla y la refresca para mostrarlo
            if (nuevopedido != null) {
                this.olpedido.add(nuevopedido);
                this.tblPedidos.setItems(olpedido);
                this.tblPedidos.refresh();}
        } catch (IOException ex) {
        	//alerta de la excepcion!!
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();}}
    @FXML
    //funcion para refrescar la tabla
    private void refrescar() {
    	this.tblPedidos.refresh();}
    @FXML
    //funcion que utilizaramos para eliminar un registro de la tabla y de la BBDD
    	private void eliminar (ActionEvent event) {
    	//tenemos que coger el pedido seleccionado
    	Modelo.Pedido nuevopedido = this.tblPedidos.getSelectionModel().getSelectedItem();
    	if (nuevopedido==null) {
    		//si el objeto seleccionado es null, da error. Tienes que seleccionar un pedido no un hueco vacio
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Ha ocurrido un error");
            alert.setContentText("Debes seleccionar un pedido para poder eliminarlo");
            alert.showAndWait();   		
    	} else {
    		//si el pedido no esta vacio lo borramos de la tabla y refrescamos
    		this.olpedido.remove(nuevopedido);
    		this.tblPedidos.setItems(olpedido);
    		this.tblPedidos.refresh();
    		//como lo queremos borrar también de la BBDD llamamos a la clase PedidoDAO
    		//dentro PedidoDAO llamamos a su función borrarPedidoBBDD
    		//de esta forma, esa funcion se encarga de borrar el pedido de la BBDD a traves de Hibernate
    		Modelo.PedidoDAO.borrarPedidoBBDD(nuevopedido);
    		//Alerta de informacion: se ha borrado el pedido
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("La tarea se ha efectuado con exito");
            alert.setContentText("Se ha eliminado el pedido con exito");
            alert.showAndWait();}}
    @FXML
    //función para modificar un pedido de la BBDD y de la tabla
    private void modificar() {
    	//como en la anterior función primero tenemos que seleccionar un pedido
    	Modelo.Pedido nuevopedido = this.tblPedidos.getSelectionModel().getSelectedItem();
        if (nuevopedido == null) {
        	//si el pedido es null muestra un mensaje de Error. Tienes que seleccionar un pedido!!
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar una persona para poder modificarla");
            alert.showAndWait();
            //si no es nulo realiza una serie de acciones dentro de un try-catch
            // con el try-catch lo que hacemos es capturar posibles excepciones
        } else{try {
        	//cargamos la vista de la ventana vista2.fxml
        	//llamamos e instaciamos el controlador que nos devolverá el pedido modificado.
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Vista/vista2.fxml"));
                Parent root2 = loader2.load();
                ControllerV2 controlador2 = loader2.getController();
                //enviamos los datos, del pedido que queremos modificar
                controlador2.initAttributtes(olpedido, nuevopedido);
                //creamos e iniciarmos la ventana
                Scene scene2 = new Scene(root2);
                Stage stage2 = new Stage();
                stage2.initModality(Modality.APPLICATION_MODAL);
                stage2.setScene(scene2);
                stage2.showAndWait();
                //creamos un nuevo pedido que será un auxiliar a traves de la funcion getPedido() del controlador de ControllerV2
                Modelo.Pedido pedidoauxiliar = controlador2.getPedido();
                //llamamos a la clase PedidoDAO a su función actualizarPedidoBBDD, entregandoles el pedido como debe ser modificado
                Modelo.PedidoDAO.actualizarPedidoBBDD(nuevopedido);
                //si el pedidoauxiliar es diferente de null, se manda a refrescar la tabla.
                if (pedidoauxiliar != null) {
                    this.tblPedidos.refresh();}
            } catch (IOException ex) {
            	//Alerta para mostrar error por la excepción que hemos capturado
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();}}}} 