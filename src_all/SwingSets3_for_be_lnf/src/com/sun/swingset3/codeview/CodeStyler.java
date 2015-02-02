/**
 * CodeStyler.java
 * 
 * Bill Lynch & Matt Tucker
 * CoolServlets.com, October 1999
 * 
 * Please visit CoolServlets.com for high quality, open source Java servlets. 
 *
 * Copyright (C) 1999  CoolServlets.com
 * 
 * Any errors or suggested improvements to this class can be reported
 * as instructed on Coolservlets.com. We hope you enjoy
 * this program... your comments will encourage further development!
 * 
 * This software is distributed under the terms of The BSD License.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * Neither name of CoolServlets.com nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY COOLSERVLETS.COM AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COOLSERVLETS.COM OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * @(#)CodeStyler.java	1.6 02/06/13
 */
package com.sun.swingset3.codeview;

import java.util.*;

/**
 * A class that syntax highlights Java code by turning it into html.
 * 
 * <p> A <code>CodeStyler</code> object is created and then keeps state as
 * lines are passed in. Each line passed in as java text, is returned as syntax
 * highlighted html text. (note: this class was originally named CodeViewer)
 * 
 * <p> Users of the class can set how the java code will be highlighted with
 * setter methods.
 * 
 * <p> Only valid java lines should be passed in since the object maintains
 * state and may not handle illegal code gracefully.
 * 
 * <p> The actual system is implemented as a series of filters that deal with
 * specific portions of the java code. The filters are as follows:
 * 
 * <pre>
 *  htmlFilter
 *     |__
 *        multiLineCommentFilter
 *           |___
 *                inlineCommentFilter
 *                   |___
 *                        stringFilter
 *                           |__
 *                               keywordFilter
 * </pre>
 * 
 * @author Bill Lynch, Matt Tucker, CoolServlets.com
 * @version 1.6 06/13/02
 */
public class CodeStyler {

    private final static Map<String, String> RESERVED_WORDS = new HashMap<String, String>(); // >= Java2 only (also, not thread-safe)
    private boolean inMultiLineComment = false;
    private String commentStart = "</font><font size=4 color=\"#0000aa\"><i>";
    private String commentEnd = "</font></i><font size=4 color=black>";    
    private String stringStart = "</font><font size=4 color=\"#00bb00\">";
    private String stringEnd = "</font><font size=4 color=black>";
    private String reservedWordStart = "<b>";
    private String reservedWordEnd = "</b>";

    static {
        loadHash();
    }

    public CodeStyler() {
    }

    public void setCommentStart(String commentStart) {
        this.commentStart = commentStart;
    }
    public void setCommentEnd(String commentEnd) {
        this.commentEnd = commentEnd;
    }
    public void setStringStart(String stringStart) {
        this.stringStart = stringStart;
    }
    public void setStringEnd(String stringEnd) {
        this.stringEnd = stringEnd;
    }
    public void setReservedWordStart(String reservedWordStart) {
        this.reservedWordStart = reservedWordStart;
    }
    public void setReservedWordEnd(String reservedWordEnd) {
        this.reservedWordEnd = reservedWordEnd;
    }

    public String getCommentStart() {
        return commentStart;
    }
    public String getCommentEnd() {
        return commentEnd;
    }
    public String getStringStart() {
        return stringStart;
    }
    public String getStringEnd() {
        return stringEnd;
    }
    public String getReservedWordStart() {
        return reservedWordStart;
    }
    public String getReservedWordEnd() {
        return reservedWordEnd;
    }

    /**
     * Passes off each line to the first filter.
     * @param   line    The line of Java code to be highlighted.
     */
    public String syntaxHighlight( String line ) {
       return htmlFilter(line);
    }

    /*
     * Filter html tags into more benign text.
     */ 
    private String htmlFilter( String line ) {
        if( line == null || line.equals("") ) {
            return "";
        }

        // replace ampersands with HTML escape sequence for ampersand;
        line = replace(line, "&", "&#38;");

        // replace the \\ with HTML escape sequences. fixes a problem when
        // backslashes preceed quotes.
        line = replace(line, "\\\\", "&#92;&#92;" );

        // replace \" sequences with HTML escape sequences;
        line = replace(line, "" + (char)92 + (char)34, "&#92;&#34");

        // replace less-than signs which might be confused
        // by HTML as tag angle-brackets;
        line = replace(line, "<", "&#60;");
        // replace greater-than signs which might be confused
        // by HTML as tag angle-brackets;
        line = replace(line, ">", "&#62;");
        
        return multiLineCommentFilter(line);
    }

