/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acme.schooltimetabling.rest;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.schooltimetabling.domain.Room;

import io.quarkus.panache.common.Sort;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class RoomResource {

    @GET
    public List<Room> getAllRooms() {
        return Room.listAll(Sort.by("name").and("id"));
    }

    @POST
    public Response add(Room room) {
        Room.persist(room);
        return Response.accepted(room).build();
    }

    @DELETE
    @Path("{roomId}")
    public Response delete(@PathParam("roomId") Long roomId) {
        Room room = Room.findById(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        room.delete();
        return Response.status(Response.Status.OK).build();
    }

}
