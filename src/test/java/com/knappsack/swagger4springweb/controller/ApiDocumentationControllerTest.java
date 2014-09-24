package com.knappsack.swagger4springweb.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import com.knappsack.swagger4springweb.AbstractTest;
import com.knappsack.swagger4springweb.util.ScalaToJavaUtil;
import com.wordnik.swagger.model.ApiListing;
import com.wordnik.swagger.model.ApiListingReference;
import com.wordnik.swagger.model.ResourceListing;

public class ApiDocumentationControllerTest extends AbstractTest {

    @Test
    public void getDocumentation() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/api/doc/api/v1/test");
        ApiDocumentationController apiDocumentationController = new ApiDocumentationController();
        apiDocumentationController.setApiVersion("v1");
        apiDocumentationController.setBaseControllerPackage(BASE_CONTROLLER_PACKAGE);
        apiDocumentationController.setBasePath("http://localhost/swagger4spring-web-example");
        ApiListing documentation = apiDocumentationController.getDocumentation(servletRequest);
        assertNotNull(documentation);
        assertEquals(documentation.apiVersion(), "v1");
        assertEquals(documentation.resourcePath(), "/api/v1/test");
    }

    @Test
    public void getResourceList() {
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, "/api/doc/api/v1/test");
        ApiDocumentationController apiDocumentationController = new ApiDocumentationController();
        apiDocumentationController.setApiVersion("v1");
        apiDocumentationController.setBaseControllerPackage(BASE_CONTROLLER_PACKAGE);
        apiDocumentationController.setBasePath("http://localhost/swagger4spring-web-example");

        ResourceListing documentation = apiDocumentationController.getResources(servletRequest);

        assertNotNull(documentation);
        assertEquals("v1", documentation.apiVersion());

        assertEquals(END_POINT_PATHS.size()-2, documentation.apis().size()); // ignore those 2

        for (ApiListingReference endPoint : ScalaToJavaUtil.toJavaList(documentation.apis())) {
            assertTrue(END_POINT_PATHS.contains(endPoint.path()));
        }
    }
}
