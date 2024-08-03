//
// [keycloak-flow-trace]
//
// Sanjeev Premi <spremi@ymail.com>
//
// MIT License
//

package io.github.spremi;

import java.util.List;

public class TraceConstants {
    private TraceConstants() {
        // Hide implicit public constructor
    }

    public static final String LOG_PREFIX = "[trace] ";

    public static final String TXT_HELP = "A null authenticator to trace progress through authentication flow";
    public static final String TXT_DISPLAY_TYPE = "Trace Flow";

    public static final String CFG_USER_ID = "traceUserId";
    public static final String LBL_USER_ID = "Show User ID";
    public static final String HLP_USER_ID = "Show User ID in trace.";

    public static final String CFG_EXEC_STATUS = "traceExecStatus";
    public static final String LBL_EXEC_STATUS = "Show Execution Status";
    public static final String HLP_EXEC_STATUS = "Show Execution Status in trace";

    public static final String CFG_AUTH_NOTES = "traceAuthNotes";
    public static final String LBL_AUTH_NOTES = "Show Auth Notes";
    public static final String HLP_AUTH_NOTES = "List of auth notes to trace";

    public static final String CFG_ALL_SESSION_NOTES = "traceAllSessionNotes";
    public static final String LBL_ALL_SESSION_NOTES = "Show all session notes?";
    public static final String HLP_ALL_SESSION_NOTES = "This value takes precedence over specific list below.";

    public static final String CFG_SESSION_NOTES = "traceSessionNotes";
    public static final String LBL_SESSION_NOTES = "Show only these session notes";
    public static final String HLP_SESSION_NOTES = "List of session notes to show. This list is used only when '" +
            LBL_ALL_SESSION_NOTES  +
            "' is set to false.";

    public static final String CFG_ALL_CLIENT_NOTES = "traceAllClientNotes";
    public static final String LBL_ALL_CLIENT_NOTES = "Show all client notes?";
    public static final String HLP_ALL_CLIENT_NOTES = "This value takes precedence over specific list below.";

    public static final String CFG_CLIENT_NOTES = "traceClientNotes";
    public static final String LBL_CLIENT_NOTES = "Show only these client notes";
    public static final String HLP_CLIENT_NOTES = "List of client notes to show. This list is used only when '" +
            LBL_ALL_CLIENT_NOTES  +
            "' is set to false.";

    public static final String CFG_RET_STATUS = "traceReturnStatus";
    public static final String LBL_RET_STATUS = "Return status";
    public static final String HLP_RET_STATUS = "Authentication status to return. " +
            "Depends on flow context where authenticator is invoked.";

    public static final String RET_STATUS_SUCCESS = "SUCCESS";
    public static final String RET_STATUS_FAILURE = "FAILURE";
    public static final String RET_STATUS_ATTEMPTED = "ATTEMPTED";

    public static final List<String> RET_STATUS = List.of(
            RET_STATUS_SUCCESS,
            RET_STATUS_FAILURE,
            RET_STATUS_ATTEMPTED);
}
