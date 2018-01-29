Feature: Admin functionalities

  Background: Setup application
    Given The client <sharingcraftsman> is registered
    And An admin group is created with admin role
    And I have registered an admin account with username <admin@scuser.fr> and password <password>
    And I am connected with my account <admin@scuser.fr> and password <password>

  Scenario: Create, delete and consult list of available roles and groups
    Given I create the following authorizations
      | Group  | Role       |
      | ADMINS | ROLE_USER  |
      | USERS  | ROLE_USER  |
      | GOD    | ROLE_ROOT  |
      | GOD    | ROLE_ADMIN |
      | GOD    | ROLE_USER  |
    When I delete the role <ROLE_USER> from the group <GOD>
    And I consult all the groups and roles
    Then I get the roles
      | Group  | Role       |
      | ADMINS | ROLE_USER  |
      | ADMINS | ROLE_ADMIN |
      | USERS  | ROLE_USER  |
      | GOD    | ROLE_ROOT  |
      | GOD    | ROLE_ADMIN |

  Scenario: Create, delete, update users
    Given I create the following users
      | Username    | Password | Firstname | Lastname | Email           | Website             | Github                 | Linkedin                 | Picture     |
      | john@doe.fr | password | John      | Doe      | john@doe.fr     | www.johndoe.fr      | github.com/johndoe     | linkedin.com/johndoe     | picture.jpg |
      | misterhello | password | Mister    | Hello    | mister@hello.fr | www.misterhello.com | github.com/misterhello | linkedin.com/misterhello | picutre.jpg |
      | foo@bar.com | password | Foo       | Bar      | foo@bar.com     | www.foobar.de       | github.com/foobar      | linkedin.com/foobar      |             |
    And I create the following authorizations
      | Group | Role      |
      | USERS | ROLE_USER |
    When I update users
      | Username    | Firstname | Lastname | Email              | Website        | Github             | Linkedin             |
      | john@doe.fr | John      | Doe      | johndoe@thebest.eu | www.johndoe.eu | github.com/johndoe | linkedin.com/johndoe |
    And I delete user <misterhello>
    And I consult all users profiles
    Then I get the profiles
      | Username    | Firstname | Lastname | Email              | Website        | Github             | Linkedin             | Picture     | isActive | Authorizations  |
      | john@doe.fr | John      | Doe      | johndoe@thebest.eu | www.johndoe.eu | github.com/johndoe | linkedin.com/johndoe | picture.jpg | true     | USERS=ROLE_USER |
      | foo@bar.com | Foo       | Bar      | foo@bar.com        | www.foobar.de  | github.com/foobar  | linkedin.com/foobar  |             | true     | USERS=ROLE_USER |

  Scenario: Change user authorizations
    Given I create the following users
      | Username    | Firstname | Lastname | Email       | Website        | Github             | Linkedin             | Picture     |
      | john@doe.fr | John      | Doe      | john@doe.fr | www.johndoe.fr | github.com/johndoe | linkedin.com/johndoe | picture.jpg |
      | foo@bar.com | Foo       | Bar      | foo@bar.com | www.foobar.de  | github.com/foobar  | linkedin.com/foobar  | picture.jpg |
    And I create the following authorizations
      | Group  | Role      |
      | USERS  | ROLE_USER |
      | ADMINS | ROLE_USER |
    When I add authorization <ADMINS> to <john@doe.fr>
    And I remove authorization <USERS> to <foo@bar.com>
    And I consult all users profiles
    Then I get the profiles
      | Username    | Firstname | Lastname | Email       | Website        | Github             | Linkedin             | Picture     | isActive | Authorizations                              |
      | john@doe.fr | John      | Doe      | john@doe.fr | www.johndoe.fr | github.com/johndoe | linkedin.com/johndoe | picture.jpg | true     | USERS=ROLE_USER;ADMINS=ROLE_ADMIN,ROLE_USER |
      | foo@bar.com | Foo       | Bar      | foo@bar.com | www.foobar.de  | github.com/foobar  | linkedin.com/foobar  | picture.jpg | true     |                                             |
