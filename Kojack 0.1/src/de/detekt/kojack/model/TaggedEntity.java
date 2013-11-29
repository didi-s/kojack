package de.detekt.kojack.model;

public class TaggedEntity {

	private boolean inTitle;
	private int count;
	private String s;
	
	public TaggedEntity(boolean inTitle, String s) {
		this.inTitle=inTitle;
		count=0;
		this.s=s;
		count(inTitle);
	}
	public boolean isInTitle() {
		return inTitle;
	}
	public int getCount() {
		return count;
	}
	public String getS() {
		return s;
	}
	
	public void count(boolean inTitle) {
		count++;
		if(inTitle) {
			this.inTitle=true;
			//TODO: count-Berechnung
		}
	}
	
	public String toString() {
		return s+"("+count+((inTitle)?"; in Title":"")+")";
	}
	
}
