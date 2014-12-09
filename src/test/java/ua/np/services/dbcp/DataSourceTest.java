package ua.np.services.dbcp;

import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;
import org.osgi.framework.ServiceReference;

import java.sql.Connection;
import java.util.Dictionary;
import javax.sql.DataSource;


/**
 * Created by zabuga.m on 10.08.2014.
 */
public class DataSourceTest extends CamelBlueprintTestSupport {

    @Test
    public void testGetConnection() throws Exception {
        ServiceReference serviceReference = getBundleContext().getServiceReference(DataSource.class);
        assertNotNull(serviceReference);

        DataSource dataSource = (DataSource) getBundleContext().getService(serviceReference);
        assertNotNull(dataSource);

        Connection connection = dataSource.getConnection();
        assertNotNull(connection);

        connection.close();
    }

    @Override
    protected String getBlueprintDescriptor() {
        return "/OSGI-INF/blueprint/blueprint.xml,/OSGI-INF/blueprint/camelContext.xml";
    }

    @Override
    protected String[] loadConfigAdminConfigurationFile() {
        return new String[] {"src/main/fabric8/localhost/ua.np.services.dbcp.snp.properties", "ua.np.services.dbcp.snp"
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String useOverridePropertiesWithConfigAdmin(Dictionary props) throws Exception {
        props.put("ua.np.services.dbcp.snp.driverClassName", "org.hsqldb.jdbc.JDBCDriver");
        props.put("ua.np.services.dbcp.snp.jdbcUrl", "jdbc:hsqldb:mem:testdb");
        props.put("ua.np.services.dbcp.snp.username", "sa");
        props.put("ua.np.services.dbcp.snp.password", "");

        return "ua.np.services.dbcp.snp";
    }

}
