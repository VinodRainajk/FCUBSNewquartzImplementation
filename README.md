# FCUBSNewquartzImplementation

This is a sample program in which we have tried to implement the Notification and Quartz.

In Flexcube most of Notifications are Quartz based. The issue in scaling the Notification is the problem with a dedicated quartz session. If the volume is huge the Session should be fast and we should enable multithreading. Here a quartz will trigger a job that job will trigger multithreading using a executor service. Finally we have used the Callable to update the status.

Below are few changes that I will recommend you to use if You are planning to use in Production 
1) Go with a datasource configuration and get DB session from the pooled Datasource.
2) You dont need hibernate. You can remove the part and add manual DB open and close.
3) When you use the step 3, once you identify the records update it. So your query will be update custom table set Guid =  xxx where Guid is null. This way you will avoid unecessary complication of hibernate which first runs a select query and the you can update.
4) Use a log4j for logging wherever necessary it is upto you.
5) finally add your code for queue or Kafka whatever is the bank recomendations.

Best Of luck.
