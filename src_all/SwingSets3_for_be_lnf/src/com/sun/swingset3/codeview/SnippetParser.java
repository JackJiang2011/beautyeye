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

import java.text.CharacterIterator;
import java.util.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;

/**
 * Parses a source code document, looking for blocks of code 
 * bracketed by comments including start and ending &quot;snippet&quot; markers.  
 *
 * @author aim
 */
public class SnippetParser {
    private final static String START_MARKER = "<snip>";
    private final static String END_MARKER = "</snip>";
    
    protected SnippetParser() {
        // never instantiate
    }
    
    public static HashMap<String,List<Snippet>> parse(Document document) {
        
        return parse(document, START_MARKER, END_MARKER);
    }
    
    private static HashMap<String,List<Snippet>> parse(Document document,
            String startMarker, String endMarker) {
        HashMap<String,List<Snippet>> snippetMap = new HashMap<String,List<Snippet>>();
        Stack<Snippet> stack = new Stack<Snippet>(); // snippets may be nested
        char startMarkerChars[] = startMarker.toCharArray();
        char endMarkerChars[] = endMarker.toCharArray();
        int nleft = document.getLength();
        Segment segment = new Segment();
        int offset = 0;
        int lineStart = 0;
        int charCount = 0;
        int startMarkerIndex = 0;
        int endMarkerIndex = 0;
        StringBuffer keyBuf = new StringBuffer();
        
        segment.setPartialReturn(true);
        try {
            while (nleft > 0) {
                
                document.getText(offset, nleft, segment);
                
                for(char c = segment.first(); c != CharacterIterator.DONE; 
                    c = segment.next()) {
                    
                    if (!stack.isEmpty()) {
                        // already found a begin marker, so looking for end marker
                        if (c == endMarkerChars[endMarkerIndex]) {
                            endMarkerIndex++;
                            if (endMarkerIndex == endMarkerChars.length) {
                                // found end marker, so register snippet
                                Snippet snippet = stack.pop();
                                if (snippet.key.equals("tempkey")) {
                                    // haven't stored key yet
                                    snippet.key = keyBuf.toString().trim();
                                }
                                snippet.endLine = charCount + 1;
                                List<Snippet> snippetList = snippetMap.get(snippet.key);
                                
                                if (snippetList == null) {
                                    snippetList = new ArrayList<Snippet>();
                                    snippetMap.put(snippet.key, snippetList);
                                }
                                snippetList.add(snippet);                                
                                endMarkerIndex = 0;
                            }
                        } else {
                            endMarkerIndex = 0;
                            
                            if (stack.peek().startLine == lineStart){
                                // build snippet key
                                keyBuf.append(c);
                            }
                        }                        
                    }
                    if (c == startMarkerChars[startMarkerIndex]) {
                        startMarkerIndex++;
                        if (startMarkerIndex == startMarkerChars.length) {
                            // found new begin marker
                            if (!stack.isEmpty()) {
                                // nested snippet, save previous key before pushing new one
                                Snippet snippet = stack.peek();
                                snippet.key = keyBuf.toString().trim();
                            }
                            stack.push(new Snippet(document, "tempkey", lineStart));
                            keyBuf.setLength(0);
                            startMarkerIndex = 0;
                        }
                        
                    } else {
                        startMarkerIndex = 0;
                    }
                    charCount++;
                    if (c == '\n') {
                        lineStart = charCount;                        
                    }
                }                
                nleft -= segment.count;
                offset += segment.count;
            }
        } catch (BadLocationException e) {
            System.err.println(e);
        }
        return snippetMap;        
    }    
}
