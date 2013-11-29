package de.detekt.kojack.model;
import java.io.File;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class DocumentCreator {

	enum ContentState {
		CHANNEL, IMAGE, ITEM;
	}
	enum NERState {
		STARTTAG, CONTENT, ENDTAG, TEXT;
	}
	
	
	private AbstractSequenceClassifier<CoreLabel> classifier;
	
	public DocumentCreator() {
		classifier=CRFClassifier.getClassifierNoExceptions("classifiers/english.muc.7class.distsim.crf.ser.gz");
	}
	
	public Document createDocument(File RSSFeed) {
		
		class ContentHandler extends DefaultHandler {
			
			Document doc=new Document();
			String current="";
			ContentState state;
			
			public void characters(char[] ch, int start, int length) throws SAXException {
				current+=new String(ch, start, length);
			}
			public void startElement(String uri, String localName, String qName, Attributes atts) {
				if(qName.equals("channel"))
					state=ContentState.CHANNEL;
				else if(qName.equals("image"))
					state=ContentState.IMAGE;
				else if(qName.equals("item"))
					state=ContentState.ITEM;
				current="";
			}
			public void endElement(String uri, String localName, String qName) {
				if(qName.equals("title") && state==ContentState.CHANNEL)
					doc.setCategory(current);
				else if(qName.equals("pubDate") && state==ContentState.CHANNEL)
					doc.setDate(toDate(current));
				else if(qName.equals("title") && state==ContentState.ITEM)
					doc.setTitle(current);
				else if(qName.equals("link") && state==ContentState.ITEM)
					doc.setLink(current);
				else if(qName.equals("ExtractedText") && state==ContentState.ITEM) {
					doc.setText(current);
				}
			}
			
			private GregorianCalendar toDate(String date) {
				String[] dateFields=date.substring(5, 16).split(" ");
				switch(dateFields[1]) {
				case "Jan": dateFields[1]="0"; break;
				case "Feb": dateFields[1]="1"; break;
				case "Mar": dateFields[1]="2"; break;
				case "Apr": dateFields[1]="3"; break;
				case "May": dateFields[1]="4"; break;
				case "Jun": dateFields[1]="5"; break;
				case "Jul": dateFields[1]="6"; break;
				case "Aug": dateFields[1]="7"; break;
				case "Sep": dateFields[1]="8"; break;
				case "Oct": dateFields[1]="9"; break;
				case "Nov": dateFields[1]="10"; break;
				case "Dec": dateFields[1]="11"; break;
				}
				return new GregorianCalendar(Integer.parseInt(dateFields[2]), Integer.parseInt(dateFields[1]), Integer.parseInt(dateFields[0]));
			}
			
		}
		
		try {
			SAXParser parser=SAXParserFactory.newInstance().newSAXParser();
			ContentHandler handler=new ContentHandler();
			parser.parse(RSSFeed, handler);
			return handler.doc;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	public void addEntities(Document doc) {
		addEntities(true, doc, classifier.classifyWithInlineXML(doc.getTitle()));
		addEntities(false, doc, classifier.classifyWithInlineXML(doc.getText()));
	}
	private void addEntities(boolean fromTitle, Document doc, String taggedText) {
		
		LinkedList<String> tagsOfInterest=new LinkedList<String>();
		tagsOfInterest.add("LOCATION");
		tagsOfInterest.add("MONEY");
		tagsOfInterest.add("ORGANIZATION");
		tagsOfInterest.add("PERCENT");
		tagsOfInterest.add("PERSON");
		
		NERState state=NERState.TEXT;
		String content="", tag="";
		
		for(char c:taggedText.toCharArray()) {
			if(c=='<' && state==NERState.TEXT)
				state=NERState.STARTTAG;
			else if(c=='>' && state==NERState.STARTTAG)
				state=NERState.CONTENT;
			else if(c=='<' && state==NERState.CONTENT)
				state=NERState.ENDTAG;
			else if(c=='>' && state==NERState.ENDTAG)
				state=NERState.TEXT;
			else {
				if(state==NERState.STARTTAG)
					tag+=c;
				else if(state==NERState.CONTENT)
					content+=c;
				else if(state==NERState.ENDTAG && !tag.isEmpty()) {
					
					if(tagsOfInterest.contains(tag.toUpperCase())) {
						
						if(tag.toUpperCase().equals("PERSON"))
							try {content=content.split(" ")[1];}catch(IndexOutOfBoundsException ex){}
							
						doc.countOrAdd(fromTitle, content, tag);
						
					}
					
					tag="";
					content="";
					
				}
			}
		}
		
	}
	
}
