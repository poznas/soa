package com.agh.soa.parking.service;

import com.agh.soa.parking.exception.ActiveSessionException;
import javax.validation.constraints.NotNull;

public interface ISessionContextService {

  void registerSession(@NotNull String sessionId) throws ActiveSessionException;

  void unregisterSession();

}
