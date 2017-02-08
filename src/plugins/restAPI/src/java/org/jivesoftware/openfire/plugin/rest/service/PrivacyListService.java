package org.jivesoftware.openfire.plugin.rest.service; //rockyprince the file

import javax.annotation.PostConstruct;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.plugin.rest.controller.PrivacyListController;
import org.jivesoftware.openfire.plugin.rest.entity.PrivacyListItem;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;

@Path("restapi/v1/privacy_lists")
public class PrivacyListService {

	private PrivacyListController privacyListController;

	@PostConstruct
	public void init() {
		privacyListController = PrivacyListController.getInstance();
	}

	@POST
	@Path("/{username}/name/{listname}")
	public Response createPrivacyLists(@PathParam("username") String username, @PathParam("listname") String listname, PrivacyListItem privacyListItem) throws ServiceException {
		privacyListController.createPrivacyLists(username, listname, privacyListItem);
		return Response.status(Response.Status.CREATED).build();
	}
        
	@DELETE
	@Path("/{username}/name/{listname}")
	public Response deletePrivacyLists(@PathParam("username") String username, @PathParam("listname") String listname, PrivacyListItem privacyListItem) throws ServiceException {
		privacyListController.deletePrivacyLists(username, listname, privacyListItem);
		return Response.status(Response.Status.OK).build();
	}
    
}
