#   @Author: Simon (s163595)
#   @Author: Hildibjørg (s164539)
#   @Author: Emily (s223122)

Feature: Report Feature

  Scenario: Report Success
    Given User has an ID
    And A user requests a report

    When a "ReportRequested" event is received
    Then the event is handled
    #Test if id is received
    Then a "LogRequested" event is sent







    #When a "ReportGenerated" event is received
    #test if report is received
    #Then a "ReportFinal" event is sent

  Scenario: Report Id null fail
    Given User has an ID null
    And A user requests a report

    When a "ReportRequested" event is received
    #Test if id is received
    Then a fail is sent