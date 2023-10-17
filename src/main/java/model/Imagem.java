package model;

import database.ItemCrud;

public class Imagem implements ItemCrud {

	int id;
	String url;
	
	public Imagem() {}
		
	@Override
	public String toString() {
		return "Imagem [id=" + id + ", url=" + url + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
