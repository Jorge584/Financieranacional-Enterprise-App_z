package co.com.cfn.personas.integration.service.rest;

import co.com.cfn.foundation.canonical.personas.TipoIdentificacionDTO;
import co.com.cfn.foundation.framework.components.builder.Mapper;
import co.com.cfn.foundation.framework.exceptions.BusinessException;
import co.com.cfn.foundation.framework.exceptions.SystemException;
import co.com.cfn.personas.business.boundary.TipoIdentificacionManager;
import co.com.cfn.personas.domain.entity.TipoIdentificacion;
import co.com.cfn.personas.infrastructure.mapper.MapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by zmiranda on 23/03/2017.
 */

@Path("/tipoIdentificacionResouce")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
public class TipoIdentificacionResource {

    private static Logger LOGGER = LogManager.getLogger(TipoIdentificacionResource.class.getName());

    //[fields] -----------------------------

    @Autowired
    private TipoIdentificacionManager boundary;
    private final Mapper<TipoIdentificacionDTO, TipoIdentificacion> dtoToEntitymapper;
    private final Mapper<List<TipoIdentificacion>, List<TipoIdentificacionDTO>> entityToDtoMapper;

    //[constructor] -----------------------------

    public TipoIdentificacionResource() {
        dtoToEntitymapper = MapperFactory.getInstance().getMapper(MapperFactory.TIPOIDENTIFICACION_DTO_TO_ENTITY);
        entityToDtoMapper = MapperFactory.getInstance().getMapper(MapperFactory.TIPOIDENTIFICACION_ENTITY_TO_DTO);

    }

    //[service] -----------------------------

    @GET
    @Path("/")
    public Response listartipoIdentificacion() {

        try {

            LOGGER.info("processing soap request - listartipoIdentificacion ");

            List<TipoIdentificacionDTO> TipoIdentificacionList;

            TipoIdentificacionList = entityToDtoMapper.map(boundary.listarTipoIdentificacion());

            return Response.ok(new GenericEntity<List<TipoIdentificacionDTO>>(TipoIdentificacionList) {}).build();

        } catch (SystemException | BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
    }

}
