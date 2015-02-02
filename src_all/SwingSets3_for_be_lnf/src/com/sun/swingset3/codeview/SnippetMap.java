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

package com.sun.swingset3.codeview;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.*;

/**
 * Data structure to support maintaining Snippet information across a 
 * set of source code files:
 *
 *                        SnippetMap
 *                             |
 *            ---------------------------------------
 *            |                   |                 |
 *    snippet key "A"       Snippet key "B"     Snippet key "C"
 *          |                     |                 |
 *    ----------------          ------          -------------
 *    |               |              |                   |          
 *  File1.java    File3.java     File2.java          File3.java
 *       |            |              |                   |
 *  ----------       ---            ---                -----
 *  |        |        |              |                   |
 * snipA-1  snipA-2  snipA-3       snipB-1            snipC-1
 *
 *
 * This class also maintains a pointer to a &quot;current&quot; snippet within
 * a selected set.
 *
 *
 * @author aim
 */
public class SnippetMap {
    private final HashMap<String, List<FileSnippets>> snippetSets = new HashMap<String, List<FileSnippets>>();    
    
    private String currentKey;
    private List<FileSnippets> currentSet;
    private FileSnippets currentFileSnippets;
    private int currentFileSnippetsIndex;
    private int currentSnippetIndex;
    private Snippet currentSnippet;
    
    private PropertyChangeSupport pcs;
    
    /** Creates a new instance of SnippetMap */
    public SnippetMap() {
        pcs = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }
    
    public void add(String key, URL codeFile, Snippet snippet) {
        List<FileSnippets> fileSnippetList = snippetSets.get(key);
        if (fileSnippetList == null) {
            // new key! so create new set...
            fileSnippetList = new ArrayList<FileSnippets>();
            snippetSets.put(key, fileSnippetList);
        }
        
        FileSnippets fileSnippets = findFileSnippetsForFile(fileSnippetList, codeFile);
        if (fileSnippets == null) {
            // found first snippet in this file
            fileSnippets = new FileSnippets(key, codeFile);
            fileSnippetList.add(fileSnippets);
        }
        if (!fileSnippets.snippets.contains(snippet)) {
            fileSnippets.snippets.add(snippet);
        }
                                
    }
    
    public Set<String> keySet() {
        return snippetSets.keySet();
    }
    
    public int getSnippetCountForSet(String key) {
        int count = 0;
        URL files[] = getFilesForSet(key);
        for(URL file : files) {
            Snippet snippets[] = getSnippetsForFile(key, file);
            count += snippets.length;
        }
        return count;
    }
    
    public URL[] getFilesForSet(String key) {
        List<FileSnippets> fileSnippetList = snippetSets.get(key);
        URL files[];
        if (fileSnippetList != null) {
            files = new URL[fileSnippetList.size()];
            int i = 0;
            for(FileSnippets fileSnippets : fileSnippetList) {
                files[i++] = fileSnippets.file;
            }
        } else {
            files = new URL[0];
        }
        return files;
    }
    
    public Snippet[] getSnippetsForFile(String key, URL file) {
        List<FileSnippets> fileSnippetList = snippetSets.get(key);
        FileSnippets fileSnippets = findFileSnippetsForFile(fileSnippetList, file);
        if (fileSnippets != null) {          
            if (fileSnippets.snippets != null) {
                return fileSnippets.snippets.toArray(new Snippet[0]);
            } 
        }
        return new Snippet[0];        
    }
    
    public int getIndexForSnippet(Snippet snippet) {
        List<FileSnippets> fileSnippetList = snippetSets.get(snippet.key); 
        if (fileSnippetList != null) {
            int index = 1;
            for(FileSnippets fileSnippets : fileSnippetList) {
                for(Snippet snippetInFile : fileSnippets.snippets) {
                    if (snippetInFile == snippet) {
                        return index;
                    } else {
                        index++;
                    }
                }
            }
        }
        return -1;
    }
    
    public URL getFileForSnippet(Snippet snippet) {
        List<FileSnippets> fileSnippetList = snippetSets.get(snippet.key); 
        if (fileSnippetList != null) {
            for(FileSnippets fileSnippets : fileSnippetList) {
                for(Snippet snippetInFile : fileSnippets.snippets) {
                    if (snippetInFile == snippet) {
                        return fileSnippets.file;
                    } 
                }
            }
        }
        return null;     
    }
    
    public boolean isEmpty() {
        return snippetSets.isEmpty();
    }
    
    public int getSize() {
        return snippetSets.size();
    }
    
    public void clear() {
        snippetSets.clear();
        setCurrentSnippet(null);
        setCurrentSet(null);
    }
    
