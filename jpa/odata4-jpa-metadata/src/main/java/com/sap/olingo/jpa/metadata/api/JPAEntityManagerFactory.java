package com.sap.olingo.jpa.metadata.api;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


//import static org.eclipse.persistence.config.EntityManagerProperties.NON_JTA_DATASOURCE;
public class JPAEntityManagerFactory {
  public static final String NON_JTA_DATASOURCE = "javax.persistence.nonJtaDataSource";

  private static Map<String, Map<Integer, EntityManagerFactory>> emfMap;

  private JPAEntityManagerFactory() {
    throw new IllegalStateException("JPAEntityManagerFactory class");
  }

  public static EntityManagerFactory getEntityManagerFactory(final String pUnit, final Map<String, Object> ds) {
    if (pUnit == null) {
      return null;
    }
    if (emfMap == null) {
      emfMap = new HashMap<>();
    }
    Integer dsKey = ds.hashCode();
    if (emfMap.containsKey(pUnit)) {
      final Map<Integer, EntityManagerFactory> dsMap = emfMap.get(pUnit);
      EntityManagerFactory emf = dsMap.get(dsKey);

      if (emf != null)
        return emf;
      emf = Persistence.createEntityManagerFactory(pUnit, ds);
      dsMap.put(dsKey, emf);
      return emf;

    } else {
      final Map<Integer, EntityManagerFactory> dsMap = new HashMap<>();
      emfMap.put(pUnit, dsMap);
      final EntityManagerFactory emf = Persistence.createEntityManagerFactory(pUnit, ds);
      dsMap.put(dsKey, emf);
      return emf;
    }
  }

  public static EntityManagerFactory getEntityManagerFactory(final String pUnit, final DataSource ds) {
    final Map<String, Object> properties = new HashMap<>();
    properties.put(NON_JTA_DATASOURCE, ds);
    return getEntityManagerFactory(pUnit, properties);
  }

}