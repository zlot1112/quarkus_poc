package com.fss;

import com.fss.entity.boards;
import com.fss.request.UpdateBoard;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Slf4j
@Path("/board")
public class BoardResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> addPost(boards post) {
        System.out.println(post.toString());
        return post.<boards>persist().map(v ->
                Response.created(URI.create("/board/" + v.id.toString()))
                        .entity(post).build());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<boards> update(@PathParam("id") String id, UpdateBoard updateBoard) {
        return boards.updateBoard(id, updateBoard);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<boards> getPost(@PathParam("id") String id) {
        return boards.findById(new ObjectId(id));
    }

}