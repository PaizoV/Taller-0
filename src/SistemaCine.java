import java.util.Scanner;

public class SistemaCine {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		// Creacion de arreglos
		
		// Listas parelelas de clientes
		String[] nombres = new String[1000];
		String[] apellidos = new String[1000];
		String[] ruts = new String[1000];
		String[] contrasenias = new String [1000];
		String[] estados = new String [1000];
		double[] saldos = new double[1000];
		
		// Listas paralelas de peliculas
		String[] nombresDePeliculas = new String[1000];
		String[] tiposDePeliculas = new String[1000];
		double[] recaudaciones = new double[1000];
		String[] horarios = new String [1000];
		
		// Matrices de inventarios y asientos
		String[][] entradasCompradas = new String[1000][1000];
		String[][][] asientos = new String[7][30][6];
		
		//Lecturas 
		int cantidadUsuarios=lecturaArchivoClientes(scan,nombres,apellidos,ruts,contrasenias,saldos);
		lecturaArchivoStatus(scan,ruts,estados);
		int cantidadPeliculas=lecturaArchivoPeliculas(scan,nombresDePeliculas,tiposDePeliculas,recaudaciones,horarios);
		
		//Iniciar sesion
		iniciarSesion(scan,nombres,apellidos,ruts,contrasenias,saldos,estados,nombresDePeliculas,tiposDePeliculas,
				recaudaciones,horarios,entradasCompradas,asientos, cantidadUsuarios, cantidadPeliculas);
	}

	private static int lecturaArchivoPeliculas(Scanner scan, String[] nombresDePeliculas, String[] tiposDePeliculas,
			double[] recaudaciones, String[] horarios) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static void lecturaArchivoStatus(Scanner scan, String[] ruts, String[] estados) {
		// TODO Auto-generated method stub
		
	}

	private static int lecturaArchivoClientes(Scanner scan, String[] nombres, String[] apellidos, String[] ruts,
			String[] contrasenias, double[] saldos) {
		// TODO Auto-generated method stub
		return 0;
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
			rutInput = cambiaFormato(rutInput);
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
						rutInput = cambiaFormato(rutInput);
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
							rutInput = cambiaFormato(rutInput);
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
		int indicePelicula=buscarIndice(nombrePelicula, horarios, cantidadPeliculas);
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
		int index_rut = buscarIndice(rutInput, ruts, cantidadUsuarios);
		//RUT ENCONTRADO
		if ((index_rut != -1) || (rutInput.equals("ADMIN"))) {
			return true;
		} 
		//RUT NO ENCONTRADO
		else {
			return false;
		}
	}

	private static int buscarIndice(String clave, String[] lista, int cantidad) {
		if (cantidad == 0) {
			return -1;	
		}
		for (int i = 0; i < cantidad; i++) {
			if (lista[i].equals(clave)) {
				return i;
			}
		}
		return -1;
	}

	private static String cambiaFormato(String rut) {
		String[] lista = rut.split("");
		String retorno = "";
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].equals(".") || lista[i].equals("-")) {	
			} 
			else {
				if (lista[i].equalsIgnoreCase("k")) {
					retorno += "k";
				} 
				else {
					retorno+=lista[i];
				}
			}
		}
		return retorno;
	}


}
