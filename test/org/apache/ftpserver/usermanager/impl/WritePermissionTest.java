package org.apache.ftpserver.usermanager.impl;

import junit.framework.TestCase;

/**
*
* @author <a href="http://mina.apache.org">Apache MINA Project</a>*
*/
public class WritePermissionTest extends TestCase {

    public void testRootDir() throws Exception {
        WritePermission permission = new WritePermission("/");
        assertNotNull(permission.authorize(new WriteRequest("/")));
    }

    public void testDirs() throws Exception {
        WritePermission permission = new WritePermission("/bar");
        assertNull(permission.authorize(new WriteRequest("/foo")));
        assertNull(permission.authorize(new WriteRequest("/foo/bar")));
        assertNotNull(permission.authorize(new WriteRequest("/bar")));
        assertNotNull(permission.authorize(new WriteRequest("/bar/foo")));
    }
}
