package server;

import java.net.*;
import client.*;
import server.data.*;
import server.database.*;
import server.mining.*;
import java.io.*;
import server.utility.*;
import java.util.*;

/**
 * 
 * @author Angelo, Simone, Antonio
 * Thread che evade la richiesta del client
 * @param s
 * @throws IOException
 *
 */
class ServerOneClient extends Thread {
	private Socket socket; // porta per il canale di connessione con il client
	private ClosedPatternArchive archive; // riferimento all'archivio di closed
											// pattern correttemente scoperti
	private BufferedReader in;
	private PrintWriter pout;

	
	
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		setDefaultUncaughtExceptionHandler(new ServerException());
		start(); // avvia il thread, invoca run()
	}

	public void run() {
		String choice = ""; // Operazione scelta sul client
		String table_name = "";
		float minSup = 0F;
		float eps = 0F;
		String path = "";

		try {
			// Ricevo la scelta effettuata sul menu
			choice = in.readLine();
			System.out.println("Ricevuto da Client: " + choice);
			// Se si richiede la scoperta di pattern chiusi allora si richiedono gli altri valori
			if (choice.equals("1")) {
				// Invio la stringa e ricevo il nome della tabella
				table_name = in.readLine();
				System.out.println("Tabel_name: " + table_name);

				// Il server richiede il valore di minSup
				minSup = Float.parseFloat(in.readLine());
				System.out.println("minSup: " + minSup);

				// Il Server richiede il valore di eps e lo riceve dal client
				eps = Float.parseFloat(in.readLine());
				System.out.println("esp: " + eps);

				// Il Server richiede il nome del file più estensione su cui salvare (Si assume di salvare sul workspace)
				path = in.readLine();
				System.out.println("path: " + path);
				
				

				// Il Server fornisce il servizio di scoperta di pattern chiusi
				try {
					if (DBAccess.initConnection() == false)
						throw new DatabaseConnectionException();
					else {
						Data data = new Data(table_name); // Carico la matrice

						// Scoperta patten frequenti
						LinkList outputFP = FrequentPatternMiner.frequentPatternDiscovery(data, minSup);

						// Copio i pattern frequenti in una LinkedList che mi servirà per la scoperta di pattern chiusi
						LinkedList<FrequentPattern> out = new LinkedList<FrequentPattern>();
						Puntatore p = outputFP.firstList();
						int i = 1;
						while (!outputFP.endList(p)) {
							FrequentPattern FP = (FrequentPattern) outputFP.readList(p);
							out.add(FP); // copia nella lista
							p = outputFP.succ(p);
							i++;
						}

						Collections.sort(out);// ordinamento della lista di pattern frequenti

						// calcolo dei pattern chiusi
						ClosedPatternArchive archivio = ClosedPatternMiner.closedPatternDiscovery(out, eps);
						
						 if (archivio == null)
							 throw new ServerException();

						// salvataggio
						try {
							ClosedPatternArchive.salvataggio(archivio, path);
							String arch = archivio.toString();
							pout.println(arch);
						} catch (IOException e) {
							System.err.println("Errore nel salvataggio");
						}
						
					}
				} catch (DatabaseConnectionException e) {
					System.err.println("Errore caricamento driver");
				} catch (EmptySetException e) {
					System.err.println("Matrice vuota");
				}

			} else if (choice.equals("2")) {
				try {
					path = in.readLine();
					System.out.println("Ricevo dal client il nome del file: " + path);
					File file = new File(path);
					if (!(file.exists())) {
						pout.println("File non trovato");
					}
					ClosedPatternArchive ar = ClosedPatternArchive.caricamento(path);
					pout.println(ar.toString());
				} catch (IOException exc) {
					System.err.println("Errore nel caricamento, archivio non trovato");
				} catch (Exception e) {
					e.getMessage();
				}

			}

		} catch (IOException exc) {
			System.err.println("IOException");
		} finally {
			try {
				socket.close();
			} catch (IOException exc) {
				System.err.println("Socket not closed");

			}
		}
	}
}

/**
 * accetta la richiesta di ciascun Client 
 * avviando un thread ServerOneClient
 * @author Angelo, Simone, Antonio
 *
 */
public class MultiServer extends Thread {
	public static final int PORT = 8080;

	public MultiServer() {
		start();
		System.out.println("Server Avviato");

	}

	public void run() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(PORT);
			while (true) {
				Socket socket = s.accept();
				try {
					new ServerOneClient(socket);
				} catch (IOException e) {
					System.err.println("Fallito chiudo");
					socket.close();
				}
			}
		} catch (IOException e) {
			System.err.println("IOException run");
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				System.err.println("Chiusura finale");
			}
		}
	}

	public static void main(String[] args) {
		MultiServer ml = new MultiServer();

	}
}
