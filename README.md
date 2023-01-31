# FCUBSNewquartzImplementation

This is a sample program in which we have tried to implement the Notification and Quartz.

The Idea here is to show case how we can move out of a dedicated session of quartz.
The quartz will just trigger a job and will then come out. 
The job once triggered will fetch all the records and will start processing those.
Once all are processed we can then get the status and update it in a single session.
We could have updated the respective notification status in the Thread itself but then we will have connection restriction. That will cause us to limit thread.

This is the reason to use Future.

In most of the application the Quartz is implemented in such a manner that till the time session does not complete processing we will be stuck with the session and 
Quartz will not process anything else. TO overcome that we have done this POC
