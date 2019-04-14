package models.entity;

public class MonthlyContract extends Contract implements Command
{
	public MonthlyContract( Bid bid )
	{
		super( "monthlycontract" ); // this is only a GSON library requirement for deserializing
		this.setStockFrequency( StockFrequency.MONTHLY );
		this.setAgreedBid( bid );

	}

	public MonthlyContract( Farmer farmer )
	{
		this.farmer = farmer;
	}

	@Override
	public double getPriceOnFrequency()
	{
		return getAgreedBid().getAgreedFinalPrice();
	}

	@Override
	public StringBuilder getContractConstraints()
	{
		getStringBuilder().append( "  > This contract roles out in monthy basis\n" );
		return getStringBuilder();
	}

	@Override
	public String getFarmerDetails()
	{
		return "Farmer ID : " + getAgreedBid().getFarmerID();
	}

	@Override
	public String getRetailerDetails()
	{
		return "Retailer ID : " + getAgreedBid().getRetailerID();
	}

	@Override
	public String execute()
	{
		// TODO Auto-generated method stub
		return farmer.dispatchMonthly();
	}

}
