package nl.buildforce.sequoia.jpa.processor.core.api;

import nl.buildforce.sequoia.jpa.metadata.api.JPAEdmProvider;
import nl.buildforce.sequoia.jpa.processor.core.database.JPADefaultDatabaseProcessor;
import nl.buildforce.sequoia.jpa.processor.core.database.JPAODataDatabaseOperations;
import nl.buildforce.sequoia.jpa.processor.core.database.JPAODataDatabaseProcessorFactory;
import org.apache.olingo.commons.api.edmx.EdmxReference;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class JPAODataContextAccessDouble implements JPAODataCRUDContextAccess {
  private final JPAEdmProvider edmProvider;
  private final DataSource ds;
  private final JPAODataDatabaseOperations context;
  private final String[] packageNames;
  private final JPAODataPagingProvider pagingProvider;

  public JPAODataContextAccessDouble(final JPAEdmProvider edmProvider, final DataSource ds,
      final JPAODataPagingProvider provider, final String... packages) {
    this.edmProvider = edmProvider;
    this.ds = ds;
    this.context = new JPADefaultDatabaseProcessor();
    this.packageNames = packages;
    this.pagingProvider = provider;
  }

  @Override
  public List<EdmxReference> getReferences() {
    fail();
    return null;
  }

  @Override
  public JPAODataDatabaseOperations getOperationConverter() {
    return context;
  }

  @Override
  public JPAEdmProvider getEdmProvider() {
    return edmProvider;
  }

  @Override
  public JPAODataDatabaseProcessor getDatabaseProcessor() {
    try {
      return new JPAODataDatabaseProcessorFactory().create(ds);
    } catch (SQLException e) {
      fail();
    }
    return null;
  }

  @Override
  public JPACUDRequestHandler getCUDRequestHandler() {
    fail();
    return null;
  }

  @Override
  public String[] getPackageName() {
    return packageNames;
  }

  @Override
  public JPAODataPagingProvider getPagingProvider() {
    return pagingProvider;
  }
}