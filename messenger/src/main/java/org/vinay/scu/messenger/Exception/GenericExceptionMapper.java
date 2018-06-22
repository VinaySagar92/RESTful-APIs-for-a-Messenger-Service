package org.vinay.scu.messenger.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.vinay.scu.messenger.model.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage eM = new ErrorMessage(500, ex.getMessage(), "www.linkedin.com/vinay92");
		return Response.status(Status.INTERNAL_SERVER_ERROR).
						entity(eM).
						build();
	}
	
}
