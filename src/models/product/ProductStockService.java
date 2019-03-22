package models.product;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.google.gson.reflect.TypeToken;

import models.entity.EntityService;
import models.entity.Farmer;
import models.entity.Product;
import models.entity.ProductStock;
import models.entity.RandomNumberGenerator;
import models.entity.StockFrequency;
import models.login.LoginService;
import models.profile.ProfilesService;

public class ProductStockService extends EntityService
{
	private static ProductStockService instance;
	List<ProductStock> productStock = new ArrayList<>();
	static String filePath = "/WEB-INF/db/product/ProductStock.json";
	
	private ProductStockService( ServletContext context, String filePath )
	{
		super( context, filePath );
	}
	
	public static ProductStockService getProductStockServiceInstance(ServletContext context)
	{
		if(instance == null)
		{
			instance = new ProductStockService(context,filePath);
			instance.loadEntities();
		}
		return instance;
	}
	public String addProductStock(String name,String quantity,String frequency,String farmerID, String price) {
		
		
		ProductStock productStockObj=new ProductStock();
		
		StockFrequency freq=StockFrequency.valueOf(frequency);
		int quant=Integer.parseInt(quantity);
		long id=Long.parseLong(farmerID);
		Farmer farmer = (Farmer)ProfilesService.getProfile( id );
		Product prod = ProductService.getProductServiceInstance( LoginService.getServeletContext() ).getProductByProductID( Long.parseLong( name ) );
		Double unitPrice=Double.parseDouble(price);
		
		productStockObj.setFrequency(freq);
		productStockObj.setQuantitiy(quant);
		productStockObj.setProduct(prod);
		productStockObj.setUnitPrice(unitPrice);
		productStockObj.setFarmerID( id );
		productStockObj.setProductStockID( RandomNumberGenerator.getLongID() );
		productStockObj.setPriority( farmer.getPriorityLevel() );
		productStock.add(productStockObj);
		return "{\"state\":\"Product Stock addded successfully..\"}";
		

	}
	
	public ProductStock getProductStockByID(String productStockID)
	{
		ProductStock prodStock = null;
		for(ProductStock stock : productStock)
		{
			
			if(stock.getProductStockID() == Long.parseLong( productStockID ))
			{
				prodStock = stock;
			}
		}
		return prodStock;
	}
	
	
	public String getSearchedProductStocksAsJSON(String productID, String frequency, String quantity)
	{
		List<ProductStock> searched = new ArrayList<>();
		for(ProductStock stock : productStock)
		{
			Product product = stock.getProduct();
			if(product.getProductID() == Long.parseLong( productID ) && stock.getFrequency().toString().equals( frequency ) && stock.getQuantitiy() == Integer.parseInt( quantity ))
			{
				stock.setPriority(ProfilesService.getProfile( stock.getFarmerID() ).getPriorityLevel());
				searched.add(stock);
			}
		}
		return getGson().toJson(searched);
	}
    
	public void loadEntities()
	{
		try
		{
			super.loadEntities();
		}
		catch ( FileNotFoundException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TypeToken<List<ProductStock>> token = new TypeToken<List<ProductStock>>() {};
		productStock = getGson().fromJson(new InputStreamReader(getIs()), token.getType());
	}
}
