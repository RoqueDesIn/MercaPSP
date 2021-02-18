package Common;

public abstract class Constantes{
	//estados
	public static final String BD = "mercado";
	public static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	public static final  String URL = "jdbc:mysql://localhost:3306/"+ BD + "?allowPublicKeyRetrieval=true&useSSL=false";
	//public static final String USUARIO = "root";
	// public static final String CLAVE = "AdminMysql1211$";
	public static final String USUARIO = "usuario_hibernate";
	public static final String CLAVE = "1234";

	public static final String SQLCREATE = "src/Scripts/scriptBBDD.sql";
	public static final String SQLCARGA = "src/Scripts/rellenaDummy.sql";


	//comportamientos
	public Constantes() {}

}
