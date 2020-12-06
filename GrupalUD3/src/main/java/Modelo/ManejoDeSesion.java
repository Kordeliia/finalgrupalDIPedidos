package Modelo;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class ManejoDeSesion {
    private static final SessionFactory mf;
    static {
        try {
        	//la SessionFactory que se ha declarado se inicializa
        	//se inicializa con el valor de nuestro archivo hibernate.cfg.xml
        	//también tendremos que vincularlo con nuestra clase de mapeo que esta en Modelo.Pedido
        	//el mapeo de hibernate.cfg.xml está en Vista/Pedido.hbn.xml 
            mf = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Modelo.Pedido.class).buildSessionFactory();
        }catch(Throwable th){
        	//se capturan posibles excepciones, de ahí que se encuentre en una estructura try-catch
            System.err.println("Ha ocurriedo un error mientras se creaba SessionFactory"+th);
            throw new ExceptionInInitializerError(th);}}
    public static SessionFactory getmf(){
    	//devuelve la sesion mf para poder trabajar con la conexion en PedidoDAO.java
        return mf;}}
