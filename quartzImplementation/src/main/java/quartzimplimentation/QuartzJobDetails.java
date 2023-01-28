package quartzimplimentation;

import DAO.JdbcOracleConnection;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLOutput;

@DisallowConcurrentExecution
public class QuartzJobDetails implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Inside QuartzJobDetails execute");
        JdbcOracleConnection jdbcOracleConnection = new JdbcOracleConnection();
        jdbcOracleConnection.processMessage();
        System.out.println("done QuartzJobDetails execute");
    }
}
