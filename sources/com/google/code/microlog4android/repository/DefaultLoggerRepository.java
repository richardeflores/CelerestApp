package com.google.code.microlog4android.repository;

import android.util.Log;
import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public enum DefaultLoggerRepository implements LoggerRepository, CommonLoggerRepository {
    INSTANCE;
    
    private static final String TAG = "Microlog.DefaultLoggerRepository";
    private Hashtable<String, MicrologRepositoryNode> leafNodeHashtable;
    private MicrologRepositoryNode rootNode;

    public Logger getRootLogger() {
        return this.rootNode.getLogger();
    }

    public synchronized Logger getLogger(String str) {
        Logger logger;
        MicrologRepositoryNode micrologRepositoryNode = this.leafNodeHashtable.get(str);
        if (micrologRepositoryNode == null) {
            logger = new Logger(str, this);
            addLogger(logger);
        } else {
            logger = micrologRepositoryNode.getLogger();
        }
        return logger;
    }

    /* access modifiers changed from: package-private */
    public void addLogger(Logger logger) {
        String name = logger.getName();
        MicrologRepositoryNode micrologRepositoryNode = this.rootNode;
        String[] loggerNameComponents = LoggerNamesUtil.getLoggerNameComponents(name);
        for (String str : loggerNameComponents) {
            if (micrologRepositoryNode.getChildNode(str) == null) {
                micrologRepositoryNode = createNewChildNode(str, micrologRepositoryNode);
            }
        }
        if (loggerNameComponents.length > 0) {
            MicrologRepositoryNode micrologRepositoryNode2 = new MicrologRepositoryNode(LoggerNamesUtil.getClassName(loggerNameComponents), logger, micrologRepositoryNode);
            micrologRepositoryNode.addChild(micrologRepositoryNode2);
            this.leafNodeHashtable.put(name, micrologRepositoryNode2);
        }
    }

    public void setLevel(String str, Level level) {
        MicrologRepositoryNode micrologRepositoryNode = this.leafNodeHashtable.get(str);
        if (micrologRepositoryNode != null) {
            micrologRepositoryNode.getLogger().setLevel(level);
            return;
        }
        MicrologRepositoryNode micrologRepositoryNode2 = this.rootNode;
        for (String str2 : LoggerNamesUtil.getLoggerNameComponents(str)) {
            if (micrologRepositoryNode2.getChildNode(str2) == null) {
                micrologRepositoryNode2 = createNewChildNode(str2, micrologRepositoryNode2);
            }
        }
        if (micrologRepositoryNode2 != null) {
            micrologRepositoryNode2.getLogger().setLevel(level);
        }
    }

    private MicrologRepositoryNode createNewChildNode(String str, MicrologRepositoryNode micrologRepositoryNode) {
        MicrologRepositoryNode micrologRepositoryNode2 = new MicrologRepositoryNode(str, micrologRepositoryNode);
        micrologRepositoryNode.addChild(micrologRepositoryNode2);
        return micrologRepositoryNode2;
    }

    public Level getEffectiveLevel(String str) {
        MicrologRepositoryNode micrologRepositoryNode = this.leafNodeHashtable.get(str);
        Level level = null;
        while (level == null && micrologRepositoryNode != null) {
            level = micrologRepositoryNode.getLogger().getLevel();
            micrologRepositoryNode = micrologRepositoryNode.getParent();
        }
        return level;
    }

    public boolean contains(String str) {
        return this.leafNodeHashtable.containsKey(str);
    }

    public int numberOfLeafNodes() {
        return this.leafNodeHashtable.size();
    }

    public void reset() {
        this.rootNode.resetLogger();
        this.leafNodeHashtable.clear();
    }

    public void shutdown() {
        Enumeration<MicrologRepositoryNode> elements = this.leafNodeHashtable.elements();
        while (elements.hasMoreElements()) {
            Logger logger = elements.nextElement().getLogger();
            if (logger != null) {
                try {
                    logger.close();
                } catch (IOException unused) {
                    Log.e(TAG, "Failed to close logger " + logger.getName());
                }
            }
        }
    }
}
