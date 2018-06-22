package org.vinay.scu.messenger.resources;
import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.vinay.scu.messenger.model.Message;
import org.vinay.scu.messenger.resources.bean.MessageFilterBean;
import org.vinay.scu.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService mS = new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJSONMessages(@BeanParam MessageFilterBean fB)
	{
		if(fB.getYear() > 0) return mS.getAllMessagesForYear(fB.getYear());
		if(fB.getStart() > 0 && fB.getSize() > 0) return mS.getAllMessagesPaginated(fB.getStart(), fB.getSize());
		return mS.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXMLMessages(@BeanParam MessageFilterBean fB)
	{
		if(fB.getYear() > 0) return mS.getAllMessagesForYear(fB.getYear());
		if(fB.getStart() > 0 && fB.getSize() > 0) return mS.getAllMessagesPaginated(fB.getStart(), fB.getSize());
		return mS.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo)
	{
		Message newMessage = mS.addMessage(message);
		String id = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(uri).
						entity(newMessage).
						build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message)
	{
		message.setId(id);
		return mS.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public Message deleteMessage(@PathParam("messageId") long id)
	{
		return mS.removeMessage(id);
	}
	 
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo)
	{
		Message message = mS.getMessage(id);
		message.addLinks(getUriForSelf(uriInfo, message), "self");
		message.addLinks(getUriForProfile(uriInfo, message), "profile");
		message.addLinks(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().
					 path(MessageResource.class).
					 path(Long.toString(message.getId())).
					 build().
					 toString();
		return uri;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().
				 path(ProfileResource.class).
				 path(message.getAuthor()).
				 build().
				 toString();
		return uri;
	}
	
	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().
				 path(MessageResource.class).
				 path(MessageResource.class, "getCommentResource").
				 path(CommentResource.class).
				 resolveTemplate("messageId", message.getId()).
				 build().
				 toString();
		return uri;
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource()
	{
		return new CommentResource();
	}
}
