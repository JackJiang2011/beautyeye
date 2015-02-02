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
package com.sun.swingset3.demos;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author Pavel Porvatov
 */
public class ResourceManager {
    private static final Logger logger = Logger.getLogger(ResourceManager.class.getName());

    private final Class demoClass;

    // Resource bundle for internationalized and accessible text
    private ResourceBundle bundle = null;

    public ResourceManager(Class demoClass) {
        this.demoClass = demoClass;

        String bundleName = demoClass.getPackage().getName() + ".resources." + demoClass.getSimpleName();

        try {
            bundle = ResourceBundle.getBundle(bundleName);
        } catch (MissingResourceException e) {
            logger.log(Level.SEVERE, "Couldn't load bundle: " + bundleName);
        }
    }

    public String getString(String key) {
        return bundle != null ? bundle.getString(key) : key;
    }

    public char getMnemonic(String key) {
        return (getString(key)).charAt(0);
    }

    public ImageIcon createImageIcon(String filename, String description) {
        String path = "resources/images/" + filename;

        URL imageURL = demoClass.getResource(path);

        if (imageURL == null) {
            logger.log(Level.SEVERE, "unable to access image file: " + path);

            return null;
        } else {
            return new ImageIcon(imageURL, description);
        }
    }
}
