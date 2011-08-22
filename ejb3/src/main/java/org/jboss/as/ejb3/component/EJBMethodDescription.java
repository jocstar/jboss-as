/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.ejb3.component;

import org.jboss.jandex.MethodInfo;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Jaikiran Pai
 */
public class EJBMethodDescription {

    private String methodName;

    private String[] methodParamTypes;

    private MethodIntf viewType;

    private int hashCode;

    public EJBMethodDescription(MethodInfo methodInfo) {
        if (methodInfo == null) {
            throw new IllegalArgumentException("MethodInfo cannot be null");
        }
        this.methodName = methodInfo.name();
        this.viewType = MethodIntf.BEAN;
        String[] params = this.toString(methodInfo.args());
        this.methodParamTypes = params == null ? new String[0] : params;
    }

    public EJBMethodDescription(Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method cannot be null");
        }
        this.methodName = method.getName();
        this.viewType = MethodIntf.BEAN;
        String[] params = this.toString(method.getParameterTypes());
        this.methodParamTypes = params == null ? new String[0] : params;
    }

    public EJBMethodDescription(String methodName, String... methodParamTypes) {
        this(MethodIntf.BEAN, methodName, methodParamTypes);
    }

    public EJBMethodDescription(MethodIntf view, String methodName, String... paramTypes) {
        if (methodName == null) {
            throw new IllegalArgumentException("Method name cannot be null");
        }
        this.methodName = methodName;
        this.methodParamTypes = paramTypes == null ? new String[0] : paramTypes;
        this.viewType = view == null ? MethodIntf.BEAN : view;

        this.hashCode = this.generateHashCode();
    }


    public String getMethodName() {
        return this.methodName;
    }

    public String[] getMethodParams() {
        return this.methodParamTypes;
    }

    public MethodIntf getViewType() {
        return this.viewType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EJBMethodDescription that = (EJBMethodDescription) o;

        if (!methodName.equals(that.methodName)) {
            return false;
        }
        if (!Arrays.equals(methodParamTypes, that.methodParamTypes)) {
            return false;
        }
        if (viewType != that.viewType) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    private int generateHashCode() {
        int result = methodName.hashCode();
        result = 31 * result + Arrays.hashCode(methodParamTypes);
        result = 31 * result + viewType.hashCode();
        return result;
    }

    private String[] toString(Object[] types) {
        if (types == null) {
            return null;
        }
        String[] result = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            result[i] = types[i].toString();
        }
        return result;
    }
}