    /*
     * Filter out multiLine comments. State is kept with a private boolean
     * variable.
     */     
    private String multiLineCommentFilter(String line) {
        if (line == null || line.equals("")) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        int index;
        //First, check for the end of a multi-line comment.
        if (inMultiLineComment && (index = line.indexOf("*/")) > -1 && !isInsideString(line,index)) {
            inMultiLineComment = false;               
            buf.append(line.substring(0,index));
            buf.append("*/").append(commentEnd);
            if (line.length() > index+2) {
                buf.append(inlineCommentFilter(line.substring(index+2)));
            }
            return buf.toString();
        }
        //If there was no end detected and we're currently in a multi-line
        //comment, we don't want to do anymore work, so return line.
        else if (inMultiLineComment) {
            return line;
        }
        //We're not currently in a comment, so check to see if the start
        //of a multi-line comment is in this line.
        else if ((index = line.indexOf("/*")) > -1 && !isInsideString(line,index)) {
            inMultiLineComment = true;
            //Return result of other filters + everything after the start
            //of the multiline comment. We need to pass the through the
            //to the multiLineComment filter again in case the comment ends
            //on the same line.
            buf.append(inlineCommentFilter(line.substring(0,index)));
            buf.append(commentStart).append("/*");
            buf.append(multiLineCommentFilter(line.substring(index+2)));
            return buf.toString();
        }
        //Otherwise, no useful multi-line comment information was found so
        //pass the line down to the next filter for processesing.
        else {
            return inlineCommentFilter(line);
        }
    }      

    /*
     * Filter inline comments from a line and formats them properly.
     */
    private String inlineCommentFilter(String line) {
        if (line == null || line.equals("")) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        int index;
        if ((index = line.indexOf("//")) > -1 && !isInsideString(line,index)) {
            buf.append(stringFilter(line.substring(0,index)));
            buf.append(commentStart);
            buf.append(line.substring(index));
            buf.append(commentEnd);
        }
        else {
            buf.append(stringFilter(line));
        }
        return buf.toString();
    } 

    /*
     * Filters strings from a line of text and formats them properly.
     */
    private String stringFilter(String line) {
        if (line == null || line.equals("")) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        if (!line.contains("\"")) {
            return keywordFilter(line);
        }
        int start = 0;
        int startStringIndex = -1;
        int endStringIndex;
        int tempIndex;
        //Keep moving through String characters until we want to stop...
        while ((tempIndex = line.indexOf("\"")) > -1) {
            //We found the beginning of a string
            if (startStringIndex == -1) {
                startStringIndex = 0;
                buf.append( stringFilter(line.substring(start,tempIndex)) );
                buf.append(stringStart).append("\"");
                line = line.substring(tempIndex+1);
            }
            //Must be at the end
            else {
                startStringIndex = -1;
                endStringIndex = tempIndex;
                buf.append(line.substring(0,endStringIndex+1));
                buf.append(stringEnd);
                line = line.substring(endStringIndex+1);
            }
        }

        buf.append( keywordFilter(line) );

        return buf.toString();
    }

    /*
     * Filters keywords from a line of text and formats them properly.
     */
    private String keywordFilter( String line ) {
        if( line == null || line.equals("") ) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        Map<String, String> usedReservedWords = new HashMap<String, String>(); // >= Java2 only (not thread-safe)
        //Hashtable usedReservedWords = new Hashtable(); // < Java2 (thread-safe)
        int i=0;
        char ch;
        StringBuffer temp = new StringBuffer();
        while( i < line.length() ) {
            temp.setLength(0);
            ch = line.charAt(i);
            // 65-90, uppercase letters
            // 97-122, lowercase letters
            while( i<line.length() && ( ( ch >= 65 && ch <= 90 )
                    || ( ch >= 97 && ch <= 122 ) ) ) {
                temp.append(ch);
                i++;
                if( i < line.length() ) {
                    ch = line.charAt(i);
                }
            }
            String tempString = temp.toString();
            if( RESERVED_WORDS.containsKey(tempString) && !usedReservedWords.containsKey(tempString)) {
                usedReservedWords.put(tempString,tempString);
                line = replace( line, tempString, (reservedWordStart+tempString+reservedWordEnd) );
                i += (reservedWordStart.length() + reservedWordEnd.length());
            }
            else {
                i++;
            }            
        }
        buf.append(line);
        return buf.toString();
    }

