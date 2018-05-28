# WSD-Demo-2018
Web Services Development (31284) demonstration project.

In order to use this project, you'll need to resolve it's dependencies using Maven. Furthermore, since this project uses concepts introduced in Java 7 / 8, you'll need to modify your JDK and GlassFish configurations.

1). Change your JDK version to 1.7 or 1.8 (preferrable).
2). Modify your GlassFish installations JSP Servlet by adding the following parameters within: glassfish > domains > %domain_name% > config > default-web.xml

&lt;init-param&gt;
&lt;param-name&gt;compilerSourceVM&lt;/param-name&gt;
&lt;param-value&gt;1.8&lt;/param-value&gt;
&lt;/init-param&gt;

&lt;init-param&gt;
&lt;param-name&gt;compilerTargetVM&lt;/param-name&gt;
&lt;param-value&gt;1.8&lt;/param-value&gt;
&lt;/init-param&gt;
