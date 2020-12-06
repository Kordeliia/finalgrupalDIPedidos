package Controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Modelo.Pedido;
public class ControllerV2 implements Initializable {
	//declaramos variables
    @FXML
    private TextField tfnombre;
    @FXML
    private TextField tfapellidos;
    @FXML
    private TextField tfdni;
    @FXML
    private TextField tfcorreo;
    @FXML
    private TextField tfntelefono;
    @FXML
    private ChoiceBox tftpedido;
    @FXML
    private TextField tfobs;
    @FXML
    private Button btnBORRAR;
    @FXML
    private Button btnEnviardatos;
    @FXML
    private Button btnSalir;
    private Pedido pedido;
    private ObservableList<Pedido> olpedido;
    boolean emailverfificado=false;
    public void initialize(URL url, ResourceBundle rb) {
    	//implementamos el ChoiceBox para mostrar que apartados tiene que mostrar
    	tftpedido.setItems(FXCollections.observableArrayList("Desarrollo Web","Desarrollo Apps","Mockup", "Disenyo grafico"));}
   //establecemos los atributos que nos enviará el controlador ControllerV1
    public void initAttributtes(ObservableList<Pedido> olpedido) {
        this.olpedido = olpedido;}
    public void initAttributtes(ObservableList<Pedido> olpedido, Modelo.Pedido ped) {
        this.olpedido = olpedido;
        this.pedido=ped;
        this.tfnombre.setText(ped.getNombre());
        this.tfapellidos.setText(ped.getApellidos());
        this.tfdni.setText(ped.getDni());
        this.tfcorreo.setText(ped.getCorreo());
        this.tfntelefono.setText(ped.getTelefono());
        this.tftpedido.setValue(ped.getTpedido());
        this.tfobs.setText(ped.getObs());}
    @FXML
    //Esta función se vincula con el botón añadir pedido
    //Se utilizará tanto para agregar nuevos pedidos, como para modificarlos
    private void enviarpedidos(ActionEvent event) {
    	//primero valida los TextField, el ChoiceBox, para que no estén en blanco y el e-mail tenga formato de correo
    	validarEmail();
		if(tfnombre.getText()==""){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Ha ocurrido un error. El campo de Nombre esta vacio");
			a.show();}
		else if(tfapellidos.getText()==""){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Ha ocurrido un error. El campo de Apellidos está vacío");
			a.show();}
		else if(tfdni.getText()==""){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Ha ocurrido un error. El campo de DNI está vacío");
			a.show();}
		else if(tfcorreo.getText()==""){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Ha ocurrido un error. El campo de Correo está vacío");
			a.show();}
		else if(tftpedido.getValue()==""){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Ha ocurrido un error. El campo de Tipo de pedido electrónico está vacío");
			a.show();}
		else if(tfntelefono.getText()==""){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Ha ocurrido un error. El campo de telefono está vacío");
			a.show();}
		else if(tfobs.getText()==""){
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setContentText("Ha ocurrido un error. El campo de observaciones está vacío");
			a.show();}
		if(emailverfificado==false){
			Alert a = new Alert(Alert.AlertType.ERROR); a.setContentText("Ha ocurrido un error. El campo de correo no tiene estructura nombre@example.es");
			a.show();}
		else {
        //Guardo en variables el contenido de los TextField y del ChoiceBox
        String nombre = this.tfnombre.getText();
        String apellidos = this.tfapellidos.getText();
        String dni = this.tfdni.getText();
        String correo = this.tfcorreo.getText();
        String telefono = this.tfntelefono.getText();
        String tpedido = (String) this.tftpedido.getValue();
        String obs = this.tfobs.getText();
        //Creo un nuevo objeto de Pedido a partir de las anteriores variables que creamos a partir del contenido de TextFields y ChoiceBox
      Modelo.Pedido nuevopedido = new Modelo.Pedido(nombre, apellidos, dni, correo, telefono, tpedido, obs);
      //Comprobamos si lo que vamos a hacer es modificar un pedido o crear uno nuevo.
      //Si ya tenía contenido en pedido, es decir, sino es null, le damos los nuevos valores, estamos modificando un pedido
      if(!olpedido.contains(nuevopedido)){
    	   if(this.pedido != null ) {
    		   this.pedido.setNombre(nombre);
    		   this.pedido.setApellidos(apellidos);
    		   this.pedido.setDni(dni);
    		   this.pedido.setCorreo(correo);
    		   this.pedido.setTelefono(telefono);
    		   this.pedido.setTpedido(tpedido);
    		   this.pedido.setObs(obs);}
    	   //si era null, es else, estamos creando un nuevo pedido, no es ya modificar. Es crear
    	   else {
    		   //si creamos un nuevo pedido llamamos a la funcion PedidoDAO que nos cree un nuevo registros a la BBD
    		   //para ello se llama a la clase PedidoDAO, a la funcion insertarPedidoBBDD
    		   //gracias a esta funcion hibernate genera una nueva linea en la BBDD con los datos que aquí hemos proporcionado
    		   this.pedido=nuevopedido;
    		   Modelo.PedidoDAO.insertarPedidoBBDD(nuevopedido);
    		   //generamos alerta de nuevo pedido
        	   Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setHeaderText(null);
               alert.setTitle("Éxito en la operación");
               alert.setContentText("Se ha añadido de forma correcta un nuevo pedido");
               alert.showAndWait();}
    	   //cerramos esta ventana para volver a la anterior
           Stage stage = (Stage) this.btnEnviardatos.getScene().getWindow();
           stage.close();}
       else {
    	   //si es else, es que este pedido ya existe. No es posible ingresarlo porque existe ya en la tabla o en la BBDD
    		   Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setTitle("Ha ocurrido un error");
               alert.setContentText("Este pedido ya ha sido anteriormente creado");
               alert.showAndWait();}}}
    //esta funcion la usamos para validar si el email tiene la estructura correcta de email
    private boolean validarEmail() {
		 Pattern pattern = Pattern
				 .compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		 String email = tfcorreo.getText();
		 Matcher mather = pattern.matcher(email);
		 if (mather.find() == true) {
			 emailverfificado=true;
	        }else {
	        	emailverfificado=false;}
		 return emailverfificado;}
    @FXML
    //por esta funcion salimos de la ventana sin guardar ningun registro
    //funcion vinculada al boton salir
    private void salir(ActionEvent event) throws IOException{
    	this.pedido=null;
        // Cerrar ventana
        Stage stage = (Stage) this.btnEnviardatos.getScene().getWindow();
        stage.close();}
    //esta funcion se usa para devolver al otro controlador los datos del pedido, devuelve el objeto pedido
    public Modelo.Pedido getPedido() {
        return pedido;}
    @FXML
    //funcion vincuada con el boton borrar
    //borramos los datos de los TextField y el ChoiceBox queda con valor ""
    private void clearFields(){
    	tfnombre.clear();
    	tfapellidos.clear();
    	tfdni.clear();
    	tfcorreo.clear();
    	tfntelefono.clear();
    	tftpedido.setValue("");
    	tfobs.clear();}}