package de.detekt.kojack.model;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class Document {

	private GregorianCalendar date;
	private LinkedList<TaggedEntity> locations, moneys, organizations, percents, persons;
	private String category, link, text, theme, title;
	
	public Document() {
		locations=new LinkedList<TaggedEntity>();
		moneys=new LinkedList<TaggedEntity>();
		organizations=new LinkedList<TaggedEntity>();
		percents=new LinkedList<TaggedEntity>();
		persons=new LinkedList<TaggedEntity>();
	}
	
	public GregorianCalendar getDate() {
		return date;
	}
	public String getCategory() {
		return category;
	}
	public String getLink() {
		return link;
	}
	public String getText() {
		return text;
	}
	public String getTheme() {
		return theme;
	}
	public String getTitle() {
		return title;
	}
	public void setDate(GregorianCalendar date) {
		this.date=date;
	}
	public void setCategory(String category) {
		this.category=category;
	}
	public void setLink(String link) {
		this.link=link;
	}
	public void setText(String text) {
		this.text=text;
	}
	public void setTheme(String theme) {
		this.theme=theme;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	
	public int countOf(String entity, String kind) {
		
		LinkedList<TaggedEntity> countedEntities;
		kind=kind.toUpperCase();
		switch(kind) {
		case "LOCATION": countedEntities=locations; break;
		case "MONEY": countedEntities=moneys; break;
		case "ORGANIZATION": countedEntities=organizations; break;
		case "PERCENT": countedEntities=percents; break;
		case "PERSON": countedEntities=persons; break;
		default: return 0;
		}
		
		int rc=0;
		for(TaggedEntity countedEntity:countedEntities)
			if(countedEntity.getS().equals(entity))
				rc++;
		return rc;
		
	}
	
	public boolean countOrAdd(boolean inTitle, String entity, String kind) {
		
		LinkedList<TaggedEntity> countedEntities;
		kind=kind.toUpperCase();
		switch(kind) {
		case "LOCATION": countedEntities=locations; break;
		case "MONEY": countedEntities=moneys; break;
		case "ORGANIZATION": countedEntities=organizations; break;
		case "PERCENT": countedEntities=percents; break;
		case "PERSON": countedEntities=persons; break;
		default: throw new IllegalArgumentException();
		}
		
		for(TaggedEntity countedEntity:countedEntities)
			if(countedEntity.getS().equals(entity)) {
				countedEntity.count(inTitle);
				return true;
			}
		countedEntities.add(new TaggedEntity(inTitle, entity));
		return false;
		
	}
	
	public String toString() {
		
		String rc="Category: "+category+"\n"
				+ "Theme: "+theme+"\n"
				+ "Title: "+title+"\n"
				+ "Date: "+String.format("%02d.%02d.%d", date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH)+1, date.get(Calendar.YEAR))+"\n"
				+ "Text: "+text+"\n"
				+ "Link:"+link+"\n";
		
		rc+="Tagged Persons:\n";
		for(TaggedEntity person:persons)
			rc+="\t"+person+"\n";
		rc+="Tagged Locations:\n";
		for(TaggedEntity location:locations)
			rc+="\t"+location+"\n";
		rc+="Tagged Organizations:\n";
		for(TaggedEntity organization:organizations)
			rc+="\t"+organization+"\n";
		rc+="Tagged Money:\n";
		for(TaggedEntity money:moneys)
			rc+="\t"+money+"\n";
		rc+="Tagged Percentage:\n";
		for(TaggedEntity percent:percents)
			rc+="\t"+percent+"\n";
		
		return rc;
	
	}
	
}
