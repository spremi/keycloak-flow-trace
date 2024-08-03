//
// [keycloak-flow-trace]
//
// Sanjeev Premi <spremi@ymail.com>
//
// MIT License
//

package io.github.spremi;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import static io.github.spremi.TraceConstants.LOG_PREFIX;

public class TraceAuthenticator implements Authenticator {
    private static final Logger logger = Logger.getLogger(TraceAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.debug(LOG_PREFIX + "authenticate()");
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.debug(LOG_PREFIX + "action()");
    }

    @Override
    public boolean requiresUser() {
        logger.debug(LOG_PREFIX + "requiresUser()");

        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realmModel, UserModel userModel) {
        logger.debug(LOG_PREFIX + "configuredFor()");

        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realmModel, UserModel userModel) {
        logger.debug(LOG_PREFIX + "setRequiredActions()");
    }

    @Override
    public void close() {
        logger.debug(LOG_PREFIX + "close()");
    }
}
