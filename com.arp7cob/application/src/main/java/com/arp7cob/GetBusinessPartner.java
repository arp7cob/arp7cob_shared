package com.arp7cob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@WebServlet("/api/businesspartners")
public class GetBusinessPartner extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(GetBusinessPartner.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
    	//final ErpConfigContext configContext = new ErpConfigContext();
    	try {
			final List<BusinessPartner> businessPartners =
					new DefaultBusinessPartnerService()
					.getAllBusinessPartner()
					.select(BusinessPartner.BUSINESS_PARTNER,
					        BusinessPartner.LAST_NAME,
					        BusinessPartner.FIRST_NAME,
					        BusinessPartner.IS_MALE,
					        BusinessPartner.IS_FEMALE,
					        BusinessPartner.CREATION_DATE,
					        BusinessPartner.SUPPLIER)
					.top(5)
					.execute(null);
			String jsonResult = new Gson().toJson(businessPartners);
			response.setContentType("application/json");
			response.getWriter().write(jsonResult);
		} catch (ODataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	/*final List<MyBusinessPartnerType> businessPartners = ODataQueryBuilder
    	.withEntity("/sap/opu/odata/sap/API_BUSINESS_PARTNER",
    	            "A_BusinessPartner")
    	.select("BusinessPartner",
    	        "LastName",
    	        "FirstName",
    	        "IsMale",
    	        "IsFemale",
    	        "CreationDate")
    	.build()
    	.execute(configContext)
    	.asList(MyBusinessPartnerType.class); */

        //logger.info("I am running!");
       // response.getWriter().write("Hello World!");
    }
}
