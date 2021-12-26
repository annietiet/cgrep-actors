/**
 * Contains a count of the number of files being scanned
 * Immutable message object
 */
public class FileCount {
    private final int numFiles;

    /**
     * Constructor
     * @param numFiles count of the number of files being scanned
     */
    public FileCount(int numFiles){
        this.numFiles = numFiles;
    }

    /**
     * Getter for numFiles
     * @return int numFiles
     */
    public int getNumFiles() {
        return numFiles;
    }
}
