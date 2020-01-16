package com.sap.olingo.jpa.processor.core.api;

import java.util.Collection;

import org.apache.olingo.server.api.debug.RuntimeMeasurement;

public interface JPAServiceDebugger {
  int startRuntimeMeasurement(final Object instance, final String methodName);

  void stopRuntimeMeasurement(final int handle);

  Collection<RuntimeMeasurement> getRuntimeInformation();
}