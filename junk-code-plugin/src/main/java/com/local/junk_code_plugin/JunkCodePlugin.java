package com.local.junk_code_plugin;

import com.android.build.gradle.AppExtension;
import com.local.junk_code_plugin.visitors.JunkCodeClassVisitor;
import com.ss.android.ugc.bytex.common.CommonPlugin;
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain;
import com.ss.android.ugc.bytex.pluginconfig.anno.PluginConfig;

import org.gradle.api.Project;
import org.objectweb.asm.ClassWriter;

import javax.annotation.Nonnull;

/**
 * Created on 2021/8/30 16:19
 */
@PluginConfig("bytex.junk-code")
public class JunkCodePlugin extends CommonPlugin<JunkCodeExtension, JunkCodeContext> {

    @Override
    protected JunkCodeContext getContext(Project project, AppExtension android, JunkCodeExtension extension) {
        return new JunkCodeContext(project, android, extension);
    }

    @Override
    public boolean transform(@Nonnull String relativePath, @Nonnull ClassVisitorChain chain) {
        if (context.needJunkClass(relativePath.substring(0, relativePath.lastIndexOf('.')))) {
            chain.connect(new JunkCodeClassVisitor(context));
        }
        return super.transform(relativePath, chain);
    }

//    @Override
//    public int flagForClassWriter() {
//        return ClassWriter.COMPUTE_FRAMES;
//    }
}
