package infoartex.artex.bundles;

import java.util.LinkedList;
import java.util.List;

public class EstructuraUnion {
	private String columna;
	private List<String> attrs;

	public EstructuraUnion(String columna) {
		this.columna = columna;
		attrs = new LinkedList<>();
	}

	public void adicionarAtributo(String attr) {
		if (attrs.indexOf(attr)==-1) {
			attrs.add(attr);
		}
	}

	public String getColumna() {
		return columna;
	}

	public void setColumna(String columna) {
		this.columna = columna;
	}

	public List<String> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<String> attrs) {
		this.attrs = attrs;
	}
	
	@Override
	public boolean equals(Object estruct){
		return this.columna.equals(((EstructuraUnion)estruct).columna);
	}

}
