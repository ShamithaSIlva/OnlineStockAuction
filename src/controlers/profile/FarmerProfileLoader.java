package controlers.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.contract.ContractService;
import models.entity.Contract;
import models.entity.Farmer;
import models.product.CareTaker;
import models.product.OriginatorWidget;
import models.product.ProductService;
import models.product.ProductStockService;
import models.profile.ProfilesService;

/**
 * Servlet implementation class FarmerProfileLoader
 */
@WebServlet("/FarmerProfileLoader")
public class FarmerProfileLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	OriginatorWidget o = new OriginatorWidget();
	CareTaker c = new CareTaker();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FarmerProfileLoader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		HttpSession session = request.getSession(true);
		session.setAttribute("userid", username);
		String event =  request.getParameter("tabEvent");
		response.setContentType("application/json");
		String oldPassword=request.getParameter("old password");
		String newPassword=request.getParameter("new password");
		
		if(event != null &&  event.equals("Add Products") && !"productStockForm".equals(request.getParameter("formSubmit")) )
		{
			response.getWriter().append(ProductService.getProductServiceInstance(getServletContext()).getProductsAsJSON());
					    
		}
		else if(event != null &&  event.equals("View Products"))
		{
		    Gson gson = new GsonBuilder().create();	
		   
			response.getWriter().append( gson.toJson(c.getProductList()));
					    
		}
		else if(("individualContractDispatch".equals(request.getParameter("individualContractDispatch")))) {
			String contractID = request.getParameter("contractID");
			Contract contract = ContractService.getContractServiceInstance( getServletContext() ).getContractByID( contractID );
			Farmer user = (Farmer)ProfilesService.getProfileServiceInstance(getServletContext()).getProfile((String)session.getAttribute("username"));	
			contract.setFarmer( user );
			response.getWriter().append("{\"state\":\"success\",\"message\":\"" + contract.execute() + "\"}");
			
		}
		else if(("commandForm".equals(request.getParameter("formSubmit1")))) {
			String frequency1 = request.getParameter("frequency-dropdown_1");
			Farmer user = (Farmer)ProfilesService.getProfileServiceInstance(getServletContext()).getProfile((String)session.getAttribute("username"));	
			
			response.getWriter().append(ContractService.getContractServiceInstance(getServletContext()).getPaidContractsForFarmerDispatch(user.getUserID(), frequency1));
			
		}
		else if(event != null &&  event.equals("Change password")) {
			if(ProfilesService.getProfileServiceInstance(getServletContext()).validPasswordCheck(username, oldPassword)) {
				
				if(oldPassword!=null) {
				Farmer user = (Farmer)ProfilesService.getProfileServiceInstance(getServletContext()).getProfile((String)session.getAttribute("username"));	
				response.getWriter().append("{\"state\":\"Success\",\"message\":\"password change successfull..!!\",\"page\""+":\""+user.getProfileType()+"\",\"name\":\""+user.getUsername()+"\"}");
			
				}
			}
			
		}
		else if("productStockForm".equals(request.getParameter("formSubmit"))) {
			
			String selectedProduct = request.getParameter("product-dropdown");
			String quantity = request.getParameter("quantity");
			String price = request.getParameter("price");
			String frequency = request.getParameter("frequency-dropdown");
			c.setWidget(o);
			
			
			if ("undobutton_clicked".equals(request.getParameter("undobutton"))) {
				
				c.undoOperation();
				System.out.println(c.getWidgetValue());
				response.getWriter().append("{\"state\":\"Undo process done sucessfully..!!\"}");
			}
			else {
				
				Farmer user = (Farmer)ProfilesService.getProfileServiceInstance(getServletContext()).getProfile((String)session.getAttribute("username"));
				String id=Long.toString(user.getUserID());
				c.setWidgetValue((ProductService.getProductServiceInstance(getServletContext()).getProductByID(selectedProduct)));
				System.out.println(c.getWidgetValue());
				response.getWriter().append(ProductStockService.getProductStockServiceInstance(getServletContext()).addProductStock(selectedProduct, quantity, frequency, id, price));
			}
				
		}
		
		else
		{
			
			Farmer user = (Farmer)ProfilesService.getProfileServiceInstance(getServletContext()).getProfile((String)session.getAttribute("username"));		
			response.getWriter().append("{\"state\":\"Success\",\"message\":\"Login Successfull..!!\",\"page\":\""+user.getUsername()+"\",\"id\":\""+user.getUserID()+"\",\"address\":\""+user.getFarmAddress()+"\",\"telephone\":\""+user.getTelephone()+"\",\"spam\":\""+user.isSpam()+"\"}");
			
		}
		
		
	}

}

