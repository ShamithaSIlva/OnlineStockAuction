package models.entity;

import java.util.Date;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import models.report.IReportStructure;
import models.report.PDFCell;
import models.report.PDFTable;

public abstract class Contract implements IReportStructure
{
	private long farmerUserID;
	private long retailerUserID;
	private long contractID;
	private Bid agreedBid;
	private StockFrequency stockFrequency;
	private StringBuilder contractConstraints = new StringBuilder();
	String type;  // this is only a GSON library requirement for deserializing
	boolean paid;
	
	public Contract(String type)
	{
		this.type = type;
	}
	
	public Contract() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract double getPriceOnFrequency();
	
	public abstract StringBuilder getContractConstraints();
	
	public abstract String getFarmerDetails();
	
	public abstract String getRetailerDetails();
	
	@Override
	public String toString()
	{
		return "Farmer ID : "+farmerUserID+" "+"Retailer ID : "+retailerUserID+" "+" Placed Bid : "+agreedBid.toString();
	}

	public long getFarmerUserID()
	{
		return farmerUserID;
	}

	public void setFarmerUserID( long farmerUserID )
	{
		this.farmerUserID = farmerUserID;
	}

	public long getRetailerUserID()
	{
		return retailerUserID;
	}

	public void setRetailerUserID( long retailerUserID )
	{
		this.retailerUserID = retailerUserID;
	}

	public Bid getAgreedBid()
	{
		return agreedBid;
	}

	public void setAgreedBid( Bid agreedBid )
	{
		this.agreedBid = agreedBid;
	}

	public StockFrequency getStockFrequency()
	{
		return stockFrequency;
	}

	public void setStockFrequency( StockFrequency stockFrequency )
	{
		this.stockFrequency = stockFrequency;
	}

	public void setContractConstraints( StringBuilder contractConstraints )
	{
		this.contractConstraints = contractConstraints;
	}	
	
	public StringBuilder getStringBuilder()
	{
		return contractConstraints;
	}
	
	
	@Override
	public String getFooter() {
		return "Signed Date : "+new Date().toString();
	}
	@Override
	public String getHeader() {
		return "Contract";
	}
	@Override
	public PdfPTable getBody(PdfPTable  table) {
		table.addCell(new PdfPCell(new Phrase(getFarmerDetails())));
		table.addCell(new PdfPCell(new Phrase(getRetailerDetails())));
		table.addCell(new PdfPCell(new Phrase("Delevery Frequency : "+getContract().getStockFrequency())));
		table.addCell(new PdfPCell(new Phrase("Original Price On Frequency : "+getContract().getPriceOnFrequency())));
		table.addCell(new PdfPCell(new Phrase("Final Price On Frequency : "+getPriceOnFrequency())));
		table.addCell(new PdfPCell(new Phrase("Contract Constraints :\n"+getContractConstraints().toString())));
	    return table;
	}
	
	public Contract getContract()
	{
		return this;
	}

	public long getContractID()
	{
		return contractID;
	}


	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}	
	
	
	public void setContractID( long contractID )
	{
		this.contractID = contractID;
	}		

}
