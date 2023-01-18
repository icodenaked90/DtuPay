#   @Author: Simon (s163595)
#   @Author: Hildibjørg (s164539)
#   @Author: Emily (s223122)

Feature: Report Feature

  Scenario: Report Success
    Given 2 customers
    And 2 merchants which have recieved 1 payment from each customer
    When a "ReportRequested" event is received for a customer
    #Test if id is received
    Then a "LogRequested" event is sent
    And both payments are logged

    Given 2 merchants
    And 2 customers which have made 1 payment to each customer
    When a "ReportRequested" event is received for a customer
    #Test if id is received
    Then a "LogRequested" event is sent
    And both payments are logged





    #When a "ReportGenerated" event is received
    #test if report is received
    #Then a "ReportFinal" event is sent

