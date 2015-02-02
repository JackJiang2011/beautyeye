/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The BeautyEye Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/beautyeye
 * Version 3.6
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * CodeViewer.java at 2015-2-1 20:25:39, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */

/*
 * @(#)CodeViewer.java	1.6 02/06/13
 */


import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * A class that syntax highlights Java code by turning it into html.
 *
 * <p> A <code>CodeViewer</code> object is created and then keeps state as
 * lines are passed in. Each line passed in as java text, is returned as syntax
 * highlighted html text.
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
 * @version 1.6 06/13/02
 * @author Bill Lynch, Matt Tucker, CoolServlets.com
 */
public class CodeViewer {

    /** The reserved words. */
    private static HashMap reservedWords = new HashMap(); // >= Java2 only (also, not thread-safe)
    //private static Hashtable reservedWords = new Hashtable(); // < Java2 (thread-safe)
    /** The in multi line comment. */
    private boolean inMultiLineComment = false;
    
    /** The background color. */
    private String backgroundColor = "#ffffff";
    
    /** The comment start. */
    private String commentStart = "</font><font size=2 color=\"#0000aa\"><i>";
    
    /** The comment end. */
    private String commentEnd = "</font></i><font size=2 color=black>";    
    
    /** The string start. */
    private String stringStart = "</font><font size=2 color=\"#00bb00\">";
    
    /** The string end. */
    private String stringEnd = "</font><font size=2 color=black>";
    
    /** The reserved word start. */
    private String reservedWordStart = "<b>";
    
    /** The reserved word end. */
    private String reservedWordEnd = "</b>";

    static {
        loadHash();
    }

    /**
     * Instantiates a new code viewer.
     */
    public CodeViewer() {
    }

    /**
     * Sets the comment start.
     *
     * @param commentStart the new comment start
     */
    public void setCommentStart(String commentStart) {
        this.commentStart = commentStart;
    }
    
    /**
     * Sets the comment end.
     *
     * @param commentEnd the new comment end
     */
    public void setCommentEnd(String commentEnd) {
        this.commentEnd = commentEnd;
    }
    
    /**
     * Sets the string start.
     *
     * @param stringStart the new string start
     */
    public void setStringStart(String stringStart) {
        this.stringStart = stringStart;
    }
    
    /**
     * Sets the string end.
     *
     * @param stringEnd the new string end
     */
    public void setStringEnd(String stringEnd) {
        this.stringEnd = stringEnd;
    }
    
    /**
     * Sets the reserved word start.
     *
     * @param reservedWordStart the new reserved word start
     */
    public void setReservedWordStart(String reservedWordStart) {
        this.reservedWordStart = reservedWordStart;
    }
    
    /**
     * Sets the reserved word end.
     *
     * @param reservedWordEnd the new reserved word end
     */
    public void setReservedWordEnd(String reservedWordEnd) {
        this.reservedWordEnd = reservedWordEnd;
    }

    /**
     * Gets the comment start.
     *
     * @return the comment start
     */
    public String getCommentStart() {
        return commentStart;
    }
    
    /**
     * Gets the comment end.
     *
     * @return the comment end
     */
    public String getCommentEnd() {
        return commentEnd;
    }
    
    /**
     * Gets the string start.
     *
     * @return the string start
     */
    public String getStringStart() {
        return stringStart;
    }
    
    /**
     * Gets the string end.
     *
     * @return the string end
     */
    public String getStringEnd() {
        return stringEnd;
    }
    
    /**
     * Gets the reserved word start.
     *
     * @return the reserved word start
     */
    public String getReservedWordStart() {
        return reservedWordStart;
    }
    
    /**
     * Gets the reserved word end.
     *
     * @return the reserved word end
     */
    public String getReservedWordEnd() {
        return reservedWordEnd;
    }

    /**
     * Passes off each line to the first filter.
     *
     * @param line the line
     * @return the string
     */
    public String syntaxHighlight( String line ) {
       return htmlFilter(line);
    }

