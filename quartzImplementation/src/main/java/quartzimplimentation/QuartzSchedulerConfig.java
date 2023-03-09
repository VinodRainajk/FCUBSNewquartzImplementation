package quartzimplimentation;

import ExecutorService.ExecutorProperties;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzSchedulerConfig {

    private static final String TRIGGER_NAME = "FCUBSTrigger";
    private static final String GROUP = "NotificationGroup";
    private static final String JOB_NAME = "NotificationTrigger";
    private static Scheduler scheduler;
    public static void main(String[] args) throws Exception {
        System.out.println(" QuartzSchedulerApp main thread: " + Thread.currentThread().getName());
        ExecutorProperties properties = new ExecutorProperties();
        SchedulerFactory sf = new StdSchedulerFactory(properties.readPropertiesFile("src/main/resources/quartz.properties"));
        scheduler = sf.getScheduler();
        scheduler.start();
        Trigger trigger =  buildSimpleSchedulerTrigger();
        scheduleJob(trigger);

    }


    private static void scheduleJob(Trigger trigger) throws Exception {

        JobDetail Notifjobdetails = JobBuilder.newJob(QuartzSchedulerExecutor.class).withIdentity(JOB_NAME, GROUP).build();

        scheduler.scheduleJob(Notifjobdetails, trigger);

    }

    private static Trigger buildSimpleSchedulerTrigger() {

        int INTERVAL_SECONDS = 5;

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, GROUP)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(INTERVAL_SECONDS).repeatForever())
                .build();
        return trigger;
    }
}
