import android.content.Context;
import java.util.concurrent.ExecutionException;

public class AddBidCommand extends Command {

    private BidList bid_list;
    private Bid bid;
    private Context context;

    public AddBidCommand(Bid bid) { this.bid = bid; }



    // Save the item remotely to server
    public void execute(){
        ElasticSearchManager.AddBidTask add_bid_task = new ElasticSearchManager.AddBidTask();
        add_bid_task.execute(bid);


        try {
            if(add_bid_task.get()) {
                bid_list.addBid(bid);
                super.setIsExecuted(true);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            super.setIsExecuted(false);
        }