    /*
     * Filter html tags into more benign text.
     */ 
    /**
     * Html filter.
     *
     * @param line the line
     * @return the string
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
    /**
     * Multi line comment filter.
     *
     * @param line the line
     * @return the string
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
    /**
     * Inline comment filter.
     *
     * @param line the line
     * @return the string
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
    /**
     * String filter.
     *
     * @param line the line
     * @return the string
     */
    private String stringFilter(String line) {
        if (line == null || line.equals("")) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        if (line.indexOf("\"") <= -1) {
            return keywordFilter(line);
        }
        int start = 0;
        int startStringIndex = -1;
        int endStringIndex = -1;
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
    /**
     * Keyword filter.
     *
     * @param line the line
     * @return the string
     */
    private String keywordFilter( String line ) {
        if( line == null || line.equals("") ) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        HashMap usedReservedWords = new HashMap(); // >= Java2 only (not thread-safe)
        //Hashtable usedReservedWords = new Hashtable(); // < Java2 (thread-safe)
        int i=0, startAt=0;
        char ch;
        StringBuffer temp = new StringBuffer();
        while( i < line.length() ) {
            temp.setLength(0);
            ch = line.charAt(i);
            startAt = i;
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
            if( reservedWords.containsKey(tempString) && !usedReservedWords.containsKey(tempString)) {
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
    /**
     * Replace.
     *
     * @param line the line
     * @param oldString the old string
     * @param newString the new string
     * @return the string
     */
    private String replace( String line, String oldString, String newString ) {
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
    /**
     * Checks if is inside string.
     *
     * @param line the line
     * @param position the position
     * @return true, if is inside string
     */
    private boolean isInsideString(String line, int position) {
        if (line.indexOf("\"") < 0) {
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
        if (rightCount % 2 != 0 && leftCount % 2 != 0) {
            return true;
        }
        else {
            return false;
        }        
    }

    /*
     * Load Hashtable (or HashMap) with Java reserved words.
     */
    /**
     * Load hash.
     */
    private static void loadHash() {
        reservedWords.put( "abstract", "abstract" );
        reservedWords.put( "do", "do" );
        reservedWords.put( "inner", "inner" );
        reservedWords.put( "public", "public" );
        reservedWords.put( "var", "var" );
        reservedWords.put( "boolean", "boolean" );
        reservedWords.put( "continue", "continue" );
        reservedWords.put( "int", "int" );
        reservedWords.put( "return", "return" );
        reservedWords.put( "void", "void" );
        reservedWords.put( "break", "break" );
        reservedWords.put( "else", "else" );
        reservedWords.put( "interface", "interface" );
        reservedWords.put( "short", "short" );
        reservedWords.put( "volatile", "volatile" );
        reservedWords.put( "byvalue", "byvalue" );
        reservedWords.put( "extends", "extends" );
        reservedWords.put( "long", "long" );
        reservedWords.put( "static", "static" );
        reservedWords.put( "while", "while" );
        reservedWords.put( "case", "case" );
        reservedWords.put( "final", "final" );
        reservedWords.put( "naive", "naive" );
        reservedWords.put( "super", "super" );
        reservedWords.put( "transient", "transient" );
        reservedWords.put( "cast", "cast" );
        reservedWords.put( "float", "float" );
        reservedWords.put( "new", "new" );
        reservedWords.put( "rest", "rest" );
        reservedWords.put( "catch", "catch" );
        reservedWords.put( "for", "for" );
        reservedWords.put( "null", "null" );
        reservedWords.put( "synchronized", "synchronized" );
        reservedWords.put( "char", "char" );
        reservedWords.put( "finally", "finally" );
        reservedWords.put( "operator", "operator" );
        reservedWords.put( "this", "this" );
        reservedWords.put( "class", "class" );
        reservedWords.put( "generic", "generic" );
        reservedWords.put( "outer", "outer" );
        reservedWords.put( "switch", "switch" );
        reservedWords.put( "const", "const" );
        reservedWords.put( "goto", "goto" );
        reservedWords.put( "package", "package" );
        reservedWords.put( "throw", "throw" );
        reservedWords.put( "double", "double" );
        reservedWords.put( "if", "if" );
        reservedWords.put( "private", "private" );
        reservedWords.put( "true", "true" );
        reservedWords.put( "default", "default" );
        reservedWords.put( "import", "import" );
        reservedWords.put( "protected", "protected" );
        reservedWords.put( "try", "try" );
    }
}
