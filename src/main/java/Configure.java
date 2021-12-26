import akka.actor.ActorRef;

/**
 * Immutable message object
 */
public class Configure {
    private final String fileName;
    private final ActorRef collectionActor;

    /**
     * Constructor
     * @param fileName string of file name
     * @param collectionActor reference to CollectionActor which collects and prints scan results
     */
    public Configure(String fileName, ActorRef collectionActor){
        this.fileName = fileName;
        this.collectionActor = collectionActor;
    }

    /**
     * Getter for fileName
     * @return String fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Getter for collectionActor
     * @return ActorRef collectionActor
     */
    public ActorRef getCollectionActor() {
        return collectionActor;
    }
}
