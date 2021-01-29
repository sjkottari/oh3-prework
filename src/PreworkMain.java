/* 
 * Ohjelmointi 3 - Preassignment
 * Tehnyt: Santeri Kottari
 */

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;

public class PreworkMain {

	// luodaan globaali Scanner sc-olio
	static Scanner sc = new Scanner(System.in);

	// Main-metodissa kysytään käyttäjältä välittömästi tämän nimeä ja ensimmäistä viestiä
	// switch-case valikon kautta käyttäjä voi valita toimintojen suhteen
	public static void main(String[] args) {

		String username = null, message = null, timestamp = null;

		// uusi ChatData chatItem-olio, yhdistää viestin rakentavat elementit
		ChatData chatItem = new ChatData(username, message, timestamp);

		chatItem = lueKayttaja();
		lueViesti(chatItem);

		int valinta;
		do {
			System.out.print("\nValitse\n"
							+ "1)Nayta edelliset viestit\n"
							+ "2)Syota uusi viesti\n"
							+ "3)Lopetus\n> ");
			valinta = sc.nextInt();
			sc.nextLine();

			switch (valinta) {
				case 1:
					tulostaViestit();
					break;
				case 2:
					lueViesti(chatItem);
					break;
				case 3:
					break;
				default:
					System.out.print("\nYrita uudelleen!\n");
					break;
			}
		} while (valinta != 3);

	}

	// Metodi käyttäjänimen syöttämiseksi, palauttaa ChatData d-olion
	public static ChatData lueKayttaja() {

		String username, message = null, timestamp = null;

		System.out.print("Anna kayttajanimi> ");
		username = sc.nextLine();

		ChatData d = new ChatData(username, message, timestamp);

		return d;
	}

	// Metodi viestin antamiseksi. Ohjelma tallentaa viestin database.txt tiedostoon.
	// Mikäli tiedostoa ei ole olemassa, ohjelma luo sen ensin.
	public static ChatData lueViesti(ChatData chatItem) {

		String message, timestamp;
		String username = chatItem.getUsername();

		File file = new File("database.txt");

		// createNewFile() palauttaa if-lauseelle arvon 'true', 
		// jos tiedostoa ei ole olemassa ja se luodaan onnistuneesti
		try {
			if (file.createNewFile()) {
				System.out.println("\nLuotiin uusi tiedosto: " + file.getName());
			}
		} catch (IOException e) {
			System.out.println("\nTiedostoa ei voitu luoda\n");
			e.printStackTrace();
		}

		System.out.print("\nSyota viesti> ");
		message = sc.nextLine();

		// otetaan aikaleima (tiettyyn muotoon), kun viesti on annettu
		timestamp = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());

		ChatData newItem = new ChatData(username, message, timestamp);
		
		// kirjoitetaan viesti tietoineen database-tiedostoon UTF-8 enkoodattuna.
		// Alla oleva implementaatio ei toimi aivan oikein VSCodessa, vikana siis on, 
		// etteivät ääkköset / muut erikoismerkit tulostu terminaaliin sekä tiedostoon.
		// Eclipsen ympäristössä toimii kuitenkin lähes moitteetta (esim. ääkköset tiedostoon)
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true),
											 StandardCharsets.UTF_8)) 
		{
			writer.write("(" + timestamp + ") <" + username + "> " + message + "\n");
			writer.flush();
			writer.close();
			System.out.println("\nViestin tallennus onnistui:"
							 + "\nTimestamp: " + newItem.getTimestamp()
							 + "\nUser: " + newItem.getUsername()
							 + "\nMsg: " + newItem.getMessage());
		}
		catch (IOException e) {
			System.out.println("\nVirhe viestin kirjoituksessa\n");
			e.printStackTrace();
		}

		return newItem;
	}

	// tulostusmetodi tiedostoon kirjoitettujen viestin tulostamiseksi
	public static void tulostaViestit() {

		try {
			File file = new File("database.txt");
			Scanner fileScanner = new Scanner(file);

			while (fileScanner.hasNextLine()) {
				String prev = fileScanner.nextLine();
				System.out.println(prev);
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("\nTiedostoa ei loytynyt\n");
			e.printStackTrace();
		}

	}

}
