package models.report;

import com.itextpdf.text.pdf.PdfPTable;

public class PDFTable {
	
	PdfPTable pTable;
	int columns;
	int rows;
	
	public PDFTable(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		this.pTable = new PdfPTable(columns);
	}

	public PdfPTable getpTable() {
		return pTable;
	}

	public void setpTable(PdfPTable pTable) {
		this.pTable = pTable;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}	

}
