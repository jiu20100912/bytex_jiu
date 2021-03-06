package com.ss.android.ugc.bytex.common.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class ClassVisitorChain {
    private BaseClassVisitor head;
    private BaseClassVisitor tail;
    private ClassWriter classWriter;

    public ClassVisitorChain() {
    }

    public ClassVisitorChain(ClassWriter cw) {
        this.classWriter = cw;
    }

    public ClassVisitorChain connect(BaseClassVisitor cv) {
        if (cv == null) {
            return this;
        }
        if (this.tail != null) {
            this.tail.setNext(cv);
            this.tail = cv;
        } else {
            this.head = this.tail = cv;
            this.tail.setNext(classWriter);
        }
        return this;
    }

    public ClassVisitorChain append(ClassNode cv) {
        if (this.tail != null) {
            this.tail.setNext(cv);
        } else {
            this.head = this.tail = new BaseClassVisitor(cv);
        }
        return this;
    }

    public ClassVisitorChain classWriter(ClassWriter classWriter) {
        this.classWriter = classWriter;
        if (this.tail != null)
            this.tail.setNext(classWriter);
        return this;
    }

    public byte[] accept(ClassReader classReader) {
        return accept(classReader, 0);
    }

    public byte[] accept(ClassReader classReader, int flag) {
        if (head != null) {
            classReader.accept(head, flag);
        }
        if (classWriter != null) {
            return classWriter.toByteArray();
        } else return null;
    }


    public byte[] accept(ClassNode classNode) {
        if (head != null) {
            classNode.accept(head);
        }
        if (classWriter != null) {
            return classWriter.toByteArray();
        } else return null;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
