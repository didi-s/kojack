package de.detekt.kojack.model;
import java.io.File;
import java.util.LinkedList;


public class MAIN {

	public static void main(String[] args) {
		
		DocumentCreator creator=new DocumentCreator();
		Crawler crawler=new Crawler();
		
//		Document doc=creator.createDocument(new File("C:\\Users\\Sven.Haese.A9194969\\WS1314\\Praktikum\\Nachrichtenarchiv\\rssfiles\\RSS973602347.xml"));
//		creator.addEntities(doc);
//		System.out.println(doc);
		
		LinkedList<Document> docs=new LinkedList<Document>();
		long time1=System.currentTimeMillis();
		for(File file:crawler.search(new File("C:\\Users\\Sven.Haese.A9194969\\WS1314\\Praktikum\\Nachrichtenarchiv\\rssfiles\\US"))) {
			Document doc=creator.createDocument(file);
			if(doc!=null) {
				creator.addEntities(doc);
				docs.add(doc);
			}
		}
		long time2=System.currentTimeMillis();
		for(Document doc:docs)
			System.out.println(doc);
		long time3=System.currentTimeMillis();
		
		System.out.format("Dauer Erstellen: %02d:%02d.%03d%n", (time2-time1)/60000, (time2-time1)%60000/1000, (time2-time1)%1000);
		System.out.format("Dauer Ausgabe:   %02d:%02d.%03d%n", (time3-time2)/60000, (time3-time2)%60000/1000, (time3-time2)%1000);
		System.out.format("Gesamtdauer:     %02d:%02d.%03d%n", (time3-time1)/60000, (time3-time1)%60000/1000, (time3-time1)%1000);
		
	}
	
}
