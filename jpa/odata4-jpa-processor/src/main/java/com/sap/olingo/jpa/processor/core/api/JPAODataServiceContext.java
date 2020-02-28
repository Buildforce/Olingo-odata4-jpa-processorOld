package com.sap.olingo.jpa.processor.core.api;

import com.sap.olingo.jpa.metadata.api.JPAEdmMetadataPostProcessor;
import com.sap.olingo.jpa.metadata.api.JPAEdmProvider;
import com.sap.olingo.jpa.metadata.api.JPAEntityManagerFactory;
import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEdmNameBuilder;
import com.sap.olingo.jpa.metadata.core.edm.mapper.impl.JPADefaultEdmNameBuilder;
import com.sap.olingo.jpa.processor.core.database.JPADefaultDatabaseProcessor;
import com.sap.olingo.jpa.processor.core.database.JPAODataDatabaseOperations;
import com.sap.olingo.jpa.processor.core.database.JPAODataDatabaseProcessorFactory;
import com.sap.olingo.jpa.processor.core.exception.ODataJPAFilterException;
import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.debug.DebugSupport;
import org.apache.olingo.server.api.processor.ErrorProcessor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public final class JPAODataServiceContext implements JPAODataCRUDContext, JPAODataCRUDContextAccess {
  //@formatter:off
  @Deprecated
  private             JPAODataGetHandler jpaODataGetHandler;

  private      JPAODataDatabaseProcessor databaseProcessor;
  private Optional<EntityManagerFactory> emf;
  private                 ErrorProcessor errorProcessor;
  private           JPACUDRequestHandler jpaCUDRequestHandler;
  private                 JPAEdmProvider jpaEdm;
  private                         String mappingPath;
  private                           String namespace;
  private     JPAODataDatabaseOperations operationConverter;
  private                       String[] packageName;
  private         JPAODataPagingProvider pagingProvider;
  private    JPAEdmMetadataPostProcessor postProcessor;
  private            List<EdmxReference> references = new ArrayList<>();
  //@formatter:on

  public static Builder with() {
    return new Builder();
  }

  public JPAODataServiceContext(DataSource ds, String pU, String model) throws ODataException {
    Optional<EntityManagerFactory> emf;

    this.namespace = pU;
    JPADefaultEdmNameBuilder nameBuilder = new JPADefaultEdmNameBuilder(pU);
    this.packageName = new String[]{model};

    emf = Optional.ofNullable(JPAEntityManagerFactory.getEntityManagerFactory(namespace, ds));
   if (emf.isPresent())
     jpaEdm = new JPAEdmProvider(emf.get().getMetamodel(), postProcessor, packageName, nameBuilder);

     try {
      if (databaseProcessor == null)
        databaseProcessor = new JPAODataDatabaseProcessorFactory().create(ds);
    } catch (SQLException | PersistenceException e) {
      throw new ODataJPAFilterException(e, HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

  }




    /**
     * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
     * instead
     */
  @Deprecated
  JPAODataServiceContext(JPAODataGetHandler jpaODataGetHandler) throws ODataException {
    this.jpaODataGetHandler = jpaODataGetHandler;
    namespace = jpaODataGetHandler.namespace;
    operationConverter = new JPADefaultDatabaseProcessor();
    try {
      databaseProcessor = new JPAODataDatabaseProcessorFactory().create(this.jpaODataGetHandler.ds);
    } catch (SQLException e) {
      throw new ODataJPAFilterException(e, HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  private JPAODataServiceContext(final Builder builder) {
    this.jpaODataGetHandler = null;
    databaseProcessor = builder.databaseProcessor;
    emf = builder.builderEmf;
    errorProcessor = builder.errorProcessor;
    jpaCUDRequestHandler = null;

    jpaEdm = builder.builderJpaEdm;
    mappingPath = builder.mappingPath;
    namespace = builder.namespace;
    operationConverter = builder.operationConverter;
    packageName = builder.packageName;

    pagingProvider = builder.pagingProvider;
    postProcessor = builder.postProcessor;
    references = builder.references;
  }

  @Override
  public JPACUDRequestHandler getCUDRequestHandler() {
    return jpaCUDRequestHandler;
  }

  @Override
  public JPAODataDatabaseProcessor getDatabaseProcessor() {
    return databaseProcessor;
  }

  @Override
  public JPAEdmProvider getEdmProvider() throws ODataException {
    if (jpaEdm == null && jpaODataGetHandler != null && jpaODataGetHandler.jpaMetamodel != null)
      jpaEdm = new JPAEdmProvider(namespace, jpaODataGetHandler.jpaMetamodel, postProcessor, packageName);
    return jpaEdm;
  }

  public JPAEdmProvider getEdmProvider(final EntityManager em) throws ODataException {
    if (jpaEdm == null) jpaEdm = new JPAEdmProvider(namespace, em.getMetamodel(), postProcessor, packageName);
    return jpaEdm;
  }

  @Override
  public Optional<EntityManagerFactory> getEntityManagerFactory() {
    return emf;
  }

  @Override
  public ErrorProcessor getErrorProcessor() {
    return errorProcessor == null ? new JPADefaultErrorProcessor() : errorProcessor;
  }

  @Override
  public JPAODataDatabaseOperations getOperationConverter() {
    return operationConverter;
  }

  @Override
  public String[] getPackageName() {
    return packageName;
  }

  @Override
  public JPAODataPagingProvider getPagingProvider() {
    return pagingProvider;
  }

  @Override
  public List<EdmxReference> getReferences() {
    return references;
  }

  @Override
  public String getMappingPath() {
    return mappingPath;
  }

  /**
   * @deprecated will be removed with 1.0.0;
   */
  @Deprecated
  @Override
  public void initDebugger(String debugFormat) {
    // method deprecated
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setCUDRequestHandler(JPACUDRequestHandler jpaCUDRequestHandler) {
    this.jpaCUDRequestHandler = jpaCUDRequestHandler;
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setDatabaseProcessor(final JPAODataDatabaseProcessor databaseProcessor) {
    this.databaseProcessor = databaseProcessor;
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setDebugSupport(final DebugSupport jpaDebugSupport) {
    // this.debugSupport = new JPADebugSupportWrapper(jpaDebugSupport);
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setErrorProcessor(ErrorProcessor errorProcessor) {
    this.errorProcessor = errorProcessor;
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setMetadataPostProcessor(final JPAEdmMetadataPostProcessor postProcessor) throws ODataException {
    if (jpaODataGetHandler.jpaMetamodel != null)
      jpaEdm = new JPAEdmProvider(jpaODataGetHandler.namespace, jpaODataGetHandler.jpaMetamodel, postProcessor, packageName);
    else
      this.postProcessor = postProcessor;
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setOperationConverter(final JPAODataDatabaseOperations jpaOperationConverter) {
    operationConverter = jpaOperationConverter;
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setPagingProvider(final JPAODataPagingProvider provider) {
    pagingProvider = provider;
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setReferences(final List<EdmxReference> references) {
    this.references = references;
  }

  /**
   * @deprecated will be removed with 1.0.0; use newly created builder (<code>JPAODataServiceContext.with()</code>)
   * instead
   */
  @Deprecated
  @Override
  public void setTypePackage(final String... packageName) {
    this.packageName = packageName;
  }

  public static class Builder {
    // format: OFF
    private      JPAODataDatabaseProcessor databaseProcessor;
    private                     DataSource builderDs;
    private Optional<EntityManagerFactory> builderEmf = Optional.empty();
    private                 ErrorProcessor errorProcessor;
    private                 JPAEdmProvider builderJpaEdm;

    private                         String mappingPath;
    private              JPAEdmNameBuilder nameBuilder;
    private                         String namespace;
    private     JPAODataDatabaseOperations operationConverter = new JPADefaultDatabaseProcessor();
    private                       String[] packageName;

    private         JPAODataPagingProvider pagingProvider;
    private    JPAEdmMetadataPostProcessor postProcessor;
    private            List<EdmxReference> references = new ArrayList<>();
    // format: ON

    public JPAODataCRUDContextAccess build() throws ODataException {
        if (packageName == null) packageName = new String[0];
        if (nameBuilder == null) nameBuilder = new JPADefaultEdmNameBuilder(namespace);
        if (!(builderEmf.isPresent() || builderDs == null || namespace == null))
          builderEmf = Optional.ofNullable(JPAEntityManagerFactory.getEntityManagerFactory(namespace, builderDs));
        if (builderEmf.isPresent())
          builderJpaEdm = new JPAEdmProvider(builderEmf.get().getMetamodel(), postProcessor, packageName, nameBuilder);
      try {
        if (databaseProcessor == null)
          databaseProcessor = new JPAODataDatabaseProcessorFactory().create(builderDs);
      } catch (SQLException | PersistenceException e) {
        throw new ODataJPAFilterException(e, HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      return new JPAODataServiceContext(this);
    }

    /**
     * A database processor allows database specific implementations for search and odata function with function import
     * that are implemented as database functions.<br>
     * In case no database processor is provided and non could be determined via an data source
     * {@link JPADefaultDatabaseProcessor} is used.
     * @param databaseProcessor
     * @return
     */
    public Builder setDatabaseProcessor(final JPAODataDatabaseProcessor databaseProcessor) {
      this.databaseProcessor = databaseProcessor;
      return this;
    }

    /**
     * The data source is used to create an entity manager factory if not provided, see
     * {@link Builder#setEntityManagerFactory(EntityManagerFactory)}, and to determine the type of
     * database used to select an integrated database processor, in case the database processor was not set via
     * {@link Builder#setDatabaseProcessor(JPAODataDatabaseProcessor)}}.
     * @param ds
     * @return
     */
    public Builder setDataSource(final DataSource ds) {
      builderDs = ds;
      return this;
    }

    /**
     * Allows to provide an Olingo error processor. The error processor allows to enrich an error response. See
     * <a
     * href="http://docs.oasis-open.org/odata/odata-json-format/v4.0/errata03/os/odata-json-format-v4.0-errata03-os-complete.html#_Toc453766668"
     * >JSON Error Response</a> or
     * <a
     * href="http://docs.oasis-open.org/odata/odata-atom-format/v4.0/cs02/odata-atom-format-v4.0-cs02.html#_Toc372792829">Atom
     * Error Response</a>.
     * @param errorProcessor
     */
    public Builder setErrorProcessor(final ErrorProcessor errorProcessor) {
      this.errorProcessor = errorProcessor;
      return this;
    }

    /**
     *
     * @param postProcessor
     * @return
     */
    public Builder setMetadataPostProcessor(final JPAEdmMetadataPostProcessor postProcessor) {
      this.postProcessor = postProcessor;
      return this;
    }

    /**
     *
     * @param jpaOperationConverter
     * @return
     */
    public Builder setOperationConverter(final JPAODataDatabaseOperations jpaOperationConverter) {
      operationConverter = jpaOperationConverter;
      return this;
    }

    /**
     * Register a provider that is able to decides based on a given query if the server like to return only a sub set of
     * the requested results as well as a $skiptoken.
     * @param provider
     */
    public Builder setPagingProvider(final JPAODataPagingProvider provider) {
      pagingProvider = provider;
      return this;
    }

    /**
     * The name of the persistence-unit to be used. It is taken to create a entity manager factory
     * ({@link Builder#setEntityManagerFactory(EntityManagerFactory)}), if not provided and
     * as namespace of the OData service, in case the default name builder shall be used.
     * @param pUnit
     * @return
     */
    public Builder setPUnit(final String pUnit) {
      namespace = pUnit;
      return this;
    }

    /**
     *
     * @param references
     * @return
     */
    public Builder setReferences(final List<EdmxReference> references) {
      this.references = references;
      return this;
    }

    /**
     * Name of the top level package to look for
     * <ul>
     * <li> Enumeration Types
     * <li> Java class based Functions
     * </ul>
     * @param packageName
     */
    public Builder setTypePackage(final String... packageName) {
      this.packageName = packageName;
      return this;
    }

    public Builder setRequestMappingPath(final String mappingPath) {
      this.mappingPath = mappingPath;
      return this;
    }

    /**
     * Set an externally created entity manager factory.<br>
     * This is necessary e.g. in case a spring based service shall run without a <code>persistence.xml</code>.
     * @param emf
     * @return
     */
    public Builder setEntityManagerFactory(final EntityManagerFactory emf) {
      builderEmf = Optional.of(emf);
      return this;
    }

    /**
     * Set a custom EDM name builder {@link JPAEdmNameBuilder}. If non is provided {@link JPADefaultEdmNameBuilder} is
     * used, which uses the provided persistence-unit name ({@link JPAODataServiceContext.Builder#setPUnit}) as
     * namespace.
     * @param nameBuilder
     * @return
     */
    public Builder setEdmNameBuilder(final JPAEdmNameBuilder nameBuilder) {
      this.nameBuilder = nameBuilder;
      return this;
    }
  }

/*  static class JPADebugSupportWrapper implements DebugSupport {

    private final DebugSupport debugSupport;
    private JPAServiceDebugger debugger;

    public JPADebugSupportWrapper(final DebugSupport debugSupport) {
      super();
      this.debugSupport = debugSupport;
    }

    *//*
     * (non-Javadoc)
     *

     * @see org.apache.olingo.server.api.debug.DebugSupport#createDebugResponse(java.lang.String,
     * org.apache.olingo.server.api.debug.DebugInformation)
     *//*
    @Override
    public ODataResponse createDebugResponse(final String debugFormat, final DebugInformation debugInfo) {
      joinRuntimeInfo(debugInfo);
      return debugSupport.createDebugResponse(debugFormat, debugInfo);
    }

    *//*
     * (non-Javadoc)
     *

     * @see org.apache.olingo.server.api.debug.DebugSupport#init(org.apache.olingo.server.api.OData)
     *//*
    @Override
    public void init(final OData odata) {
      debugSupport.init(odata);
    }

    *//*
     * (non-Javadoc)
     *

     * @see org.apache.olingo.server.api.debug.DebugSupport#isUserAuthorized()
     *//*
    @Override
    public boolean isUserAuthorized() {
      return debugSupport.isUserAuthorized();
    }

    void setDebugger(final JPAServiceDebugger debugger) {
      this.debugger = debugger;
    }

    private void joinRuntimeInfo(final DebugInformation debugInfo) {
      // Olingo create a tree for runtime measurement in DebugTabRuntime.add(final RuntimeMeasurement
      // runtimeMeasurement). The current algorithm (V4.3.0) not working well for batch requests if the own runtime info
      // is just appended (addAll), so insert sorted:
      final List<RuntimeMeasurement> olingoInfo = debugInfo.getRuntimeInformation();
      int startIndex = 0;
      for (RuntimeMeasurement m : debugger.getRuntimeInformation()) {
        for (; startIndex < olingoInfo.size(); startIndex++) {
          if (olingoInfo.get(startIndex).getTimeStarted() > m.getTimeStarted()) {
            break;
          }
        }
        olingoInfo.add(startIndex, m);
        startIndex += 1;
      }
    }
  }*/
}