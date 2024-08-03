//
// [keycloak-flow-trace]
//
// Sanjeev Premi <spremi@ymail.com>
//
// MIT License
//

package io.github.spremi;

import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;

import java.util.Arrays;
import java.util.Map;

import static io.github.spremi.TraceConstants.LOG_PREFIX;

public class TraceAuthenticator implements Authenticator {
    private static final Logger logger = Logger.getLogger(TraceAuthenticator.class);

    private static final String RE_COMMA = "\\s*,\\s*";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.debug(LOG_PREFIX + "authenticate()");

        final AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        final AuthenticationSessionModel session = context.getAuthenticationSession();

        boolean traceUserId = true;
        boolean traceExecStatus = true;
        boolean traceAllSessionNotes = true;
        boolean traceAllClientNotes = true;

        String[] authNoteKeys = new String[0];
        String[] sessionNotekeys = new String[0];
        String[] clientNoteKeys = new String[0];

        if (config != null) {
            traceUserId = Boolean.parseBoolean(config.getConfig().get(TraceConstants.CFG_USER_ID));
            traceExecStatus = Boolean.parseBoolean(config.getConfig().get(TraceConstants.CFG_EXEC_STATUS));

            String cfgAuthNotes = config.getConfig().get(TraceConstants.CFG_AUTH_NOTES);
            if (cfgAuthNotes != null) {
                authNoteKeys = cfgAuthNotes.trim().split(RE_COMMA);
            }

            String cfgSessionNotes = config.getConfig().get(TraceConstants.CFG_SESSION_NOTES);
            if (cfgSessionNotes != null) {
                sessionNotekeys = cfgSessionNotes.trim().split(RE_COMMA);
            }

            String cfgClientNotes = config.getConfig().get(TraceConstants.CFG_SESSION_NOTES);
            if (cfgClientNotes != null) {
                clientNoteKeys = cfgClientNotes.trim().split(RE_COMMA);
            }

            traceAllSessionNotes = Boolean.parseBoolean(config.getConfig().get(TraceConstants.CFG_ALL_SESSION_NOTES));
            traceAllClientNotes = Boolean.parseBoolean(config.getConfig().get(TraceConstants.CFG_ALL_CLIENT_NOTES));
        }

        if (traceUserId) {
            if (context.getUser() == null) {
                logger.info(LOG_PREFIX + "User ID: null");
            } else {
                logger.infov(LOG_PREFIX + "User ID: {0}", context.getUser().getId());
            }
        }

        if (traceExecStatus) {
            logger.infov(LOG_PREFIX + "Current status: {0}", context.getStatus());
        }

        if (authNoteKeys.length == 0) {
            logger.info(LOG_PREFIX + "No AuthNote(s)");
        } else  {
            for (String authNote: authNoteKeys) {
                logger.infov(LOG_PREFIX + "AuthNote: {0}={1}", authNote, session.getAuthNote(authNote));
            }
        }

        if (sessionNotekeys.length == 0) {
            logger.info(LOG_PREFIX + "No UserSessionNote(s)");
        } else  {
            String[] noteKeys;

            if (traceAllSessionNotes) {
                noteKeys = session.getUserSessionNotes().keySet().toArray(new String[0]);
            } else {
                noteKeys = getIntersection(
                        session.getUserSessionNotes().keySet().toArray(new String[0]),
                        sessionNotekeys);
            }

            final Map<String, String> notes = session.getUserSessionNotes();

            for (String noteKey: noteKeys) {
                logger.infov(LOG_PREFIX + "UserSessionNote: {0}={1}", noteKey, notes.get(noteKey));
            }
        }

        if (clientNoteKeys.length == 0) {
            logger.info(LOG_PREFIX + "No ClientNote(s)");
        } else  {
            String[] noteKeys;

            if (traceAllClientNotes) {
                noteKeys = session.getClientNotes().keySet().toArray(new String[0]);
            } else {
                noteKeys = getIntersection(
                        session.getClientNotes().keySet().toArray(new String[0]),
                        clientNoteKeys);
            }

            final Map<String, String> notes = session.getClientNotes();

            for (String noteKey: noteKeys) {
                logger.infov(LOG_PREFIX + "ClientNote: {0}={1}", noteKey, notes.get(noteKey));
            }
        }

        setStatus(context);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.debug(LOG_PREFIX + "action()");

        setStatus(context);
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

    /**
     * Set status of authenticator from configuration.
     * Default is "SUCCESS".
     */
    private void setStatus(AuthenticationFlowContext context) {
        final AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        final String ret = config.getConfig().get(TraceConstants.CFG_RET_STATUS);

        logger.infov(LOG_PREFIX + "Returning: {0}", ret);

        switch (ret) {

            case TraceConstants.RET_STATUS_ATTEMPTED -> {
                context.attempted();
            }

            case TraceConstants.RET_STATUS_FAILURE -> {
                context.failure(AuthenticationFlowError.GENERIC_AUTHENTICATION_ERROR,
                        context.form()
                                .setError(TraceConstants.MSG_ERROR)
                                .createErrorPage(Response.Status.METHOD_NOT_ALLOWED));
            }

            default -> {
                context.success();
            }
        }
    }

    /**
     * Find intersection between given array.
     * i.e. values in "reqValues" that exist in "allValues".
     */
    private String[] getIntersection(final String[] allValues, final String[] reqValues) {
        return Arrays.stream(allValues)
                .filter(s -> Arrays.asList(reqValues).contains(s))
                .toArray(String[]::new);
    }
}
