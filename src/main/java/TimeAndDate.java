public class TimeAndDate extends Thread{

    private boolean exit;
    public TimeAndDate(Runnable run){
        exit = false;
    }

    public TimeAndDate(){
        exit = false;
    }

    public void start(){
        while(!exit){
            //TODO: Update label?????
        }
    }

    public void end(){
        exit = true;
    }
}
