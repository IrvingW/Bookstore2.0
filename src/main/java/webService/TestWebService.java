package webService;

import model.Book;
import model.Order;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Produces(MediaType.APPLICATION_JSON)
public interface TestWebService {
    @GET
    @Path(value = "/showAllBooks")
    List<Book> showAllBooks();

    @GET
    @Path(value = "/showMyOrders/{userID}")
    List<Order> showMyOrders(@PathParam("userID") int userID);

    @GET
    @Path(value = "/showAllOrders")
    List<Order> showAllOrders();
}
