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
	}
}
