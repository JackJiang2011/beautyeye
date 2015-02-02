/*
 * Copyright 2007-2008 Sun Microsystems, Inc.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sun.swingset3.demos.table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Class used to support converting a movie title string into an IMDB URI
 * corresponding to that movie's IMDB entry.   Since IMDB encodes entries with
 * an alpha-numeric key (rather than title), we have to use Yahoo search on the
 * title and then screenscrape the search results to find the IMDB key.
 *
 * @author aim
 */
public class IMDBLink {

    private IMDBLink() {
    }

    /**
     * @param movieTitle the title of the movie
     * @param year       the year the movie was nominated for the oscar
     * @return String containing URI for movie's IMDB entry or null if URI could not be found
     */
    public static String getMovieURIString(String movieTitle, int year) throws IOException {
        ArrayList<String> matches = new ArrayList<String>();
        URL url;
        BufferedReader reader;

        // btw, google rejects the request with a 403 return code!
        // URL url = new URL("http://www.google.com/search?q=Dazed+and+confused");
        // Thank you, yahoo, for granting our search request :-)
        try {
            String urlKey = URLEncoder.encode(movieTitle, "UTF-8");
            url = new URL("http://search.yahoo.com/search?ei=utf-8&fr=sfp&p=imdb+" +
                    urlKey + "&iscqry=");
        } catch (Exception ex) {
            System.err.println(ex);
            
            return null;
        }

        URLConnection conn = url.openConnection();
        conn.connect();

        // Get the response from Yahoo search query
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        // Parse response a find each imdb/titleString result
        String line;
        String imdbString = ".imdb.com";
        String titleStrings[] = {"/title", "/Title"};

        while ((line = reader.readLine()) != null) {
            for (String titleString : titleStrings) {
                String scrapeKey = imdbString + titleString;
                int index = line.indexOf(scrapeKey);
                if (index != -1) {
                    // The IMDB key looks something like "tt0032138"
                    // so we look for the 9 characters after the scrape key 
                    // to construct the full IMDB URI.
                    // e.g. http://www.imdb.com/title/tt0032138
                    int len = scrapeKey.length();
                    String imdbURL = "http://www" +
                            line.substring(index, index + len) +
                            line.substring(index + len, index + len + 10);

                    if (!matches.contains(imdbURL)) {
                        matches.add(imdbURL);
                    }
                }
            }
        }
        reader.close();

        // Since imdb contains entries for multiple movies of the same titleString,
        // use the year to find the right entry
        if (matches.size() > 1) {
            for (String matchURL : matches) {
                if (verifyYear(matchURL, year)) {
                    return matchURL;
                }
            }
        }
        return matches.isEmpty()? null : matches.get(0);
    }


    private static boolean verifyYear(String imdbURL, int movieYear) throws IOException {
        boolean yearMatches = false;

        URLConnection conn = new URL(imdbURL).openConnection();
        conn.connect();

        // Get the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            int index = line.indexOf("</title>");
            if (index != -1) {
                // looking for "<title>movie title (YEAR)</title>"                
                try {
                    int year = Integer.parseInt(line.substring(index - 5, index - 1));
                    // Movie may have been made the year prior to oscar award
                    yearMatches = year == movieYear || year == movieYear - 1;

                } catch (NumberFormatException ex) {
                    // ignore title lines that have other formatting
                }
                break; // only interested in analyzing the one line
            }
        }
        reader.close();

        return yearMatches;
    }
}