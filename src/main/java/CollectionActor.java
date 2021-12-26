import akka.actor.UntypedAbstractActor;

import java.util.List;

/**
 * Receives two types of messages.
 * 1) FileCount - count of the number of files being scanned
 * 2) Found - printed by Actor (file name and list of matching lines)
 * Shut down all actors after all Found messages have been processed
 */
public class CollectionActor extends UntypedAbstractActor {
    private int numFiles;

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        if(message instanceof FileCount){   // identify message type
            numFiles = ((FileCount) message).getNumFiles();
        } else if (message instanceof Found){
            numFiles--;
            Found found = (Found) message;
            for(String line: found.getMatching()){
                System.out.println(found.getFileName() + "\t" + line);
            }

        } else {
            System.out.println("Unexpected message type identified");
        }

        if(numFiles <= 0) getContext().system().terminate(); // Graceful shutdown
    }
}
