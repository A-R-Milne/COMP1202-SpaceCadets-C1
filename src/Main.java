import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.lang.NullPointerException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, NullPointerException { //URISyntaxException is for new URI, IOException and NullPointerException are for getName
        Scanner getInput = new Scanner(System.in); //Simpler way to input than using BufferedReader - as this is small scale, speed is not a significant concern
        System.out.println("Input email ID: ");
        String id = getInput.nextLine(); //Lecturers and Lab Runners: tsh2n14, js9g09, mds1u19, hsp2m10
        String webAddress = "https://www.ecs.soton.ac.uk/people/" + id;
        URL webPage = new URI(webAddress).toURL(); // Create URL via URI, as creating one using new URL() was deprecated in 2023

        String line = getName(webPage);
        line = line.replace("            \"name\": \"","").replace("\",",""); //Replacing the bits on each side of the name itself with an empty string each to delete them
        System.out.println(line);
    }

    private static String getName(URL webPage) throws IOException, NullPointerException { //IOException is for toURL, openStream and readLine, NullPointerException is for contains
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(webPage.openStream())); //Had to find how to create a BufferedReader from the internet
        boolean humanFound = false;
        boolean nameFound = false;
        String line = "";
        while (!humanFound) {
            line = buffRead.readLine();
            humanFound = line.contains("\"@type\": \"Person\""); //There are multiple names in the page, so we make sure we're looking at the actual person and not something else when we get the name
        }
        while (!nameFound) {
            line = buffRead.readLine();
            nameFound = line.contains("\"name\""); //This line contains their name
        }
        return line;
    }
}