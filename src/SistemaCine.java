import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class SistemaCine {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		// Arrays creation
		
		// Users' parallel lists
		String[] nombres = new String[1000];
		String[] apellidos = new String[1000];
		String[] ruts = new String[1000];
		String[] contrasenias = new String [1000];
		String[] estados = new String [1000];
		double[] saldos = new double[1000];
		
		// Movies' parallel lists
		String[] nombresDePeliculas = new String[1000];
		String[] tiposDePeliculas = new String[1000];
		double[] recaudaciones = new double[1000];
		String[] horarios = new String [1000];
		
		// Inventory and seats' matrixes
		String[][] entradasCompradas = new String[1000][1000];
		String[][][] asientos = new String[10][30][6];
		
		// Text files reading
		int cantidadUsuarios = leerArchivoClientes(nombres, apellidos, ruts, contrasenias, saldos);
		leerArchivoStatus(ruts, estados);
		int cantidadPeliculas = leerArchivoPeliculas(nombresDePeliculas, tiposDePeliculas, recaudaciones, horarios);
		
		// Login
		iniciarSesion(scan, nombres, apellidos, ruts, contrasenias, saldos, estados, nombresDePeliculas, tiposDePeliculas,
				recaudaciones, horarios, entradasCompradas, asientos, cantidadUsuarios, cantidadPeliculas);
	}
	
	/**
	 * Reads the text file "peliculas" and stores its data into the corresponding lists.
	 * @param nombresDePeliculas The names of the movies.
	 * @param tiposDePeliculas If the movie is a premiere or not.
	 * @param recaudaciones The amount of money the movie has generated.
	 * @param horarios The avalaible times for a movie.
	 * @return The amount of movies the text file has.
	 * @throws IOException
	 */
	private static int leerArchivoPeliculas(String[] nombresDePeliculas, String[] tiposDePeliculas,
			double[] recaudaciones, String[] horarios) throws IOException {
		Scanner scan = new Scanner(new File("peliculas.txt"));
		int i = 0;
		while (scan.hasNextLine()) {
			String[] parts = scan.nextLine().split(",", 4);		// Only splits 4 parts
			nombresDePeliculas[i] = parts[0];
			tiposDePeliculas[i] = parts[1];
			recaudaciones[i] = Double.parseDouble(parts[2]);
			horarios[i] = parts[3];
			i++;
		}
		return i;
	}

	/**
	 * Reads the text file "status" and checks if a user is enabled or not.
	 * @param ruts The RUT of the users.
	 * @param estados The status of a user.
	 * @throws IOException
	 */
	private static void leerArchivoStatus(String[] ruts, String[] estados) throws IOException {
		Scanner scan = new Scanner(new File("status.txt"));
		while (scan.hasNextLine()) {
			String[] partes = scan.nextLine().split(",");
			String rut = cambiarFormato(partes[0]);
			int i = buscarIndice(rut, ruts);
			estados[i] = partes[1];
		}
	}

	/**
	 * Reads the text file "clientes" and stores its data into the corresponding lists.
	 * @param nombres The first names of the users.
	 * @param apellidos The last names of the users.
	 * @param ruts The RUT of the users.
	 * @param contrasenias The passwords of the users.
	 * @param saldos The avalaible money a user has.
	 * @return The amount of registered users in the system.
	 * @throws IOException
	 */
	private static int leerArchivoClientes(String[] nombres, String[] apellidos, String[] ruts,
			String[] contrasenias, double[] saldos) throws IOException {
		Scanner scan = new Scanner(new File("clientes.txt"));
		int i = 0;
		while (scan.hasNextLine()) {
			String[] partes = scan.nextLine().split(",");
			nombres[i] = partes[0];
			apellidos[i] = partes[1];
			ruts[i] = cambiarFormato(partes[2]);
			contrasenias[i] = partes[3];
			saldos[i] = Double.parseDouble(partes[4]);
			i++;
		}
		return i;
	}
	
	public static void rellenarAsientos(String[][][] asientos) {
		// First we fill the entire matrix with "disponible"
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 30; j++) {
				for (int k = 0; k < 6; k++) {
					asientos[i][j][k] = "disponible";
				}
			}
		}
		// Now we fill the upper corners with "no disponible"
		for (int k = 0; k < 6; k++) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {		// Upper left corner
					asientos[i][j][k] = "no disponible";
				}
				for (int j = 25; j < 30; j++) {		// Upper right corner
					asientos[i][j][k] = "no disponible";
				}
			}
		}
	}

	private static void iniciarSesion(Scanner scan, String[] nombres, String[] apellidos, String[] ruts,
			String[] contrasenias, double[] saldos, String[] estados, String[] nombresDePeliculas,
			String[] tiposDePeliculas, double[] recaudaciones, String[] horarios, String[][] entradasCompradas,
			String[][][] asientos, int cantidadUsuarios, int cantidadPeliculas) {
		while(true) {
			System.out.println("\n**********************************************************");
			System.out.println("INICIAR SESION");
			System.out.println("**********************************************************");
			System.out.print("\nRUT:");
			String rutInput = scan.nextLine();
			rutInput = cambiarFormato(rutInput);
			//valida si el usuario esta registrado
			Boolean validarUsuario=buscarRut(rutInput,ruts,cantidadUsuarios);
			
			while(validarUsuario==false) {
					desplegarMenuErrorDeIngreso();	
					System.out.print("\nOPCION: ");
					int op = Integer.parseInt(scan.nextLine());
					//INTENTAR NUEVAMENTE
					if (op == 1) {
						System.out.println("\n**********************************************************");
						System.out.println("INTENTA INICIAR SESION NUEVAMENTE");
						System.out.println("**********************************************************");
						System.out.print("\nRUT:");
						rutInput = scan.nextLine();
						rutInput = cambiarFormato(rutInput);
						validarUsuario=buscarRut(rutInput,ruts,cantidadUsuarios);
					}
					else {
					//REGISTRAR NUEVO USUARIO
						if (op == 2) {
							System.out.println("\n**********************************************************");
							System.out.println("REGISTRAR NUEVO USUARIO (RUT: "+rutInput+")");
							System.out.println("**********************************************************");
							System.out.println("\nNOMBRE: ");
							String nombreInput = scan.nextLine();
							System.out.println("\nAPELLIDO:");
							String apellidoInput = scan.nextLine();
							System.out.print("\nCONTRASEÑA:");
							String claveInput = scan.nextLine();
							registrarNuevoUsuario(rutInput, ruts, nombreInput, nombres, apellidoInput, apellidos, 
									claveInput, contrasenias, cantidadUsuarios);
							cantidadUsuarios++;
							
							System.out.println("\n**********************************************************");
							System.out.println("INICIAR SESION");
							System.out.println("**********************************************************");
							System.out.print("\nRUT:");
							rutInput = scan.nextLine();
							rutInput = cambiarFormato(rutInput);
							validarUsuario=buscarRut(rutInput,ruts,cantidadUsuarios);
						} 
						else {
					//CERRAR SISTEMA
							if (op ==3) {
								cerrarSistema(scan,nombres,apellidos,ruts,contrasenias,saldos,estados,
								nombresDePeliculas,tiposDePeliculas,recaudaciones,horarios, cantidadUsuarios, cantidadPeliculas);
							} 
					//OPCION INVALIDA
							else {
								System.out.println("Opcion invalida.");
								continue;
							}
						}
					}
				}//fin del while
			
			//VERIFICACION DE LA CONTRASEÑA
			System.out.print("\nCONTRASEÑA:");
			String claveInput = scan.nextLine();
			Boolean ingresoDeClave=verificacionDeClave(scan,rutInput,ruts,contrasenias,claveInput,cantidadUsuarios);
			
			//CONTRASEÑA INCORRECTA
			while(ingresoDeClave==false) {
				desplegarMenuErrorClave();
				System.out.println("\nOPCION: ");
				int op = Integer.parseInt(scan.nextLine());
				//REINTENTAR 
				if (op == 1) {
					System.out.println("\n**********************************************************");
					System.out.println("INTENTA CONTRASEÑA NUEVAMENTE");
					System.out.println("**********************************************************");
					System.out.println("\nCONTRASEÑA:");
					claveInput = scan.nextLine();
					ingresoDeClave=verificacionDeClave(scan,rutInput,ruts,contrasenias,claveInput,cantidadUsuarios);
				}else
				//CERRAR SISTEMA
				if(op==2) {
					cerrarSistema(scan, nombres, apellidos, ruts, contrasenias, saldos, estados, 
					nombresDePeliculas, tiposDePeliculas, recaudaciones, horarios, cantidadUsuarios, cantidadPeliculas);
									
				}
			    // OPCION INVALIDA
				else {
					System.out.println("Opcion invalida.");
					continue;
				}
			}
			
			//CONTRASEÑA CORRECTA
			//MENU CLIENTE Y ADMIN
			while(ingresoDeClave) {
				//MENU ADMIN
				if(rutInput.equals("ADMIN")&& claveInput.equals("ADMIN")) {
					desplegarMenuAdmin();
					System.out.println("\nOPCION: ");
					int op=Integer.parseInt(scan.nextLine());
					switch (op) {
					case 1: {
						//TAQUILLA
						
					}
					case 2: {
						//INFORMACION DE CLIENTE
											
					}
					case 3: {
						//INICIAR OTRA SESION
						
					}
					case 4: {
						//CERRAR SISTEMA
						
					}
					default:
						//OPCION INVALIDA
						System.out.println("\nOpcion invalida.");
						continue;
					}
									
				}
				//MENU CLIENTE
				else {
					desplegarMenuCliente();
					System.out.println("\nOPCION: ");
					int op=Integer.parseInt(scan.nextLine());
					switch (op) {
					case 1: {
						//COMPRAR ENTRADA

						System.out.print("Ingrese el nombre de la pelicula: ");
						String nombrePelicula=scan.nextLine();
						String [] horariosPeli=desplegarHorario(scan,nombrePelicula,nombresDePeliculas,
											   horarios,cantidadPeliculas);
						System.out.print("Ingrese el numero de la funcion: ");
						int numFuncion=scan.nextInt();
						desplegarAsientos(asientos,horariosPeli,numFuncion);
						System.out.print("Ingrese el numero de entradas: ");
						int numAsientos=scan.nextInt();
						for(int i=0;i<numAsientos;i++) {
							System.out.print("Seleccione un asiento para comprar: ");
							String asientoSeleccionado=scan.nextLine();
							//FALTA
						}
						calcularTotalCompra(asientos);
						//CONFIRMACION 
						System.out.println("DESEA CONFIRMAR LA COMPRA? SI[1]NO[0]: ");
						int opcion= Integer.parseInt(scan.nextLine());
						if(opcion==1) {
							System.out.println("[1] RECARGAR ");
							System.out.println("[2] CANCELAR");
							System.out.println("Ingrese una opcion: ");
							int op2=scan.nextInt();
							switch (op2) {
							case 1: {
								//RECARGAR SALDO 
								
							}
							case 2:{
								//CANCELAR
								System.out.println("CANCELADO . . .");
								continue;
							}
						}
						}else
						if(opcion==0) {
							System.out.println("COMPRA NO REALIZADA . . .");
						    continue;
						}
					}
					case 2:{
						//INFORMACION DE USUARIO
						
						
					}
					case 3:{
						//DEVOLUCION DE ENTRADA
						devolucionEntradas();
					}
					case 4:{
						//CARTELERA
						
					}
					case 5:{
						//INICIAR OTRA SESION
						iniciarSesion(scan, nombres, apellidos, ruts, contrasenias, saldos,
								 estados, nombresDePeliculas, tiposDePeliculas, recaudaciones, 
								 horarios, entradasCompradas, asientos, cantidadUsuarios, cantidadPeliculas);
					}
					case 6:{
						//CERRAR SISTEMA
						
					}
					default:{
						//OPCION INVALIDA
						System.out.println("\nOpcion invalida.");
						 continue;						 
						 
						}
					}
				}
			}
		}
	}

	private static void calcularTotalCompra(String[][][] asientos) {
		// TODO Auto-generated method stub
		
	}

	private static void desplegarAsientos(String[][][] asientos, String[] horariosPeli, int numFuncion) {
		//Asientos disponibles
		
		
	}

	private static String [] desplegarHorario(Scanner scan, String nombrePelicula, String[] nombresDePeliculas, String[] horarios,
			int cantidadPeliculas) {
		//Buscar pelicula
		int indicePelicula=buscarIndice(nombrePelicula, horarios);
		String horariosDisponibles=horarios[indicePelicula];
		String [] partes= horariosDisponibles.split("/");
		int i;
		//desplegar salas
		for(i=0;i<partes.length;i++) {
			String [] partes2= partes[i].split(",");
			String sala=partes2[0];
			String horario=partes2[1];
			if(horario.equals("M")) {
				System.out.println("Funcion ["+(i+1)+"] en la sala numero "+sala+" en el horario de la Mañana");
			}else if(horario.equals("T")){
				System.out.println("Funcion ["+(i+1)+"] en la sala numero "+sala+" en el horario de la Tarde ");
			}
		}
		return partes;
		
	}

	private static void devolucionEntradas() {
		// TODO Auto-generated method stub
		
	}

	private static void desplegarMenuCliente() {
		
			System.out.println("\n**********************************************************");
			System.out.println("MENU CLIENTE");
			System.out.println("**********************************************************\n");
			System.out.println("[1] COMPRAR ENTRADA");
			System.out.println("[2] INFORMACION DE USUARIO");
			System.out.println("[3] DEVOLUCION DE ENTRADA");
			System.out.println("[4] CARTELERA");
			System.out.println("[5] INICIAR OTRA SESION");
			System.out.println("[6] CERRAR SISTEMA");	
		}

	private static void desplegarMenuAdmin() {
			System.out.println("\n**********************************************************");
			System.out.println("MENU ADMIN");
			System.out.println("**********************************************************\n");
			System.out.println("[1] TAQUILLA");
			System.out.println("[2] INFORMACION DE CLIENTE");
			System.out.println("[3] INICIAR OTRA SESION");	
			System.out.println("[4] CERRAR SISTEMA");
		
	}

	private static Boolean verificacionDeClave(Scanner scan, String rutInput, String[] ruts, String[] contrasenias,
			String claveInput, int cantidadUsuarios) {
		int i;
		if(cantidadUsuarios!=0) {
			for(i=0;i<cantidadUsuarios;i++) {
				if(contrasenias[i].equals(claveInput)) {
					return true;
				}
			}
		}else 
			if(rutInput.equals("ADMIN")&&claveInput.equals("ADMIN")) {
				return true;
			}
		
		return false;
	}

	private static void cerrarSistema(Scanner scan, String[] nombres, String[] apellidos, String[] ruts,
			String[] contrasenias, double[] saldos, String[] estados, String[] nombresDePeliculas,
			String[] tiposDePeliculas, double[] recaudaciones, String[] horarios, int cantidadUsuarios,
			int cantidadPeliculas) {
		// TODO Auto-generated method stub
		
	}

	private static void registrarNuevoUsuario(String rutInput, String[] ruts, String nombreInput, String[] nombres,
			String apellidoInput, String[] apellidos, String claveInput, String[] contrasenias, int cantidadUsuarios) {
		
			ruts[cantidadUsuarios]=rutInput;
			nombres[cantidadUsuarios]=nombreInput;
			apellidos[cantidadUsuarios] = apellidoInput;
			contrasenias[cantidadUsuarios] = claveInput;
			System.out.print("\nRegistro exitoso.");
		
	}

	private static void desplegarMenuErrorDeIngreso() {
			System.out.println("\n**********************************************************");
			System.out.println("ERROR: USUARIO NO REGISTRADO");
			System.out.println("**********************************************************\n");
			System.out.println("[1] INTENTAR INICIAR SESION NUEVAMENTE");
			System.out.println("[2] REGISTRAR NUEVO USUARIO");
			System.out.println("[3] CERRAR SISTEMA");
		}

	private static void desplegarMenuErrorClave() {
			System.out.println("\n**********************************************************");
			System.out.println("ERROR: CLAVE INCORRECTA");
			System.out.println("**********************************************************\n");
			System.out.println("[1] INTENTAR CLAVE NUEVAMENTE");
			System.out.println("[2] CERRAR SISTEMA");
	}
	private static Boolean buscarRut(String rutInput, String[] ruts, int cantidadUsuarios) {
		int index_rut = buscarIndice(rutInput, ruts);
		//RUT ENCONTRADO
		if ((index_rut != -1) || (rutInput.equals("ADMIN"))) {
			return true;
		} 
		//RUT NO ENCONTRADO
		else {
			return false;
		}
	}

	/**
	 * Search for the specified item in a list.
	 * @param valor The item to look for.
	 * @param lista The list where the item will be searched.
	 * @return The index of item in the list. If not found, returns a -1.
	 */
	private static int buscarIndice(String valor, String[] lista) {
		int i = 0;
		while (i < lista.length && lista[i] != null) {
			if (lista[i].equals(valor)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Ignores points and hyphens in a RUT, and also transforms any 'k' to lowercase.
	 * @param rut The RUT that will be changed.
	 * @return The transformed RUT.
	 */
	private static String cambiarFormato(String rut) {
		String[] chars = rut.split("");
		String nuevoRut = "";
		for (int i = 0; i < rut.length(); i++) {
			if (chars[i].equals("-") || chars[i].equals(".")) {
				continue;
			}
			else if (chars[i].equalsIgnoreCase("k")) {
				nuevoRut += "k";
			}
			else {
				nuevoRut += chars[i];
			}
		}
		return nuevoRut;
	}
}
