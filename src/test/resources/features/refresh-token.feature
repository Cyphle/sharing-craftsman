Feature: Registration

  Background: Setup application
    Given A client <sharingcraftsman> is registered

  Scenario: Register to the application
    Given I register to the application with my credentials <john@doe.fr> and password <helloworld>
    When I connect to the application with my credentials <john@doe.fr> and password <helloworld>
    And I refresh my token
    Then I have a new token