package io.katzorke.springbootspa.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

public class RouteAwareResourceResolver extends AbstractResourceResolver {

	private static final Logger LOG = LoggerFactory.getLogger(RouteAwareResourceResolver.class);

	private static final String INDEX = "index.html";

	private final String spaRootLocation;

	private Resource index;

	public RouteAwareResourceResolver(String spaLocation, ResourceLoader resourceLoader) {
		this.spaRootLocation = spaLocation;
		StringBuilder indexLocation = new StringBuilder(this.spaRootLocation);
		if (!this.spaRootLocation.endsWith("/")) {
			indexLocation.append('/');
		}
		indexLocation.append(INDEX);
		this.index = resourceLoader.getResource(indexLocation.toString());
	}

	@Override
	protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
			List<? extends Resource> locations, ResourceResolverChain chain) {
		LOG.debug("resolving path '{}'", requestPath);
		return locateResource(requestPath, locations);
	}

	@Override
	protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
			ResourceResolverChain chain) {
		return (StringUtils.hasText(resourceUrlPath) && locateResource(resourceUrlPath, locations) != null
				? resourceUrlPath
				: null);
	}

	private Resource locateResource(String requestPath, List<? extends Resource> locations) {
		for (Resource location : locations) {
			LOG.debug("Looking in {}.", location);
			Resource located = null;
			try {
				located = location.createRelative(requestPath);
				LOG.debug("Located resource '{}' does exist? {}", located.getURI(), located.exists());
				if (located.exists()) {
					return located;
				}
			} catch (IOException e) {
				// ignored, not found, whatever
			}
		}
		LOG.debug("Resource with path {} was not found, return index.html", requestPath);
		return index;
	}
}
