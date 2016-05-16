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

import javassist.ClassPool;
import javassist.CtClass;

/**
 * Class loader, that use JFenceClassTransformer to instrument loading class.
 *
 * @author Andrey Grigorov
 */
public class TransformClassLoader extends ClassLoader {

    private String instrumentedClassName;

    /**
     * Class loader constructor.
     *
     * @param instrumentedClassName Name of class, which must be instrumented
     *                              by JFenceClassTransformer.
     */
    public TransformClassLoader(String instrumentedClassName) {
        super();
        this.instrumentedClassName = instrumentedClassName;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith(instrumentedClassName)) {
            try {
                byte[] byteBuffer = instrumentByteCode(name);
                return defineClass(name, byteBuffer, 0, byteBuffer.length);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        return super.loadClass(name);
    }

    private byte[] instrumentByteCode(String className) throws Exception {
        ClassPool pool = new ClassPool(true);
        CtClass clazz = pool.get(className);
        byte[] byteCode = clazz.toBytecode();
        JFenceClassTransformer transformer = new JFenceClassTransformer();
        return transformer.transform(this, className, null, null, byteCode);
    }
}