
package utils;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author TNM
 */
public class MyConnection {
    public static Connection getConnection() throws Exception {
        Connection con = null;
        Context context = new InitialContext();
        Context envContext = (Context)context.lookup("java:comp/env");
        DataSource ds = (DataSource) envContext.lookup("DBConnect");
        con = ds.getConnection();
        return con;
    }
}
