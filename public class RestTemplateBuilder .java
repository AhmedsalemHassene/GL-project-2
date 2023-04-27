public class RestTemplateBuilder {
	
	private final RestTemplateSettings restTemplateSettings;

	/**
	 * Create a new {@link RestTemplateBuilder} instance.
	 * @param customizers any {@link RestTemplateCustomizer RestTemplateCustomizers} that
	 * should be applied when the {@link RestTemplate} is built
	 */
	public RestTemplateBuilder(RestTemplateCustomizer... customizers) {
		Assert.notNull(customizers, "Customizers must not be null");
		this.restTemplateSettings = new RestTemplateSettings(customizers);
	}
	
	public RestTemplateBuilder requestFactorySettings(ClientHttpRequestFactorySettings requestFactorySettings) {
		this.restTemplateSettings.setRequestFactorySettings(requestFactorySettings);
		return this;
	}
	
	public RestTemplateBuilder detectRequestFactory(boolean detectRequestFactory) {
		this.restTemplateSettings.setDetectRequestFactory(detectRequestFactory);
		return this;
	}
	
	public RestTemplateBuilder rootUri(String rootUri) {
		this.restTemplateSettings.setRootUri(rootUri);
		return this;
	}
	
	public RestTemplateBuilder messageConverters(Set<HttpMessageConverter<?>> messageConverters) {
		this.restTemplateSettings.setMessageConverters(messageConverters);
		return this;
	}
	
	public RestTemplateBuilder interceptors(Set<ClientHttpRequestInterceptor> interceptors) {
		this.restTemplateSettings.setInterceptors(interceptors);
		return this;
	}
	
	public RestTemplateBuilder requestFactory(Function<ClientHttpRequestFactorySettings, ClientHttpRequestFactory> requestFactory) {
		this.restTemplateSettings.setRequestFactory(requestFactory);
		return this;
	}
	
	public RestTemplateBuilder uriTemplateHandler(UriTemplateHandler uriTemplateHandler) {
		this.restTemplateSettings.setUriTemplateHandler(uriTemplateHandler);
		return this;
	}
	
	public RestTemplateBuilder errorHandler(ResponseErrorHandler errorHandler) {
		this.restTemplateSettings.setErrorHandler(errorHandler);
		return this;
	}
	
	public RestTemplateBuilder basicAuthentication(BasicAuthentication basicAuthentication) {
		this.restTemplateSettings.setBasicAuthentication(basicAuthentication);
		return this;
	}
	
	public RestTemplateBuilder defaultHeaders(Map<String, List<String>> defaultHeaders) {
		this.restTemplateSettings.setDefaultHeaders(defaultHeaders);
		return this;
	}
	
	public RestTemplateBuilder customizers(Set<RestTemplateCustomizer> customizers) {
		this.restTemplateSettings.setCustomizers(customizers);
		return this;
	}
	
	public RestTemplateBuilder requestCustomizers(Set<RestTemplateRequestCustomizer<?>> requestCustomizers) {
		this.restTemplateSettings.setRequestCustomizers(requestCustomizers);
		return this;
	}
	
	public RestTemplate build() {
		return this.restTemplateSettings.build();
	}
	
	// Classe interne pour gérer les paramètres de configuration
	private static class RestTemplateSettings {
		
		private ClientHttpRequestFactorySettings requestFactorySettings;
		private boolean detectRequestFactory;
		private String rootUri;
		private Set<HttpMessageConverter<?>> messageConverters;
		private Set<ClientHttpRequestInterceptor> interceptors;
		private Function<ClientHttpRequestFactorySettings, ClientHttpRequestFactory> requestFactory;
		private UriTemplateHandler uriTemplateHandler;
		private ResponseErrorHandler errorHandler;
		private BasicAuthentication basicAuthentication;
		private Map<String, List<String>> defaultHeaders;
		private Set<RestTemplateCustomizer> customizers;
		private Set<RestTemplateRequestCustomizer<?>> requestCustomizers;
		
		public RestTemplateSettings(RestTemplateCustomizer... customizers) {
			this.requestFactorySettings = ClientHttpRequestFactorySettings.DEFAULTS;
			this.detectRequestFactory = true;
			this.rootUri = null;
			this
