package org.apache.ftpserver.util;

/**
 * <strong>Internal class, do not use directly.</strong>
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a> *
 */
public class ClassUtils {

    /**
     * Checks if a class is a subclass of a class with the specified name. Used
     * as an instanceOf without having to load the class, useful when trying to
     * check for classes that might not be available in the runtime JRE.
     * 
     * @param clazz
     *            The class to check
     * @param className
     *            The class name to look for in the super classes
     * @return true if the class extends a class by the specified name.
     */
    public static boolean extendsClass(final Class<?> clazz, String className) {
        Class<?> superClass = clazz.getSuperclass();
        edu.hkust.clap.monitor.Monitor.loopBegin(49);
while (superClass != null) { 
edu.hkust.clap.monitor.Monitor.loopInc(49);
{
            if (superClass.getName().equals(className)) {
                return true;
            }
            superClass = superClass.getSuperclass();
        }} 
edu.hkust.clap.monitor.Monitor.loopEnd(49);

        return false;
    }
}
