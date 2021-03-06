package models.report;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFReport implements IReport
{
	@Override
	public void generateReport( Object table,HttpServletResponse response )
	{
		// this is the place we really create PDF files and dispatch to the browser
		Document document = null;
		try 
		{
            document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(((PdfPTable)table));
            document.close();
        } catch (Exception de) {
            de.printStackTrace();
        }
	}

	@Override
	public void emailDocument( StringBuilder builder )
	{
		// TODO Auto-generated method stub
	}
}
