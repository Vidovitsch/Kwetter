FROM payara/server-full

ENV GLASSFISH_LIB=$GLASSFISH_HOME/glassfish/domains/domain1/lib/ext/

COPY .../simpleclient-0.0.26.jar $GLASSFISH_LIB
COPY .../simpleclient_common-0.0.26.jar $GLASSFISH_LIB
COPY target/application.war $GLASSFISH_HOME/glassfish/domains/domain1/autodeploy/