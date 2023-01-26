package threading;

import static java.lang.Thread.currentThread;

public class threadDistributor implements Runnable{

    private Integer indexval;

    public threadDistributor(Integer indexval) {
        this.indexval = indexval;
    }

    public Integer getIndexval() {
        return indexval;
    }

    public void setIndexval(Integer indexval) {
        this.indexval = indexval;
    }

    @Override
    public void run() {
        System.out.println("The value sent as a param is "+indexval);
        System.out.println("Thread executing value is  "+currentThread().getId()
        );
    }
}
