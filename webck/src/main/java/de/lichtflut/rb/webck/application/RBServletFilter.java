/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 *  Servlet filter. Currently just for ensuring UTF-8 encoding.
 * </p>
 *
 * <p>
 * 	Created Dec 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBServletFilter implements Filter {

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void destroy() {
	}

}