    public void setCurrentSet(String key) {
        String oldKey = currentKey;

        if (key == null) {
            // current snippet being cleared
            currentKey = null;
            currentSet = null;
            currentFileSnippets = null;
            currentFileSnippetsIndex = -1;
            currentSnippetIndex = -1;
        } else {   
            List<FileSnippets> fileSnippetList = snippetSets.get(key); 
            if (fileSnippetList == null) {
                throw new IllegalArgumentException("snippet key " + key + " does not exist.");
            }
            currentKey = key;
            currentSet = fileSnippetList;
            currentFileSnippetsIndex = 0;
            currentFileSnippets = currentSet.get(currentFileSnippetsIndex);
            currentSnippetIndex = 0;
            currentSnippet = currentFileSnippets.snippets.get(currentSnippetIndex);
        }
        pcs.firePropertyChange("currentSet", oldKey, currentKey);
        pcs.firePropertyChange("currentSnippet", null, currentSnippet);
        
    }
    
    public String getCurrentSet() {
        return currentKey;
    }
    
    public Snippet getCurrentSnippet() {
        return currentSnippet;
    }
    
    protected void setCurrentSnippet(Snippet snippet) {
        Snippet oldCurrentSnippet = currentSnippet;
        currentSnippet = snippet;
        pcs.firePropertyChange("currentSnippet", oldCurrentSnippet, snippet);
    }

    public Snippet firstSnippet() {
        if (currentKey != null) {
            currentFileSnippetsIndex = 0;
            currentFileSnippets = currentSet.get(currentFileSnippetsIndex);
            currentSnippetIndex = 0;
            Snippet firstSnippet = currentFileSnippets.snippets.get(currentSnippetIndex);
            setCurrentSnippet(firstSnippet);
            return getCurrentSnippet();
        }
        return null;
    }
    
    public boolean nextSnippetExists() {
        if (currentKey != null) {
            if (currentSnippetIndex+1 < currentFileSnippets.snippets.size()) {
                // There is a next snippet in the current file
                return true;
            }
            if (currentFileSnippetsIndex+1 < currentSet.size()) {
                // There is another file containing the next snippet
                return true;
            }
        }
        return false;
    }
    
    public Snippet nextSnippet() {
        if (currentKey != null) {
            if (currentSnippetIndex+1 < currentFileSnippets.snippets.size()) {
                // There is a next snippet in the current file
                setCurrentSnippet(currentFileSnippets.snippets.get(++currentSnippetIndex));
                return getCurrentSnippet();
            }
            if (currentFileSnippetsIndex+1 < currentSet.size()) {
                // The next snippet is contained in the next file
                currentFileSnippets = currentSet.get(++currentFileSnippetsIndex);
                currentSnippetIndex = 0;
                setCurrentSnippet(currentFileSnippets.snippets.get(currentSnippetIndex));
                return getCurrentSnippet();
            }
        }
        return null;
    }
    
    public boolean previousSnippetExists() {
        if (currentKey != null) {
            if (currentSnippetIndex-1 >= 0) {
                // There is a previous snippet in the current file
                return true;
            }
            if (currentFileSnippetsIndex-1 >= 0) {
                // There is a previous file containing the previous snippet
                return true;
            }
        }
        return false;        
    }
    
    public Snippet previousSnippet() {
        if (currentKey != null) {
            if (currentSnippetIndex-1 >= 0) {
                // There is a previous snippet in the current file
                setCurrentSnippet(currentFileSnippets.snippets.get(--currentSnippetIndex));
                return getCurrentSnippet();
            }
            if (currentFileSnippetsIndex-1 >= 0) {
                // The previous snippet is contained in the previous file
                currentFileSnippets = currentSet.get(--currentFileSnippetsIndex);
                currentSnippetIndex = currentFileSnippets.snippets.size() - 1;
                setCurrentSnippet(currentFileSnippets.snippets.get(currentSnippetIndex));
                return getCurrentSnippet();
            }
        }
        return null;      
    }
    
     public Snippet lastSnippet() {
        if (currentKey != null) {
            currentFileSnippetsIndex = currentSet.size() - 1;
            currentFileSnippets = currentSet.get(currentFileSnippetsIndex);
            currentSnippetIndex = currentFileSnippets.snippets.size() - 1;
            setCurrentSnippet(currentFileSnippets.snippets.get(currentSnippetIndex));
            return getCurrentSnippet();
        }
        return null;
    }
     
    private static FileSnippets findFileSnippetsForFile(List<FileSnippets> fileSnippetList, URL file) {
        for(FileSnippets fileSnippets : fileSnippetList) {
            if (fileSnippets.file == file) {
                return fileSnippets;
            }
        }
        return null;
    }
     
    // data structure for keeping track of a particular snippet set's snippets within one file'
    private static class FileSnippets {
        public final String key;
        public final URL file;
        public final ArrayList<Snippet> snippets;
        
        public FileSnippets(String key, URL file) {
            this.key = key;
            this.file = file;
            snippets = new ArrayList<Snippet>();
        }
    }      
}
