package com.google.code.microlog4android.repository;

import com.google.code.microlog4android.Level;
import com.google.code.microlog4android.Logger;
import java.util.Hashtable;

public class MicrologRepositoryNode extends AbstractRepositoryNode {
    private Hashtable<String, MicrologRepositoryNode> children = new Hashtable<>(17);
    private Logger logger;
    private MicrologRepositoryNode parent = null;

    public MicrologRepositoryNode(String str, Logger logger2) {
        this.name = str;
        this.logger = logger2;
    }

    public MicrologRepositoryNode(String str, MicrologRepositoryNode micrologRepositoryNode) {
        this.name = str;
        this.parent = micrologRepositoryNode;
        this.logger = new Logger(str);
        this.logger.setCommonRepository(DefaultLoggerRepository.INSTANCE);
    }

    public MicrologRepositoryNode(String str, Logger logger2, MicrologRepositoryNode micrologRepositoryNode) {
        this.name = str;
        this.logger = logger2;
        this.parent = micrologRepositoryNode;
    }

    public void addChild(MicrologRepositoryNode micrologRepositoryNode) {
        this.children.put(micrologRepositoryNode.getName(), micrologRepositoryNode);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public MicrologRepositoryNode getChildNode(String str) {
        return this.children.get(str);
    }

    public void resetLogger() {
        this.children.clear();
        this.logger.resetLogger();
        this.logger.setLevel(Level.DEBUG);
    }

    public MicrologRepositoryNode getParent() {
        return this.parent;
    }

    public void setParent(MicrologRepositoryNode micrologRepositoryNode) {
        this.parent = micrologRepositoryNode;
    }
}
