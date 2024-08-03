//
// [keycloak-flow-trace]
//
// Sanjeev Premi <spremi@ymail.com>
//
// MIT License
//

package io.github.spremi;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.util.List;

import static io.github.spremi.TraceConstants.LOG_PREFIX;

public class TraceAuthenticatorFactory implements AuthenticatorFactory {
    private static final Logger logger = Logger.getLogger(TraceAuthenticatorFactory.class);

    private static final String PROVIDER_ID = "flow-trace";

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.ALTERNATIVE,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    private static final List<ProviderConfigProperty> configProperties;

    static {
        configProperties = ProviderConfigurationBuilder.create()
                .property()
                .name(TraceConstants.CFG_USER_ID)
                .type(ProviderConfigProperty.BOOLEAN_TYPE)
                .label(TraceConstants.LBL_USER_ID)
                .defaultValue(false)
                .helpText(TraceConstants.HLP_USER_ID)
                .add()

                .property()
                .name(TraceConstants.CFG_EXEC_STATUS)
                .type(ProviderConfigProperty.BOOLEAN_TYPE)
                .label(TraceConstants.LBL_EXEC_STATUS)
                .defaultValue(false)
                .name(TraceConstants.HLP_EXEC_STATUS)
                .add()

                .property()
                .name(TraceConstants.CFG_AUTH_NOTES)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label(TraceConstants.LBL_AUTH_NOTES)
                .defaultValue("")
                .helpText(TraceConstants.HLP_AUTH_NOTES)
                .add()

                .property()
                .name(TraceConstants.CFG_ALL_SESSION_NOTES)
                .type(ProviderConfigProperty.BOOLEAN_TYPE)
                .label(TraceConstants.LBL_ALL_SESSION_NOTES)
                .defaultValue(false)
                .helpText(TraceConstants.HLP_ALL_SESSION_NOTES)
                .add()

                .property()
                .name(TraceConstants.CFG_SESSION_NOTES)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label(TraceConstants.LBL_SESSION_NOTES)
                .defaultValue("")
                .helpText(TraceConstants.HLP_SESSION_NOTES)
                .add()

                .property()
                .name(TraceConstants.CFG_ALL_CLIENT_NOTES)
                .type(ProviderConfigProperty.BOOLEAN_TYPE)
                .label(TraceConstants.LBL_ALL_CLIENT_NOTES)
                .defaultValue(false)
                .helpText(TraceConstants.HLP_ALL_CLIENT_NOTES)
                .add()

                .property()
                .name(TraceConstants.CFG_CLIENT_NOTES)
                .type(ProviderConfigProperty.STRING_TYPE)
                .label(TraceConstants.LBL_CLIENT_NOTES)
                .defaultValue("")
                .helpText(TraceConstants.HLP_CLIENT_NOTES)
                .add()

                .property()
                .name(TraceConstants.CFG_RET_STATUS)
                .type(ProviderConfigProperty.LIST_TYPE)
                .label(TraceConstants.LBL_RET_STATUS)
                .options(TraceConstants.RET_STATUS)
                .defaultValue(TraceConstants.RET_STATUS_SUCCESS)
                .helpText(TraceConstants.HLP_RET_STATUS)
                .add()

                .build();
    }

    private static final TraceAuthenticator AUTHENTICATOR = new TraceAuthenticator();

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public String getDisplayType() {
        return TraceConstants.TXT_DISPLAY_TYPE;
    }

    @Override
    public String getReferenceCategory() {
        logger.debug(LOG_PREFIX + "getReferenceCategory()");

        return null;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return TraceConstants.TXT_HELP;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        logger.debug(LOG_PREFIX + "getConfigProperties()");

        return configProperties;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return (Authenticator) AUTHENTICATOR;
    }

    @Override
    public void init(Config.Scope scope) {
        logger.debug(LOG_PREFIX + "init()");
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        logger.debug(LOG_PREFIX + "postInit()");
    }

    @Override
    public void close() {
        logger.debug(LOG_PREFIX + "close()");
    }
}
