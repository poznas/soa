package com.agh.soa.parking.wsdl;

import static javax.jws.soap.SOAPBinding.Style.RPC;

import com.agh.soa.parking.impl.crud.ParkingSpaceService;
import java.util.Map;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.validation.constraints.NotNull;

@WebService
@SOAPBinding(style = RPC)
public class ParkingSpaceWebService {

  @Inject
  ParkingSpaceService spaceService;

  @WebMethod
  public void putSpaceOccupation(@NotNull @WebParam(name = "zoneId") Long zoneId,
                                 @NotNull @WebParam(name = "spaceId") Long spaceId,
                                 @NotNull @WebParam(name = "occupied") Boolean occupied) {
    spaceService.applyPatches(zoneId, spaceId, Map.of("occupied", occupied));
  }

}
