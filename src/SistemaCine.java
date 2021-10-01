import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

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
		double[] recaudacionesManana = new double[1000];
		double[] recaudacionesTarde = new double[1000];
		
		// Inventory and seats' matrixes
		String[][] entradasCompradas = new String[1000][1000];
		String[][][] asientos = new String[10][30][6];
		rellenarAsientos(asientos);
		
		// Text files reading
		int cantidadUsuarios = leerArchivoClientes(nombres, apellidos, ruts, contrasenias, saldos);
		leerArchivoStatus(ruts, estados);
		int cantidadPeliculas = leerArchivoPeliculas(nombresDePeliculas, tiposDePeliculas, recaudaciones, horarios);
		
		// Login
		iniciarSesion(scan, nombres, apellidos, ruts, contrasenias, saldos, estados, nombresDePeliculas, tiposDePeliculas,
				recaudaciones, recaudacionesManana, recaudacionesTarde, horarios, entradasCompradas, asientos, cantidadUsuarios, cantidadPeliculas);
	}
	
	/**
	 * Reads the text file "peliculas" and stores its data into the corresponding lists.
	 * @param nombresDePeliculas The names of the movies.
	 * @param tiposDePeliculas If the movies are a premiere or not.
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
	 * @param estados The statuses of the users.
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
	 * @param saldos The avalaible money of the users.
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
	
	/**
	 * Fills the cubic matrix with strings for later use.
	 * @param asientos The cubic matrix.
	 */
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
	
	/**
	 * Prints a string several times.
	 * @param str The string to be printed.
	 * @param n The number of prints.
	 */
	private static void printlnRepeat(String str, int n) {
		for (int i = 0; i < n; i++) {
			System.out.print(str);
		}
		System.out.println();
	}

	/**
	 * 
	 * @param scan
	 * @param nombres
	 * @param apellidos
	 * @param ruts
	 * @param contrasenias
	 * @param saldos
	 * @param estados
	 * @param nombresDePeliculas
	 * @param tiposDePeliculas
	 * @param recaudaciones
	 * @param recaudacionesManana
	 * @param recaudacionesTarde
	 * @param horarios
	 * @param entradasCompradas
	 * @param asientos
	 * @param cantidadUsuarios
	 * @param cantidadPeliculas
	 * @throws IOException
	 */
	private static void iniciarSesion(Scanner scan, String[] nombres, String[] apellidos, String[] ruts,
			String[] contrasenias, double[] saldos, String[] estados, String[] nombresDePeliculas,
			String[] tiposDePeliculas, double[] recaudaciones, double[] recaudacionesManana, double[] recaudacionesTarde,
			String[] horarios, String[][] entradasCompradas, String[][][] asientos, int cantidadUsuarios, int cantidadPeliculas) throws IOException {
		while (true) {
			printlnRepeat("*", 30);
			System.out.println("INICIAR SESION");
			printlnRepeat("*", 30);
			System.out.print("\nIngrese RUT: ");
			String rutInput = cambiarFormato(scan.nextLine());
			int indiceRut = buscarIndice(rutInput, ruts);	// Checks if the user is registered
			
			while (indiceRut == -1 && !rutInput.equals("ADMIN")) {
				System.out.println();
				printlnRepeat("*", 30);
				System.out.println("ERROR: USUARIO NO REGISTRADO");
				printlnRepeat("*", 30);
				System.out.println("\n[1] INICIAR SESION NUEVAMENTE");
				System.out.println("[2] REGISTRAR NUEVO USUARIO");
				System.out.println("[3] CERRAR SISTEMA");
				System.out.print("\nOPCION: ");
				String op = scan.nextLine();
				if (op.equals("1")) {
					// Try again
					printlnRepeat("*", 30);
					System.out.println("INTENTA INICIAR SESION NUEVAMENTE");
					printlnRepeat("*", 30);
					System.out.print("\nRUT: ");
					
					rutInput = cambiarFormato(scan.nextLine());
					indiceRut = buscarIndice(rutInput, ruts);
				}
				else if (op.equals("2")) {
					// Register new user
					printlnRepeat("*", 30);
					System.out.println("REGISTRAR NUEVO USUARIO (RUT: " + rutInput + ")");
					printlnRepeat("*", 30);
					System.out.print("\nNOMBRE: ");
					String nombreInput = scan.nextLine();
					System.out.print("\nAPELLIDO: ");
					String apellidoInput = scan.nextLine();
					System.out.print("\nCONTRASEŅA: ");
					String claveInput = scan.nextLine();
					
					registrarNuevoUsuario(rutInput, ruts, nombreInput, nombres, apellidoInput, apellidos, 
							claveInput, contrasenias, cantidadUsuarios);
					cantidadUsuarios++;
					break;
				} 
				else if (op.equals("3")) {
					// Close system
					cerrarSistema(nombres,apellidos,ruts,contrasenias,saldos, nombresDePeliculas,
							tiposDePeliculas,recaudaciones,horarios, cantidadUsuarios, cantidadPeliculas);
				} 
				else {
					// Invalid option
					System.out.println("OPCION INVALIDA");
				}
			}
			
			//VERIFICACION DE LA CONTRASEŅA
			System.out.print("\nContraseņa: ");
			String claveInput = scan.nextLine();
			boolean ingresoDeClave = verificacionDeClave(rutInput, ruts, claveInput, contrasenias);
			
			//CONTRASEŅA INCORRECTA
			while (!ingresoDeClave) {
				printlnRepeat("*", 30);
				System.out.println("ERROR: CLAVE INCORRECTA");
				printlnRepeat("*", 30);
				System.out.println("\n[1] INGRESAR CLAVE NUEVAMENTE");
				System.out.println("[2] CERRAR SISTEMA");
				System.out.print("\nOPCION: ");
				String op = scan.nextLine();
				 
				if (op.equals("1")) {
					//REINTENTAR
					printlnRepeat("*", 30);
					System.out.println("INTENTA CONTRASEŅA NUEVAMENTE");
					printlnRepeat("*", 30);
					System.out.print("\nCONTRASEŅA: ");
					claveInput = scan.nextLine();
					ingresoDeClave = verificacionDeClave(rutInput, ruts,claveInput, contrasenias);
				}
				else if (op.equals("2")) {
					//CERRAR SISTEMA
					cerrarSistema(nombres, apellidos, ruts, contrasenias, saldos, nombresDePeliculas, 
							tiposDePeliculas, recaudaciones, horarios, cantidadUsuarios, cantidadPeliculas);				
				}
				else {
					// OPCION INVALIDA
					System.out.println("OPCION INVALIDA");
				}
			}
			
			// Correct password
			// User menu and Admin menu
			
			if (rutInput.equals("ADMIN") && claveInput.equals("ADMIN")) {
				while (true) {
					// Admin's menu
					System.out.println();
					printlnRepeat("*", 30);
					System.out.println("MENU ADMIN");
					printlnRepeat("*", 30);
					System.out.println("\n[1] TAQUILLA");
					System.out.println("[2] INFORMACION DE CLIENTE");
					System.out.println("[3] INICIAR OTRA SESION");	
					System.out.println("[4] CERRAR SISTEMA");
					System.out.println("\nOPCION: ");

					String op = scan.nextLine();
					switch (op) {
					case "1":
						//TAQUILLA
						taquilla(nombresDePeliculas, recaudaciones, recaudacionesManana, 
								recaudacionesTarde, cantidadPeliculas);
						break;
					case "2": 
						//INFORMACION DE CLIENTE
						informacionCliente(scan, nombres, apellidos, saldos, entradasCompradas,
								nombresDePeliculas, horarios, cantidadUsuarios, cantidadPeliculas,ruts);
						break;
					case "3":
						//INICIAR OTRA SESION
						iniciarSesion(scan, nombres, apellidos, ruts, contrasenias, 
								saldos, estados, nombresDePeliculas, tiposDePeliculas, 
								recaudaciones, recaudacionesManana, recaudacionesTarde, 
								horarios, entradasCompradas, asientos, cantidadUsuarios, 
								cantidadPeliculas);
						break;
					case "4":
						//CERRAR SISTEMA
						cerrarSistema(nombres, apellidos, ruts, contrasenias, saldos, nombresDePeliculas,
								tiposDePeliculas, recaudacionesTarde, horarios, cantidadUsuarios, cantidadPeliculas);
						break;
					default:
						//OPCION INVALIDA
						System.out.println("\nOPCION INVALIDA\n");
						printlnRepeat("*", 30);
						System.out.println("MENU ADMIN");
						printlnRepeat("*", 30);
						System.out.println("1] TAQUILLA");
						System.out.println("[2] INFORMACION DE CLIENTE");
						System.out.println("[3] INICIAR OTRA SESION");	
						System.out.println("[4] CERRAR SISTEMA");
						System.out.println("\nOPCION: ");

						op = scan.nextLine();
						continue;
					}
					break;
				}
			}
			//MENU CLIENTE
			else {
				desplegarMenuCliente();
				System.out.println("\nOPCION: ");
				String op = scan.nextLine();
				while (true) {
					switch (op) {
					case "1": 
						//COMPRAR ENTRADA
						int indicePeli = 0;
						while (true) {
							System.out.print("Ingrese el nombre de la pelicula: ");
							String nombrePeli = scan.nextLine();
							indicePeli = buscarIndice(nombrePeli, nombresDePeliculas);
							if (indicePeli == -1) {
								System.out.println("No existe la pelicula. Intente nuevamente");
							}
							else {
								break;
							}
						}
						String[] horariosPeli = desplegarHorarios(indicePeli, horarios);
						String funcion;
						while (true) {
							System.out.print("Ingrese la funcion: ");
							funcion = scan.nextLine();
							if (buscarIndice(funcion, horariosPeli) == -1) {
								System.out.println("Funcion invalida. Intente nuevamente");
							}
							else {
								break;
							}
						}
						int k = obtenerKMatriz(funcion);	// We obtain the k value of the cubic matrix according to the selected function
						desplegarAsientos(asientos, funcion);
						System.out.print("Ingrese el numero de entradas: ");
						int cantAsientos = Integer.parseInt(scan.nextLine());
						String[] asientosSeleccionados = new String[cantAsientos];
						for(int v = 0; v < cantAsientos; v++) {
							while (true) {
								System.out.print("Seleccione un asiento para comprar: ");
								String asiento = scan.nextLine();
								int i = obtenerIAsiento(asiento);
								int j = Integer.parseInt(asiento.split("", 2)[1]) - 1;
								if (asientos[i][j][k].equals("disponible")) {
									if (asientos[i][j - 1][k].equals("disponible") || asientos[i][j - 1][k].equals("no disponible") 
											&& asientos[i][j + 1][k].equals("disponible") || asientos[i][j + 1][k].equals("no disponible")) {
										asientosSeleccionados[v] = asiento.toUpperCase();
										break;
									}
									else {
										System.out.println("No cumple distanciamiento social. Elija otro asiento");
									}
								}
								else {
									System.out.println("Asiento ocupado. Elija otro asiento");
								}
							}
						}
						double total=calcularTotalCompra(cantAsientos, rutInput, ruts, estados, indicePeli, tiposDePeliculas);
						//CONFIRMACION 
						
						
							System.out.println("DESEA CONFIRMAR LA COMPRA? SI[1] NO[0]: ");
							String opcion = scan.nextLine();
							if (opcion.equals("1")) {
								//Compra realizada
								if(saldos[indiceRut] >= total) {
									saldos[indiceRut] -= total;
									agregarEntradasUsuario(entradasCompradas, indiceRut, indicePeli, funcion, asientosSeleccionados);
									agregarOcupacionAsientos(asientos, asientosSeleccionados, k);
									
									recaudaciones[indicePeli]+= total;
									if(k % 2 != 0) {
										recaudacionesManana[indicePeli]+=total;
									}else 
									if(k % 2 == 0) {
										recaudacionesTarde[indicePeli]+=total;
									}
									System.out.println("COMPRA EXITOSA ");
									break;
								}else {
									System.out.println("SALDO INVALIDO");
									break;
								}
								
							}else if(opcion.equals("0")) {
								//Compra no realizada
								System.out.println("[1] RECARGAR ");
								System.out.println("[2] CANCELAR");
								System.out.println("Ingrese una opcion: ");
								String op2 = scan.nextLine();
								switch (op2) {
								case "1":
									//RECARGAR SALDO 
									System.out.print("CANTIDAD: $");
									double monto = Double.parseDouble(scan.nextLine());
									saldos[indiceRut] += monto;
									break;
								case "2":
									//CANCELAR
									System.out.println("CANCELADO . . .");
									break;
								}	
							}else {
								System.out.println("OPCION NO VALIDA");
								continue;
							}
							break;	
					case "2":
						//INFORMACION DE USUARIO
						infomacionUsuario(rutInput,ruts,nombres,apellidos,
								saldos,nombresDePeliculas,entradasCompradas,
								cantidadUsuarios,cantidadPeliculas);
						break;					
					case "3":
						//DEVOLUCION DE ENTRADA
						String[] pelis = desplegarEntradas(indiceRut, entradasCompradas, cantidadPeliculas, nombresDePeliculas);
						int indicePelicula = 0; 
						while (true) {
							System.out.println("INGRESE PELICULA: ");
							String peli = scan.nextLine();
							indicePelicula = buscarIndice(peli, pelis);
							if (indicePelicula != -1) {
								break;
							}
							else {
								System.out.println("\nNOMBRE INVALIDO\n");
							}
						}
						// CALCULAR TOTAL
						int cantEntradas = entradasCompradas[indiceRut][indicePelicula].split("/")[0].split(",").length;
						String horario = entradasCompradas[indiceRut][indicePelicula].split("/")[1].split(",")[1]; 
						double reembolso = 0;
						if (tiposDePeliculas[indicePelicula].equals("estreno")) {
							reembolso = 5500 * cantEntradas * 0.8;
						}
						else {
							reembolso = 4000 * cantEntradas * 0.8;
						}
						if (horario.equalsIgnoreCase("m")) {
							recaudacionesManana[indicePelicula] -= reembolso; 
						}
						else {
							recaudacionesTarde[indicePelicula] -= reembolso;
						}
						recaudaciones[indicePelicula] -= reembolso;
						saldos[indiceRut] += reembolso;
						
						// DEVOLUCION
						entradasCompradas[indiceRut][indicePelicula] = null;
						break;
					case "4":
						//CARTELERA
						cartelera(nombresDePeliculas,horarios,cantidadPeliculas);
						break;
					case "5":
						//INICIAR OTRA SESION
						iniciarSesion(scan, nombres, apellidos, ruts, contrasenias, saldos,
								estados, nombresDePeliculas, tiposDePeliculas, recaudaciones, recaudacionesManana, 
								recaudacionesTarde, horarios, entradasCompradas, asientos, cantidadUsuarios, cantidadPeliculas);
						break;
					case "6":
						//CERRAR SISTEMA
						cerrarSistema(nombres, apellidos, ruts, contrasenias, saldos, nombresDePeliculas, tiposDePeliculas, recaudacionesTarde, horarios, cantidadUsuarios, cantidadPeliculas);
						break;
					default:
						//OPCION INVALIDA
						System.out.println("\nOPCION INVALIDA");
						desplegarMenuCliente();
						System.out.println("\nOPCION: ");
						op = scan.nextLine();
						continue;
					}
					break;
				}
			}
		}
	}
	
	private static void agregarOcupacionAsientos(String[][][] asientos, String[] asientosSeleccionados, int k) {
		for (int v = 0; v < asientosSeleccionados.length; v++) {
			int i = obtenerIAsiento(asientosSeleccionados[v]);
			int j = Integer.parseInt(asientosSeleccionados[v].split("", 2)[1]) - 1;
			asientos[i][j][k] = "ocupado";
		}
	}

	/**
	 * 
	 * @param infoEntradas 
	 * @param entradasCompradas
	 * @param indiceRut
	 * @param indicePeli
	 * @param funcion
	 * @param asientosSeleccionados
	 */
	private static void agregarEntradasUsuario(String[][] entradasCompradas, int indiceRut, int indicePeli, String funcion, 
			String[] asientosSeleccionados) {
		if (entradasCompradas[indiceRut][indicePeli] == null) {
			 entradasCompradas[indiceRut][indicePeli] = funcion.split("", 2)[0] + "," + funcion.split("", 2)[1].toUpperCase() + "/";
		}
		else {
			entradasCompradas[indiceRut][indicePeli] += ",";
		}
		int i;
		for (i = 0; i < asientosSeleccionados.length - 1; i++) {
			 entradasCompradas[indiceRut][indicePeli] += asientosSeleccionados[i] + ",";
		}
		entradasCompradas[indiceRut][indicePeli] += asientosSeleccionados[i];
	}

	/**
	 * 
	 * @param asiento
	 * @return
	 */
	private static int obtenerIAsiento(String asiento) {
		String letra = asiento.split("")[0].toUpperCase();
		int i = 0;
		switch (letra) {
		case "A":
			break;
		case "B":
			i = 1;
			break;
		case "C":
			i = 2;
			break;
		case "D":
			i = 3;
			break;
		case "E":
			i = 4;
			break;
		case "F":
			i = 5;
			break;
		case "G":
			i = 6;
			break;
		case "H":
			i = 7;
			break;
		case "I":
			i = 8;
			break;
		case "J":
			i = 9;
		}
		return i;
	}
	
	/**
	 * 
	 * @param scan
	 * @param nombres
	 * @param apellidos
	 * @param saldos
	 * @param entradasCompradas
	 * @param nombresDePeliculas
	 * @param horarios
	 * @param cantidadUsuarios
	 * @param cantidadPeliculas
	 * @param ruts 
	 */
	private static void informacionCliente(Scanner scan, String[] nombres, String[] apellidos, double[] saldos,
			String[][] entradasCompradas, String[] nombresDePeliculas, String[] horarios, int cantidadUsuarios, 
			int cantidadPeliculas, String[] ruts) {
		System.out.print("INGRESE EL RUT DEL CLIENTE: ");
		String rut=scan.nextLine();
		rut=cambiarFormato(rut);
		int indexRut=buscarIndice(rut, ruts);
		if(indexRut!=-1) {
			System.out.println("EL CLIENTE "+nombres[indexRut].toUpperCase()+" "+apellidos[indexRut].toUpperCase());
			System.out.println("CON SALDO: "+saldos[indexRut]);
			desplegarEntradas(indexRut,entradasCompradas,cantidadPeliculas,nombresDePeliculas);
		}
		else {
			System.out.println("***** CLIENTE NO REGISTRADO *****");
		}
	}

	/**
	 * 
	 * @param indiceRut
	 * @param entradasCompradas
	 * @param cantidadPeliculas
	 * @param nombresDePeliculas
	 */
	private static String[] desplegarEntradas(int indiceRut, String[][] entradasCompradas, int cantidadPeliculas, 
			String[] nombresDePeliculas) {
		String[] peliculas = new String[cantidadPeliculas];
		int iPeli = 0;
		for (int j = 0; j < cantidadPeliculas; j++) {
			if (entradasCompradas[indiceRut][j] != null) {
				String[] partes = entradasCompradas[indiceRut][j].split("/");
				String[] horario = partes[0].split(",");
				String[] asientos = partes[1].split(",");
				peliculas[iPeli] = nombresDePeliculas[j];
				iPeli++;
				System.out.println("\nPelicula: " + nombresDePeliculas[j]);
				System.out.println("Horario: " + horario[0] + horario[1]);
				System.out.println("Asientos:");
				for (int i = 0; i < asientos.length; i++) {
					System.out.println(asientos[i]);
				}
			}
		}
		System.out.println();
		return peliculas;
	}

	/**
	 * 
	 * @param nombresDePeliculas
	 * @param recaudaciones
	 * @param recaudacionesManana
	 * @param recaudacionesTarde
	 * @param cantidadPeliculas
	 */
	private static void taquilla(String[] nombresDePeliculas, double[] recaudaciones, double[] recaudacionesManana,
			double[] recaudacionesTarde, int cantidadPeliculas) {
		for(int i=0; i<cantidadPeliculas;i++) {
			System.out.println("Pelicula: "+nombresDePeliculas[i].toUpperCase());
			System.out.println("Monto recaudado total: "+recaudaciones[i]);
			System.out.println("Monto recaudado a lo largo del dia: "+(recaudacionesManana[i]+recaudacionesTarde[i]));
			System.out.println("Monto recaudado en la maņana: "+recaudacionesManana[i]);
			System.out.println("Monto recaudado en la tarde: "+recaudacionesTarde[i]);
			
		}
	}
	/**
	 * 
	 * @param nombresDePeliculas
	 * @param horarios
	 * @param cantidadPeliculas
	 */
	private static void cartelera(String[] nombresDePeliculas, String[] horarios, int cantidadPeliculas) {
		printlnRepeat("*", 30);
		System.out.println("PELICULAS EN CARTELERA");
		printlnRepeat("*", 30);
		for (int i = 0; i < cantidadPeliculas; i++) {
			String[] partes = horarios[i].split(",");
			System.out.println("\n" + nombresDePeliculas[i] + ". Numero de funciones: " + (partes.length / 2));
			int numFuncion = 1;
			for (int j = 0; j < partes.length; j += 2) {
				String sala = partes[j];
				String hora = partes[j + 1];
				if (hora.equalsIgnoreCase("m")) {
					System.out.println("Funcion [" + numFuncion + "] en la sala " + sala + ". Horario maņana");
				}
				else {
					System.out.println("Funcion [" + numFuncion + "] en la sala " + sala + ". Horario tarde");
				}
				numFuncion++;
			}
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @param rutInput
	 * @param ruts
	 * @param nombres
	 * @param apellidos
	 * @param saldos
	 * @param nombresDePeliculas
	 * @param entradasCompradas
	 * @param cantidadUsuarios
	 * @param cantidadPeliculas
	 */
	private static void infomacionUsuario(String rutInput, String[] ruts, String[] nombres, String[] apellidos, double[] saldos,
			String[] nombresDePeliculas, String[][] entradasCompradas, int cantidadUsuarios, int cantidadPeliculas) {
		int index=buscarIndice(rutInput, ruts);
		System.out.println("***** INFORMACION DEL USUARIO *****\n");
		System.out.println("Cliente: " + ruts[index]);
		System.out.println("Nombre: "+nombres[index].toUpperCase() + " " + apellidos[index].toUpperCase());
		System.out.println("Saldo: $" + saldos[index]);
		System.out.println("Entradas compradas: ");
		desplegarEntradas(index,entradasCompradas, cantidadPeliculas, nombresDePeliculas);
		
	}

	/**
	 * Calculates the amount of money the user has to pay.
	 * @param cantEntradas The amount of tickets the user bought.
	 * @param rut The RUT of the user who bought the tickets.
	 * @param ruts The RUT of the users in the system.
	 * @param estados The statuses of the users.
	 * @param indicePeli The index of the selected movie.
	 * @param tiposPeli If the movies are a premiere or not.
	 * @return The total amount of money the user has to pay.
	 */
	private static double calcularTotalCompra(int cantEntradas, String rut, String[] ruts, 
			String[] estados, int indicePeli, String[] tiposPeli) {
		double total = 0;
		int iRut = buscarIndice(rut, ruts);
		String status = estados[iRut];
		String tipoPeli = tiposPeli[indicePeli];
		if (tipoPeli.equalsIgnoreCase("estreno")) {
			total += 5500 * cantEntradas;
		}
		else {
			total += 4000 * cantEntradas;
		}
		if (status.equalsIgnoreCase("habilitado")) {
			total -= total * 0.15;
			return total;
		}
		return total;
	}
	
	/**
	 * 
	 * @param funcion
	 * @return
	 */
	private static int obtenerKMatriz(String funcion) {
		int k = 0;
		switch (funcion) {
		case "1m":
		case "1M":
			break;
		case "1t":
		case "1T":
			k = 1;
			break;
		case "2m":
		case "2M":
			k = 2;
			break;
		case "2t":
		case "2T":
			k = 3;
			break;
		case "3m":
		case "3M":
			k = 4;
			break;
		case "3t":
		case "3T":
			k = 5;
		}
		return k;
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	private static String obtenerLetra(int i) {
		String letra = "A";
		switch (i) {
		case 0:
			break;
		case 1:
			letra = "B";
			break;
		case 2:
			letra = "C";
			break;
		case 3:
			letra = "D";
			break;
		case 4:
			letra = "E";
			break;
		case 5:
			letra = "F";
			break;
		case 6:
			letra = "G";
			break;
		case 7:
			letra = "H";
			break;
		case 8:
			letra = "I";
			break;
		case 9:
			letra = "J";
		}
		return letra;
	}

	/**
	 * 
	 * @param asientos
	 * @param funcion
	 */
	private static void desplegarAsientos(String[][][] asientos, String funcion) {
		int k = obtenerKMatriz(funcion);
		for (int i = 0; i < 10; i++) {
			String letra = obtenerLetra(i);
			for (int j = 0; j < 30; j++) {
				if (!asientos[i][j][k].equals("no disponible")) {
					if (asientos[i][j][k].equals("disponible")) {
						System.out.print(letra + (j + 1) + " (disp) ");
					}
					else {
						System.out.print(letra + (j + 1) + " (ocup) ");
					}
				}
			}
			System.out.println();
		}
	}

	/**
	 * Prints every avalaible schedule for a movie.
	 * @param indicePeli The index of the movie.
	 * @param horarios The schedules for the movies.
	 * @return A String array with the avalaible schedules for the specified movie.
	 */
	private static String [] desplegarHorarios(int indicePeli, String[] horarios) {
		String[] partes = horarios[indicePeli].split(",");
		String[] funciones = new String[partes.length / 2];
		int j = 0;
		for (int i = 0; i < partes.length; i += 2) {
			String horario = partes[i] + partes[i + 1];
			System.out.println(horario);
			funciones[j] = horario;
			j++;
		}
		return funciones;
	}

	/**
	 * 
	 */
	private static void desplegarMenuCliente() {
		System.out.println();
		printlnRepeat("*", 30);
		System.out.println("MENU CLIENTE");
		printlnRepeat("*", 30);
		System.out.println("\n[1] COMPRAR ENTRADA");
		System.out.println("[2] INFORMACION DE USUARIO");
		System.out.println("[3] DEVOLUCION DE ENTRADA");
		System.out.println("[4] CARTELERA");
		System.out.println("[5] INICIAR OTRA SESION");
		System.out.println("[6] CERRAR SISTEMA");	
	}

	/**
	 * 
	 * @param rutInput
	 * @param ruts
	 * @param clave
	 * @param contrasenias
	 * @return
	 */
	private static boolean verificacionDeClave(String rutInput, String[] ruts, String clave, String[] contrasenias) {
		if (rutInput.equals("ADMIN")) {
			if (clave.equals("ADMIN")) {
				return true;
			}
		}
		else {
			int i = buscarIndice(rutInput, ruts);
			if (contrasenias[i].equals(clave)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Writes to the text files "clientes.txt" and "peliculas.txt" with the updated information.
	 * @param nombres The first names of the users.
	 * @param apellidos The last names of the users.
	 * @param ruts The RUT of the users.
	 * @param contrasenias The passwords of the users.
	 * @param saldos The amount of money users have.
	 * @param nombresDePeliculas The names of the films.
	 * @param tiposDePeliculas If the films are a premiere or not.
	 * @param recaudaciones The total amount of money films have generated.
	 * @param horarios The avalaible times for the films.
	 * @param cantidadUsuarios The amount of registered users in the system.
	 * @param cantidadPeliculas The amount of films in the system.
	 * @throws IOException
	 */
	private static void cerrarSistema(String[] nombres, String[] apellidos, String[] ruts,
			String[] contrasenias, double[] saldos, String[] nombresDePeliculas, String[] tiposDePeliculas, 
			double[] recaudaciones, String[] horarios, int cantidadUsuarios, int cantidadPeliculas) throws IOException {
		// "clientes.txt" text file writing
		String contenidoClientes = "";
		for (int i = 0; i < cantidadUsuarios; i++) {
			String nombre = nombres[i];
			String apellido = apellidos[i];
			String rut = ruts[i];
			String contrasenia = contrasenias[i];
			double saldo = saldos[i];
			contenidoClientes += nombre + "," + apellido + "," + rut + "," + contrasenia + "," + saldo + "\n";
		}
		FileWriter writerClientes = new FileWriter("clientes.txt");
		writerClientes.write(contenidoClientes);
		writerClientes.close();
		
		// "peliculas.txt" text file writing
		String contenidoPeliculas = "";
		for (int i = 0; i < cantidadPeliculas; i++) {
			String nombrePeli = nombresDePeliculas[i];
			String tipoPeli = tiposDePeliculas[i];
			double recaudacion = recaudaciones[i];
			String horario = horarios[i];
			contenidoPeliculas += nombrePeli + "," + tipoPeli + "," + recaudacion + "," + horario + "\n";
		}
		FileWriter writerPeliculas = new FileWriter("peliculas.txt");
		writerPeliculas.write(contenidoPeliculas);
		writerPeliculas.close();
	}

	/**
	 * 
	 * @param rutInput
	 * @param ruts
	 * @param nombreInput
	 * @param nombres
	 * @param apellidoInput
	 * @param apellidos
	 * @param claveInput
	 * @param contrasenias
	 * @param cantUsuarios
	 */
	private static void registrarNuevoUsuario(String rutInput, String[] ruts, String nombreInput, 
			String[] nombres, String apellidoInput, String[] apellidos, String claveInput, 
			String[] contrasenias, int cantUsuarios) {
		ruts[cantUsuarios] = rutInput;
		nombres[cantUsuarios] = nombreInput;
		apellidos[cantUsuarios] = apellidoInput;
		contrasenias[cantUsuarios] = claveInput;
		System.out.println("\nRegistro exitoso!");
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
			if (lista[i].equalsIgnoreCase(valor)) {
				return i;
			}
			i++;
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
