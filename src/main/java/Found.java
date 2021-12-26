import java.util.List;

/**
 * Found objects contain a String with the name of the file that was searched
 * and a List<String> with one entry in the list for each matching line.
 * The one and only message from the ScanActor to the CollectionActor
 */
public class Found {
    private final String fileName;
    private final List<String> matching;

    /**
     * Constructor
     * @param fileName String of file name that was searched
     * @param matching List<String> of one entry in the list for each matching line
     */
    public Found(String fileName, List<String> matching){
        this.fileName = fileName;
        this.matching = matching;
    }

    /**
     * Getter for fileName
     * @return String fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Getter for entryMatching
     * @return List<String> matching
     */
    public List<String> getMatching() {
        return matching;
    }
}