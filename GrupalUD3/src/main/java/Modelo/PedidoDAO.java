package Modelo;
import java.util.ArrayList;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
public class PedidoDAO{
	//funcion para INSERTAR nueva linea en la BBDD
    public static void insertarPedidoBBDD(Pedido pedido){
    	//se crea objeto mf de SessionFactory a traves de la funcion getmf() de la clase ManejoDeSesion.java
    	SessionFactory mf = ManejoDeSesion.getmf();
    	//se crea objeto ms de Session a través de mf de SessionFactory
        Session ms = mf.openSession();
        //estructura try-catch para capturar posibles excepciones
        try{
        	//se inicia transaccion
            System.out.println("Un momento, estamos iniciando la conexión con la Base de Datos para introducir los datos a la tabla de pedidos");
            ms.beginTransaction();
            //se guarda (inserta) pedido nuevo, nueva fila en la BBDD
            System.out.println("Estamos enviando sus datos al servidor de la base de datos.");
            ms.save(pedido);
            //se ejecuta la accion a traves del commit
            ms.getTransaction().commit();
            System.out.println("Se ha introducido a la BBDD el nuevo pedido con éxito");
        }catch (Exception e){
            e.printStackTrace();}}
    //funcion para mostrar los datos de la BBDD
    public static ArrayList<Pedido> mostrarPedidos(){
    	//se crea objeto mf de SessionFactory a traves de la funcion getmf() de la clase ManejoDeSesion.java
        SessionFactory mf = ManejoDeSesion.getmf();
      //se crea objeto ms de Session a través de mf de SessionFactory
        Session ms = mf.openSession();
      //estructura try-catch para capturar posibles excepciones
    	try{
    		//se inicia transaccion
            ms.beginTransaction();
            //se ve el resultado de la sentencia FROM Pedido, que muestra el contenido de la tabla, y se guarda ese contenido en un array
            Query query = ms.createQuery("FROM Pedido");
            ArrayList<Pedido> olpedido = (ArrayList<Pedido>) query.list();
          //se ejecuta la accion a traves del commit
            ms.getTransaction().commit();
            //se devuelve el contenido de la tabla a través de la varaible olpedido
            return olpedido;
        }catch (Exception e){
        	//se capturan posibles excepciones
            e.printStackTrace();}
    	return null;}
    //funcion para MODIFICAR un pedido, una linea de la BBDD
    public static void actualizarPedidoBBDD(Pedido pedido){
    	//se crea objeto mf de SessionFactory a traves de la funcion getmf() de la clase ManejoDeSesion.java
   	 SessionFactory mf = Modelo.ManejoDeSesion.getmf();
   	 //se crea objeto ms de Session a través de mf de SessionFactory
        Session ms = mf.openSession();
        //estructura try-catch para capturar posibles excepciones
       try{
           System.out.println("Un momento, estamos iniciando la conexión con la Base de Datos para introducir los datos a la tabla de pedidos");
         //se inicia transaccion
           ms.beginTransaction();
           System.out.println("Estamos enviando sus datos al servidor de la base de datos.");
           //se upgradea la linea pedido
           ms.update(pedido);
         //se ejecuta la accion a traves del commit
           ms.getTransaction().commit();
           System.out.println("Se ha introducido a la BBDD el nuevo pedido con éxito");
       }catch (Exception e){
    	 //se capturan posibles excepciones
           e.printStackTrace();}}
  //funcion para BORRAR un pedido, una linea de la BBDD
        public static void borrarPedidoBBDD(Pedido pedido){
        	//se crea objeto mf de SessionFactory a traves de la funcion getmf() de la clase ManejoDeSesion.java
          	 SessionFactory mf = ManejoDeSesion.getmf();
          	//se crea objeto ms de Session a través de mf de SessionFactory
          	 Session ms = mf.openSession();
          	//estructura try-catch para capturar posibles excepciones
           try {
               System.out.println("Un momento, estamos iniciando la conexión con la Base de Datos para proceder a su lectura");
             //se inicia transaccion
               ms.beginTransaction();
               //se ELIMINA de la BBDD la linea pedido
               ms.delete(pedido);
               System.out.println("El pedido ha sido borrado de la BBDD");
             //se ejecuta la accion a traves del commit
               ms.getTransaction().commit();
           }catch (Exception e){
        	   //se captura la excepcion a través del try-catch
               e.printStackTrace();}}}
