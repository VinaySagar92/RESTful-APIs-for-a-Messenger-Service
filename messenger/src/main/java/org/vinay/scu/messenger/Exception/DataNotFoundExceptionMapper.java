package org.vinay.scu.messenger.Exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.vinay.scu.messenger.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage eM = new ErrorMessage(404, ex.getMessage(), "www.linkedin.com/vinay92");
		return Response.status(Status.NOT_FOUND).
						entity(eM).
						build();
	}
	
}
