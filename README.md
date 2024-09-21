# Keycloak Flow Tracer

This is a **debug** authenticator that can trace path through Keycloak's
authentication flows and log values in `AuthNote`, `UserSessionNote` and
`ClientNote` for the user(s).

## Features

* Can be kept in production flows.
  - Enable/ Disable as necessary to debug specific failures.
* Can be inserted multiple times in a flow.
* Choice to show UserID along with values.
* Choice to show execution status.
* Values are added to server logs.
  - No information is passed to the UI.
* Fully configurable
  - Choose type of values to log:
    - AuthNote
    - UserSessionNote
    - ClientNote
  - Provide **keys** whose values should be logged.
* Choose the return value of authenticator.
  - Proper selection is necessary to ensure that main logic of
    the execution flow isn't altered.

## Usage

* Add to your Keycloak installation just like any other authenticator.
  - Follow the requirement (Required/ Alternative) based for the sub-flow.
* Insert authenticator into execution flow... as many times we need to debug.
* Set alias
* Select if UserID should be included in trace
  ![Show User ID](https://github.com/spremi/keycloak-flow-trace/blob/dev.01/docs/kc-trace-00.png?raw=true)
* Select if Execution Status should be included in trace
  ![Show Execution Status](https://github.com/spremi/keycloak-flow-trace/blob/dev.01/docs/kc-trace-01.png?raw=true)
* Add comma seperated list of AuthNotes to trace
  ![Show Auth Notes](https://github.com/spremi/keycloak-flow-trace/blob/dev.01/docs/kc-trace-02.png?raw=true)
* Select if User Session Notes should be traced /
  provide a comma seperated list of specific ones
  ![Show Session Notes](https://github.com/spremi/keycloak-flow-trace/blob/dev.01/docs/kc-trace-03.png?raw=true)
* Select if Client Notes should be traced /
  provide a comma seperated list of specific ones
  ![Show Client Notes](https://github.com/spremi/keycloak-flow-trace/blob/dev.01/docs/kc-trace-04.png?raw=true)
* Select return value from this authenticator.
  ![Show Client Notes](https://github.com/spremi/keycloak-flow-trace/blob/dev.01/docs/kc-trace-05.png?raw=true)

  Normal selection would be ATTEMPTED / SUCCESS depending on placement in
  the flow/ sub-flow. But, you could explicitly return FAILURE. It is quite
  useful to debug execution flow on failure.

## License
Copyright (c) 2023-2024, Sanjeev Premi.

Source available under the terms of MIT license.
