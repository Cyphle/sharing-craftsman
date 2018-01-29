Feature: Change password

  Background: Setup application
    Given A client <sharingcraftsman> is registered

  Scenario: Change my password
    Given I register to the application with my credentials <john@doe.fr> and password <helloworld>
    And I connect to the application with my credentials <john@doe.fr> and password <helloworld>
    When I ask to change my password
    And I change my password with new password <newpassword>
    Then I connect to the application with my credentials <john@doe.fr> and password <newpassword>
    And I am connected