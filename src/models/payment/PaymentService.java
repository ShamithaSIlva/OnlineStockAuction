package models.payment;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import models.contract.ContractService;
import models.entity.Contract;
import models.entity.EntityService;
import models.entity.RuntimeTypeAdapterFactory;

public class PaymentService extends EntityService
{

	private static PaymentService paymentService;
	public static String EXPIRED_PAYMENT_DETAILS = "Payment Details are expired";
	static String filePath = "/WEB-INF/db/bankaccounts/Bankaccounts.json";
	ServletContext context;
	List<BankAccount> accounts = new ArrayList<>();
	Map<String, Payment> paymentMap = new HashMap<String, Payment>();

	private PaymentService( ServletContext context, String filePath )
	{
		super( context, filePath );
	}

	public static PaymentService getPaymentServiceInstance( ServletContext context ) throws FileNotFoundException
	{
		if ( paymentService == null )
		{
			paymentService = new PaymentService( context, filePath );
			paymentService.loadEntities();
		}
		return paymentService;
	}

	public String makePayment( Contract contract, String accountNumber )
	{
		BankAccount account = getBankAccount( accountNumber );
		Payment payment = getPayment( Long.toString(contract.getContractID()) );
		String message = "";
		if ( account.validateAccount() )
		{
			try
			{
				throw new PamentDetailsNotUpdatedException( EXPIRED_PAYMENT_DETAILS );
			}
			catch ( PamentDetailsNotUpdatedException e )
			{
				message = "{\"state\":\"failed\",\"message\":\"" + e.getMessage() + "\"}";
			}
		}
		else if(payment.getState() instanceof SentToPaymentGateway)
		{
			message = "{\"state\":\"success\",\"message\":\"A payment has already been allocated for this contract\"}";
		}
		else
		{
			try
			{
				message = account.makePayment( contract.getPriceOnFrequency() );				
				payment.setState( new SentToPaymentGateway() );
				contract.setPaid(true);
			}
			catch ( NotEnoughBalanceException e )
			{
				message = "{\"state\":\"failed\",\"message\":\"" + e.getMessage() + "\"}";
			}
		}
		return message;
	}
	
	public String addFundsToAccount(double amount, String accountNumber)
	{
		BankAccount account = getBankAccount( accountNumber );
		account.addCash( amount );
		return "{\"state\":\"success\",\"message\":\"Fund "+amount+" added Successfully\"}";
	}

	private Payment getPayment( String paymentIdentifier )
	{
		Payment payment = null;
		if ( paymentMap.containsKey( paymentIdentifier ) )
		{
			payment = paymentMap.get( paymentIdentifier );
		}
		else
		{
			payment = new Payment();
			paymentMap.put( paymentIdentifier, payment );
		}
		return payment;
	}

	public BankAccount getBankAccount( String accountNumber )
	{
		BankAccount bankAccount = null;
		for ( BankAccount account : accounts )
		{
			if ( account.getAccountNumber().equals( accountNumber ) )
				bankAccount = account;
		}
		return bankAccount;
	}

	public String getUserBankAccountsAsJSON( long userID )
	{
		List<BankAccount> bankAccounts = new ArrayList<>();
		for ( BankAccount account : accounts )
		{
			if ( account.getUserID() == userID )
				bankAccounts.add( account );
		}
		return getGson().toJson( bankAccounts );
	}
	
	

	public void loadEntities() throws FileNotFoundException
	{
		super.loadEntities();
		RuntimeTypeAdapterFactory<BankAccount> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of( BankAccount.class, "type" ).registerSubtype( PayPal.class, "paypal" ).registerSubtype( Venmo.class, "venmo" ).registerSubtype( AmazonPayments.class, "amazonpayments" );
		Gson gson = new GsonBuilder().registerTypeAdapterFactory( runtimeTypeAdapterFactory ).create();
		Type listType = new TypeToken<List<BankAccount>>()
		{
		}.getType();
		accounts = gson.fromJson( new InputStreamReader( getIs() ), listType );
	}

	public List<BankAccount> getBankAccoutns()
	{
		return accounts;
	}

	public void testGSON()
	{
		List<BankAccount> accounts = new ArrayList<>();
		accounts.add( new PayPal( 4000, "PP3784", "PA7748849393", 100106, "100106@paypal.com" ) );
		accounts.add( new AmazonPayments( 8000, "AP5467", "PA7748849393", 100107, "100106@amazon.com" ) );
		accounts.add( new Venmo( 6000, "VO2334", "VO34342232", 100108, "100106@venmo.com" ) );
		accounts.add( new PayPal( 9000, "PP7895", "PA7748849393", 100109, "100106@paypal.com" ) );

		RuntimeTypeAdapterFactory<BankAccount> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of( BankAccount.class, "type" ).registerSubtype( PayPal.class, "paypal" ).registerSubtype( Venmo.class, "venmo" ).registerSubtype( AmazonPayments.class, "amazonpayments" );
		Gson gson = new GsonBuilder().registerTypeAdapterFactory( runtimeTypeAdapterFactory ).create();

		String json = gson.toJson( accounts );
		System.out.println( json );
		Type listType = new TypeToken<List<BankAccount>>()
		{
		}.getType();
		List<BankAccount> fromJson = gson.fromJson( json, listType );
	}
	
	public boolean checkIfPaymentAllocated(Contract contract)
	{
		Payment payment = getPayment( Long.toString(contract.getContractID()) );
		return payment != null && payment.getState() instanceof SentToPaymentGateway;
	}
}
