import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.regex.Pattern;

public class CGrep {
    /**
     * Create a CollectionActor, start it, and send the FileCount message to it.
     * Create one ScanActor per file, start it, and send the appropriate Configure message to each such actor.
     */
    public static void main(String[] args) {
        if (args.length < 1) { // need to provide a Pattern (at least 1 arg)
            System.out.println("Program will be invoked on the command line as follows:");
            System.out.println("java CGrep pattern [file . . .]");
        } else {
            Pattern pattern = Pattern.compile(args[0]);
            ActorSystem system = ActorSystem.create();
            // Add actors to the system. Actors will start as soon as they are added
            // Create a CollectionActor, start it, and send the FileCount message to it
            ActorRef collectionActor = system.actorOf(Props.create(CollectionActor.class));
            if(args.length == 1){ // standard input = 1 file
                collectionActor.tell(new FileCount(1), ActorRef.noSender());
                // one file (std input) so 1 ScanActor and sent message
                ActorRef scanActor = system.actorOf(Props.create(ScanActor.class, pattern));
                scanActor.tell(new Configure("-", collectionActor), ActorRef.noSender());
            } else {
                collectionActor.tell(new FileCount(args.length - 1), ActorRef.noSender());
                // Create one ScanActor per file, start it, and send the appropriate Configure message to each such actor.
                for (int i = 1; i < args.length; i++) { // remaining arguments = text files
                    ActorRef scanActor = system.actorOf(Props.create(ScanActor.class, pattern));
                    scanActor.tell(new Configure(args[i], collectionActor), ActorRef.noSender());
                }
            }
        }
    }
}
