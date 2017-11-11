//<applet code = "client.Discovery"  archive = ../../eseguibili/client.jar width = "300" height = "300">
//</applet>
package client;
import javax.swing.*;
import javax.swing.border.Border;

import server.MultiServer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Applet 
 * @author Angelo, Simone, Antonio
 *
 */
public class Discovery extends JApplet {
	private OutputStream out; /** Stream di output per la comunicazione con il server*/
	private InputStream in; /** Stream di input per la comunicazione con il server*/
	private JRadioButton db; /** Bottone per selezionare la modalit processo di scoperta da tabella di Database*/
	private JRadioButton file; /** Bottone per selezionare la modalit lettura da archivio*/
	private JTextField nameDataTxt; /** Campo per inserire il nome della tabella di Database*/
	private JButton runConstructioBt; /** Bottone per avviare il processo di scoperta da tabella di Database*/
	private JTextField minSupTxt; /** Campo per inserire il valore di minSup*/
	private JTextField epsTxt; /** Campo per inserire il valore di epsilon*/
	private JTextArea patternsAreaTxt; /** Area per visualizzare l'insieme dei pattern scoperti o letti dall'archivio*/
	private JTextArea msgAreaTxt; /** Area per visualizzare messaggi, notifiche o eccezioni all'utente*/