    /*
     * All important replace method. Replaces all occurences of oldString in
     * line with newString.
     */
    private static String replace( String line, String oldString, String newString ) {
        int i=0;
        while( ( i=line.indexOf( oldString, i ) ) >= 0 ) {
            line = (new StringBuffer().append(line.substring(0,i)).append(newString).append(line.substring(i+oldString.length()))).toString();
            i += newString.length();
        }
        return line;
    }

    /*
     * Checks to see if some position in a line is between String start and
     * ending characters. Not yet used in code or fully working :)
     */
    private static boolean isInsideString(String line, int position) {
        if (!line.contains("\"")) {
            return false;
        }
        int index;
        String left = line.substring(0,position);
        String right = line.substring(position);
        int leftCount = 0;
        int rightCount = 0;
        while ((index = left.indexOf("\"")) > -1) {
            leftCount ++;
            left = left.substring(index+1); 
        }
        while ((index = right.indexOf("\"")) > -1) {
            rightCount ++;
            right = right.substring(index+1); 
        }
        return rightCount % 2 != 0 && leftCount % 2 != 0;
    }

    /*
     * Load Hashtable (or HashMap) with Java reserved words.
     */
    private static void loadHash() {
        RESERVED_WORDS.put("abstract", "abstract");
        RESERVED_WORDS.put("do", "do");
        RESERVED_WORDS.put("inner", "inner");
        RESERVED_WORDS.put("public", "public");
        RESERVED_WORDS.put("var", "var");
        RESERVED_WORDS.put("boolean", "boolean");
        RESERVED_WORDS.put("continue", "continue");
        RESERVED_WORDS.put("int", "int");
        RESERVED_WORDS.put("return", "return");
        RESERVED_WORDS.put("void", "void");
        RESERVED_WORDS.put("break", "break");
        RESERVED_WORDS.put("else", "else");
        RESERVED_WORDS.put("interface", "interface");
        RESERVED_WORDS.put("short", "short");
        RESERVED_WORDS.put("volatile", "volatile");
        RESERVED_WORDS.put("byvalue", "byvalue");
        RESERVED_WORDS.put("extends", "extends");
        RESERVED_WORDS.put("long", "long");
        RESERVED_WORDS.put("static", "static");
        RESERVED_WORDS.put("while", "while");
        RESERVED_WORDS.put("case", "case");
        RESERVED_WORDS.put("final", "final");
        RESERVED_WORDS.put("naive", "naive");
        RESERVED_WORDS.put("super", "super");
        RESERVED_WORDS.put("transient", "transient");
        RESERVED_WORDS.put("cast", "cast");
        RESERVED_WORDS.put("float", "float");
        RESERVED_WORDS.put("new", "new");
        RESERVED_WORDS.put("rest", "rest");
        RESERVED_WORDS.put("catch", "catch");
        RESERVED_WORDS.put("for", "for");
        RESERVED_WORDS.put("null", "null");
        RESERVED_WORDS.put("synchronized", "synchronized");
        RESERVED_WORDS.put("char", "char");
        RESERVED_WORDS.put("finally", "finally");
        RESERVED_WORDS.put("operator", "operator");
        RESERVED_WORDS.put("this", "this");
        RESERVED_WORDS.put("class", "class");
        RESERVED_WORDS.put("generic", "generic");
        RESERVED_WORDS.put("outer", "outer");
        RESERVED_WORDS.put("switch", "switch");
        RESERVED_WORDS.put("const", "const");
        RESERVED_WORDS.put("goto", "goto");
        RESERVED_WORDS.put("package", "package");
        RESERVED_WORDS.put("throw", "throw");
        RESERVED_WORDS.put("double", "double");
        RESERVED_WORDS.put("if", "if");
        RESERVED_WORDS.put("private", "private");
        RESERVED_WORDS.put("true", "true");
        RESERVED_WORDS.put("default", "default");
        RESERVED_WORDS.put("import", "import");
        RESERVED_WORDS.put("protected", "protected");
        RESERVED_WORDS.put("try", "try");
    }
}
