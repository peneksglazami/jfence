/*
 * Copyright 2016 Andrey Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.peneksglazami.jfence;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;

/**
 * Class file transformer that makes all class field volatile.
 *
 * @author Andrey Grigorov
 */
public class JFenceClassTransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));
            CtClass cc = pool.get(className);
            for (CtField field : cc.getDeclaredFields()) {
                field.setModifiers(Modifier.VOLATILE);
                System.out.println(className + "-" + field.getName() + " set volatile.");
            }
            return cc.toBytecode();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return classfileBuffer;
    }
}