	/**
	 * Metodo che prepara l'applet e gestisce la connessione al server e i Listener
	 * @return void
	 */
	public void init() {

		
		Container cp = getContentPane(); // Contenitore generico

		JPanel cpDiscoveryMining = new JPanel();// pannello scelta
		Border borderTree = BorderFactory.createTitledBorder("Selecting Data Source");
		cpDiscoveryMining.setBorder(borderTree);
		cpDiscoveryMining.setLayout(new BoxLayout(cpDiscoveryMining, BoxLayout.Y_AXIS));

		ButtonGroup buttonGroup = new ButtonGroup();
		db = new JRadioButton("Discovery patterns from db");
		file = new JRadioButton("Reading patterns from file");
		buttonGroup.add(db);
		buttonGroup.add(file);
		db.setSelected(true);
		cpDiscoveryMining.add(db);
		cpDiscoveryMining.add(file);

		JPanel inputParameters = new JPanel();// pannelo input minsUp, eps, data
		Border borderTree2 = BorderFactory.createTitledBorder("Input parameters");
		inputParameters.setBorder(borderTree2);

		JLabel table_label = new JLabel("Data");
		JLabel minSup_label = new JLabel("min sup");
		JLabel eps_label = new JLabel("eps");
		nameDataTxt = new JTextField(10);
		minSupTxt = new JTextField(3);
		epsTxt = new JTextField(3);

		inputParameters.add(table_label);
		inputParameters.add(nameDataTxt);
		inputParameters.add(minSup_label);
		inputParameters.add(minSupTxt);
		inputParameters.add(eps_label);
		inputParameters.add(epsTxt);

		JPanel discoveryPanel = new JPanel();// pannello discovery
		Border borderTree3 = BorderFactory.createTitledBorder("Discovery");
		discoveryPanel.setBorder(borderTree3);
		discoveryPanel.add(cpDiscoveryMining);
		discoveryPanel.add(inputParameters);
		discoveryPanel.setLayout(new FlowLayout());

		JPanel buttonPanel = new JPanel();
		runConstructioBt = new JButton("Run");
		buttonPanel.add(runConstructioBt);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setLayout(new FlowLayout());

		patternsAreaTxt = new JTextArea(20, 30);
		JScrollPane sp = new JScrollPane(patternsAreaTxt);
		patternsAreaTxt.setEditable(false); // in questo caso la disabilito perchè deve solo mostrare

		msgAreaTxt = new JTextArea();
		msgAreaTxt.setEditable(false);

		JPanel textAreaPanel = new JPanel();// pannello risultati

		JPanel patternsAreaPanel = new JPanel();
		JPanel msgAreaPanel = new JPanel();

		Border borderTree4 = BorderFactory.createTitledBorder("Frequent Patterns and Closed Patterns");
		Border borderTree5 = BorderFactory.createTitledBorder("Msg Area");

		patternsAreaPanel.setBorder(borderTree4);
		msgAreaPanel.setBorder(borderTree5);

		textAreaPanel.add(patternsAreaPanel);
		patternsAreaPanel.add(sp);
		patternsAreaPanel.setLayout(new BoxLayout(patternsAreaPanel, BoxLayout.Y_AXIS));

		textAreaPanel.add(msgAreaPanel);
		msgAreaPanel.add(msgAreaTxt);
		msgAreaPanel.setLayout(new BoxLayout(msgAreaPanel, BoxLayout.LINE_AXIS));
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));

		cp.add(discoveryPanel, BorderLayout.BEFORE_FIRST_LINE);
		cp.add(buttonPanel, BorderLayout.CENTER);
		cp.add(textAreaPanel, BorderLayout.SOUTH);

		setVisible(true);
		
		

		runConstructioBt.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Socket socket = null;
				try {
					InetAddress addr = InetAddress.getByName("127.0.0.1");
					 socket = new Socket(addr, MultiServer.PORT);
					in = socket.getInputStream();
					out = socket.getOutputStream();
					startDiscoveryBt_mouseClicked(e);
					
				} catch (IOException exc) {
					msgAreaTxt.setText("IOException client");
				}finally{
					try{
						in.close();
						out.close();
						socket.close();
					}catch(IOException exc){
						msgAreaTxt.setText(" Error: Chiusura socket client");
						
					}
				}
				
			}
		});
		
		file.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgAreaTxt.setText("");
				patternsAreaTxt.setText("");
				nameDataTxt.setText("");
				minSupTxt.setText("");
				epsTxt.setText("");
				nameDataTxt.setEditable(false);
				minSupTxt.setEditable(false);
				epsTxt.setEditable(false);
				
			}
		});

		
		db.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgAreaTxt.setText("");
				patternsAreaTxt.setText("");
				nameDataTxt.setText("");
				minSupTxt.setText("");
				epsTxt.setText("");
				nameDataTxt.setEditable(true);
				minSupTxt.setEditable(true);
				epsTxt.setEditable(true);
				
			}
		});
		
	}

	/**
	 * Cattura l'evento di pressione del pulsante runConstructioBt
	 * e avvia l'esecuzione in base al Jbutton selezionato (db o file)
	 * quando termina i patterns saranno serializzati su server e visualizzati
	 * nell'area patternsAreaTxt
	 * L'esito del processo sara' visualizzato nell'area msgAreaTxt
	 * @param e
	 * @return void
	 */
	private void startDiscoveryBt_mouseClicked(ActionEvent e) {

		try {

			char risp = '\u0000';
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			PrintWriter pout = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)), true);

			if (db.isSelected()) {

				risp = '1';
				// Invio dei valori al Server
				pout.println(risp);
				String name_table = nameDataTxt.getText();
				pout.println(name_table);

				String minSup = minSupTxt.getText();

				if (Float.parseFloat(minSup) <= 0 || Float.parseFloat(minSup) > 1) {
					this.dialog("Error minSup","Errore: Inserire minSup compreso (0;1] ");
					minSupTxt.setText("");
					throw new NoConsistentMinSupException();

				} else {
					pout.println(minSup);
				}

				String eps = epsTxt.getText();
				if (Float.parseFloat(eps) <= 0 || Float.parseFloat(eps) > 1) {
					this.dialog("Error eps","Errore: Inserire eps compreso (0;1]");
					epsTxt.setText("");
					throw new NoConsistentEpsException();
				} else {
					pout.println(eps);
				}

				String path = "eseguibili/Pattern.ser";
				pout.println(path);
				String x = null;
				String y = "";
				while ((x = br.readLine()) != null) {
					y += x + "\n";
				}
				patternsAreaTxt.setText(y);
				msgAreaTxt.setText("successo");

			} else if (file.isSelected()) {
				risp = '2';
				// Invio dei valori al Server
				pout.println(risp);
				String path = "eseguibili/Pattern.ser";
				pout.println(path);
				String x = null;
				String y = "";
				while ((x = br.readLine()) != null) {
					y += x + "\n";
				}
				patternsAreaTxt.setText(y);
				msgAreaTxt.setText("successo");
			}

		} catch (NoConsistentMinSupException exc) {
			msgAreaTxt.setText("Fallimento: valore di minSup inserito non corretto");
		} catch (NoConsistentEpsException exc1) {
			msgAreaTxt.setText("Fallimento: valore di epsilon inserito non corretto");
		} catch (ServerException exc2) {
			msgAreaTxt.setText("Fallimento: file non trovato");
		}catch (Exception exc3) {
			msgAreaTxt.setText("IOException");
		}
	}

	/**
	 * Metodo che crea una Jdialog con parametri:
	 * titolo e messaggio
	 * @param title
	 * @param error
	 */
	private void dialog(String title,String error) {
		JFrame f = new JFrame();
		JDialog d = new JDialog(f, title, true);
		d.setLayout(new FlowLayout());
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
				patternsAreaTxt.setText("");
			}
		});
		d.add(new JLabel(error));
		d.add(b);
		d.setSize(300, 300);
		d.setVisible(true);
	}	
}