package org.jivesoftware.openfire.plugin.rest.service; //rockyprince the file

import javax.annotation.PostConstruct;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.plugin.rest.controller.DeviceTokenController;
import org.jivesoftware.openfire.plugin.rest.entity.DeviceTokenEntity;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;

@Path("restapi/v1/device_tokens")
public class DeviceTokenService {

	private DeviceTokenController deviceTokenController;

	@PostConstruct
	public void init() {
		deviceTokenController = DeviceTokenController.getInstance();
	}

	@POST
	public Response createDeviceToken(DeviceTokenEntity deviceTokenEntity) throws ServiceException {
		deviceTokenController.createDeviceToken(deviceTokenEntity);
		return Response.status(Response.Status.CREATED).build();
	}
        
	@DELETE
	@Path("/{username}")
	public Response deleteDeviceToken(@PathParam("username") String username) throws ServiceException {
		deviceTokenController.deleteDeviceToken(username);
		return Response.status(Response.Status.OK).build();
	}
    
}
