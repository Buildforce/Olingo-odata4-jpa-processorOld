# 2.1: Retrieving Data
As mentioned in the preparation, we have to replace our current service implementation by a new one.
Up to now we used `JPAEdmProvider`, which creates the service document as well as the metadata document. Now we will use `JPAODataGetHandler`. A handler instance has two contexts objects, on the one hand the service context, which contains information that does not or only changes rarely during the lifetime of the service and is shared between all requests, and a request context, which has request specific information.  Please note that an instance of `JPAEdmProvider` is of the service context and gets created automatically.

In the tutorial the service context will not change. So it will be put it into the _servlet context_, therefore we created a _listener_ in package _tutorial.service_:
```Java
package tutorial.service;

import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataCRUDContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataServiceContext;
import org.apache.olingo.commons.api.ex.ODataException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class OdataListener implements ServletContextListener {

    // Create Service Context
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final DataSource ds = DataSourceHelper.createDataSource(DataSourceHelper.DB_HSQLDB);
        try {
            final JPAODataCRUDContextAccess serviceContext = JPAODataServiceContext.with()
                    .setPUnit(OdataServlet.PUNIT_NAME)
                    .setDataSource(ds)
                    .setTypePackage("tutorial.operations", "tutorial.model")
                    .build();
            sce.getServletContext().setAttribute("ServiceContext", serviceContext);
        } catch (ODataException e) {
            // Log error
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("ServiceContext", null);
    }

}
```
The use of _web.xml_ is not anymore needed because of the _@WebListener_ annotation to trigger the call of the listener.

~~
```XML
  ...
  <listener>
    <listener-class>tutorial.service.Listener</listener-class>
  </listener>
</web-app>
```
~~
Now we have all the peaces to build a service responding to GET requests.
```Java
package tutorial.service;

import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataCRUDContextAccess;
import nl.buildforce.sequoia.jpa.processor.core.api.JPAODataCRUDHandler;
import org.apache.olingo.commons.api.ex.ODataException;
import tutorial.modify.CUDRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/DemoService.svc/*")
public class OdataServlet extends HttpServlet {
	protected static final String PUNIT_NAME = "TutorialPU";

	@Override
	protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException {
		final JPAODataCRUDContextAccess serviceContext =
				(JPAODataCRUDContextAccess) getServletContext().getAttribute("ServiceContext");
		final JPAODataCRUDHandler handler = new JPAODataCRUDHandler(serviceContext);

		handler.getJPAODataRequestContext().setCUDRequestHandler(new CUDRequestHandler());
		try { handler.process(req, resp); }
		catch (RuntimeException | ODataException e) { throw new ServletException(e); }
	}

}
```
Starting the service we are able to play around with our OData service. We could e.g.:
* Retrieve all the Companies: [http://localhost:8080/DemoOdata/DemoService.svc/Companies](http://localhost:8080/DemoOdata/DemoService.svc/Companies)
* Or we want to find out which user had created Company('1'): [http://localhost:8080/DemoOdata/DemoService.svc/Companies('1')/AdministrativeInformation/Created/User](http://localhost:8080/DemoOdata/DemoService.svc/Companies('1')/AdministrativeInformation/Created/User)
* Or we want to get all companies with role _A_: [http://localhost:8080/DemoOdata/DemoService.svc/Companies?$filter=Roles/any(d:d/BusinessPartnerRole eq 'A')](http://localhost:8080/DemoOdata/DemoService.svc/Companies?$filter=Roles/any(d:d/BusinessPartnerRole%20eq%20%27A%27))
* Or we want to know which Administrative Division has an Area greater than 40000000: [http://localhost:8080/DemoOdata/DemoService.svc/AdministrativeDivisions?$filter=Area gt 4000000&$count=true](http://localhost:8080/DemoOdata/DemoService.svc/AdministrativeDivisions?$filter=Area%20gt%204000000&$count=true)
* Or we look for the parents and children of a certain Administrative Division: [http://localhost:8080/DemoOdata/DemoService.svc/AdministrativeDivisions(DivisionCode='BE254',CodeId='NUTS3',CodePublisher='Eurostat')?$expand=Parent($expand=Parent),Children&$format=json](http://localhost:8080/DemoOdata/DemoService.svc/AdministrativeDivisions(DivisionCode='BE254',CodeId='NUTS3',CodePublisher='Eurostat')?$expand=Parent($expand=Parent),Children&$format=json)
* Or we look for ...

Next we will see how we can use functions: [Tutorial 2.3 Using Functions](2-3-UsingFunctions.md)