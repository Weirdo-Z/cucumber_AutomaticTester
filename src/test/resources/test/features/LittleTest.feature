Feature:
  Background: something you need to prepare
    #needed（or use ip:port)
    Given baseUrl is "http://my-server.com/"
    #trustStore is needed (fill the password if it is needed)
    Given trustStore is "trustStore" trustStorePassword is ""
    #hours that you expected to run (only execute once if you don't fill the number)
    Given expected run hours ""

  Scenario: simple test with trustStore to visit https

    Then send event "event.json"

    Then check status "200"
