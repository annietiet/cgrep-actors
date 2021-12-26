import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Searches file / standard input for occurrences of the regex pattern
 * Receives exactly one immutable Configure message
 * Constructs immutable Found object
 */
public class ScanActor extends UntypedAbstractActor {
    private final Pattern pattern;

    public ScanActor(Pattern pattern){
        this.pattern = pattern;
    }

    @Override
    public void onReceive(Object message) throws Throwable, Throwable {
        if(message instanceof Configure){
            Configure configure = (Configure) message;
            String fileName = configure.getFileName();
            ActorRef collectionActor = configure.getCollectionActor();

            FileReader in;
            BufferedReader br;

            if(fileName.equals("-")){ // standard input
                br = new BufferedReader(new InputStreamReader(System.in));
                collectionActor.tell(scanReader(pattern, "-", br), getSelf());
                br.close();
            } else {    // files provided
                if(fileName.endsWith(".txt")){
                    in = new FileReader(fileName);
                    br = new BufferedReader(in);
                    collectionActor.tell(scanReader(pattern, fileName, br), getSelf());
                    in.close();
                    br.close();
                }
            }
            getContext().stop(getContext().self());
        } else {
            System.out.println("Unexpected message type identified");
        }
    }

    public Found scanReader(Pattern pattern, String fileName, BufferedReader br) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            Matcher match = pattern.matcher(line);
            String patternStr = pattern.toString();

            if(patternStr.charAt(0) == '^' && patternStr.endsWith("$")){
                // anchored - match ENTIRE line
                if(match.matches()) lines.add(line);
            } else {
                // not anchored - match ANY part
                if(match.find()) lines.add(line);
            }
        }
        return new Found(fileName, lines);
    }
}
